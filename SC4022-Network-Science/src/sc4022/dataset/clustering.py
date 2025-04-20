from collections import defaultdict

import matplotlib.pyplot as plt
import networkx as nx
import numpy as np
import pandas as pd
from sklearn.cluster import KMeans


class OneHotClusterEngine:
    """
    Perform clustering on a networkx graph using the onehot embeddings
    """

    def __init__(self, graph: nx.Graph):
        self.graph = graph
        self.removed_nodes_log = []

    def extract_embeddings(self):
        self.nodes = list(self.graph.nodes(data=True))
        unique_values_per_dim = defaultdict(set)
        for node in self.nodes:
            node_name, node_data = node
            for k, v in node_data.items():
                if k == "name":
                    continue
                unique_values_per_dim[k].add(v)

        unique_values_per_dim = {k: list(v) for k, v in unique_values_per_dim.items()}
        self.dim_by_category = {k: len(v) for k, v in unique_values_per_dim.items()}
        self.category = list(self.dim_by_category.keys())
        self.dim = list(self.dim_by_category.values())
        self.embedding = np.zeros((len(self.nodes), sum(self.dim_by_category.values())))
        self.node_to_index = {
            node_name: i for i, (node_name, _) in enumerate(self.nodes)
        }
        self.index_to_node = {
            i: node_name for i, (node_name, _) in enumerate(self.nodes)
        }

        for i, (node_name, node_data) in enumerate(self.nodes):
            for k, v in node_data.items():
                if k == "name":
                    continue
                index = unique_values_per_dim[k].index(v)
                start_index = sum(self.dim[: self.category.index(k)])
                self.embedding[i, start_index + index] = 1

    def cluster(self, n_clusters, transformation_dir):
        """
        Perform clustering on the graph using KMeans
        """
        k_values = range(1, 30)
        wcss = []
        for k in k_values:
            kmeans = KMeans(n_clusters=k, random_state=42, n_init="auto")
            kmeans.fit(self.embedding)
            wcss.append(kmeans.inertia_)
        plt.figure(figsize=(8, 5))
        plt.plot(k_values, wcss, "bo-")
        plt.xlabel("Number of Clusters (k)")
        plt.ylabel("Within-Cluster Sum of Squares (WCSS)")
        plt.title("Elbow Method For Optimal k")
        plt.savefig(transformation_dir / "optimal_k_cluster.png")
        plt.close()
        kmeans = KMeans(n_clusters=n_clusters, random_state=0)
        kmeans.fit(self.embedding)
        self.labels = kmeans.labels_
        self.clusters_group = defaultdict(list)
        for idx, cluster in enumerate(self.labels):
            self.clusters_group[cluster].append(idx)

    def get_removed_nodes(self):
        return self.removed_nodes_log

    def remove_to_cutoff(
        self, cutoff: float, min_nodes: int = 32, transformation_dir=None
    ):
        iterations = 1
        print("🔁 Starting node removal iterations...")

        while True:
            degrees = list(self.graph.degree())
            if not degrees:
                print("⚠️ Graph is empty. Exiting loop.")
                break

            cutoff_degree = max(d for _, d in degrees)

            if cutoff_degree <= cutoff:
                print(f"✅ Target met. Max Degree {cutoff_degree} ≤ Cutoff {cutoff}")
                break

            if not any(
                len(nodes) >= min_nodes for nodes in self.clusters_group.values()
            ):
                print("⚠️ No cluster meets the minimum node threshold. Exiting loop.")
                break
            components = nx.connected_components(self.graph)
            giant_component_nodes = max(components, key=len)
            gc = self.graph.subgraph(giant_component_nodes)

            bridges = list(nx.bridges(gc))
            nodes_in_bridges = set()
            for bridge in bridges:
                nodes_in_bridges.update(bridge)

            node_removed_in_this_iteration = False

            for cluster, nodes in self.clusters_group.items():
                if len(nodes) <= min_nodes:
                    continue

                # Convert cluster indices to node IDs
                nodes = [
                    self.index_to_node[n]
                    for n in nodes
                    if self.index_to_node[n] in self.graph
                ]

                if not nodes:
                    continue

                removed_node = None
                for node in nodes:
                    if node in nodes_in_bridges:
                        removed_node = node
                        break

                if removed_node is None:
                    removed_node = max(nodes, key=lambda n: self.graph.degree(n))

                try:
                    self.removed_nodes_log.append(
                        {
                            "node_id": removed_node,
                            "name": self.graph.nodes[removed_node].get("name"),
                            "degree": self.graph.degree(removed_node),
                            "is_bridge": removed_node in nodes_in_bridges,
                            "cluster": cluster,
                            "iteration": iterations,
                        }
                    )

                    self.graph.remove_node(removed_node)
                    removed_node_index = self.node_to_index.get(removed_node)
                    if removed_node_index in self.clusters_group[cluster]:
                        self.clusters_group[cluster].remove(removed_node_index)

                    node_removed_in_this_iteration = True
                    break  # remove one node per iteration

                except Exception as e:
                    print(f"❌ Error removing node {removed_node}: {e}")

            print(f"🔄 Iteration {iterations}: Max Degree now {cutoff_degree}")
            iterations += 1

            if not node_removed_in_this_iteration:
                print("⚠️ No node was removed in this iteration. Exiting loop.")
                break

        # Save removal log and trend if applicable
        if transformation_dir and self.removed_nodes_log:
            df_removed = pd.DataFrame(self.removed_nodes_log)
            df_removed.to_csv(transformation_dir / "removed_nodes_log.csv", index=False)
            plt.figure(figsize=(8, 5))
            plt.plot(df_removed["iteration"], df_removed["degree"], marker="o")
            plt.xlabel("Iteration")
            plt.ylabel("Removed Node Degree")
            plt.title("Degree of Removed Nodes per Iteration")
            plt.savefig(transformation_dir / "removed_nodes_trend.png")
            plt.close()
            print("📁 Removed nodes log and plot saved.")
