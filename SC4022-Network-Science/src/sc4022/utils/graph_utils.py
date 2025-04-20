import json
import random
from collections import defaultdict
from pathlib import Path
from typing import List

import matplotlib.pyplot as plt
import networkx as nx
import numpy as np
import seaborn as sns
from matplotlib.animation import FuncAnimation
from scipy.stats import pearsonr

from sc4022.dataset import DataScientist


class GraphUtils:
    @staticmethod
    def initialize_graph(data_scientists: List[DataScientist]) -> nx.Graph:
        """
        Initializes a network graph with nodes representing data scientists.
        """
        graph = nx.Graph()
        for data_scientist in data_scientists:
            graph.add_node(
                data_scientist.pid,
                name=data_scientist.name,
                country=data_scientist.country,
                institution=data_scientist.institution,
                expertise=data_scientist.expertise,
            )
        return graph

    @staticmethod
    def construct_network(data_scientists: List[DataScientist], results_dir: Path):
        """
        Constructs a collaboration network based on co-authorship relationships.
        """

        graph = GraphUtils.initialize_graph(data_scientists)
        collaboration_edges = {}

        for data_scientist in data_scientists:
            for collaboration in data_scientist.collaborations:
                for co_author_pid in collaboration.coauthors_pid:
                    if co_author_pid in graph.nodes:
                        if (data_scientist.pid, co_author_pid) in collaboration_edges:
                            collaboration_edges[
                                (data_scientist.pid, co_author_pid)
                            ] += 1
                        else:
                            collaboration_edges[(data_scientist.pid, co_author_pid)] = 1

        for (author1, author2), weight in collaboration_edges.items():
            graph.add_edge(author1, author2, weight=weight)

        collaboration_dir = Path(results_dir) / "collaboration"
        collaboration_dir.mkdir(parents=True, exist_ok=True)
        GraphUtils.compute_network_metrics(graph, collaboration_dir, "Real Network")
        GraphUtils.visualize_network(
            graph, str(collaboration_dir / "collaboration_network.png")
        )
        return graph

    @staticmethod
    def compute_network_metrics(graph: nx.Graph, target_folder: Path, graph_type: str):
        """
        Computes and prints key network properties.
        """

        # compute the average degree, max degree and degree distribution
        degrees = dict(graph.degree())
        print(f"📊 Computing Network Metrics for {graph_type}:")
        print(f"Number of nodes: {graph.number_of_nodes()}")
        avg_degree = np.mean(list(degrees.values()))
        print(f" {graph_type} - Avg Degree: {avg_degree:.2f}")
        degree_list = [d for n, d in degrees.items()]
        highest_degree = max(degree_list)
        # find the node with the highest degree
        for node, degree in degrees.items():
            if degree == highest_degree:
                if graph_type == "Real Network":
                    print(
                        f' {graph_type} - Scientist {graph.nodes[node]["name"]} has the highest degree of {highest_degree}'
                    )
                else:
                    print(f" {graph_type} has the highest degree of {highest_degree}")
        degree_counts = {}
        for degree in degree_list:
            if degree in degree_counts:
                degree_counts[degree] += 1
            else:
                degree_counts[degree] = 1
        total_nodes = len(graph.nodes())
        degree_dist = {k: count / total_nodes for k, count in degree_counts.items()}
        degree_x = list(degree_dist.keys())
        degree_y = list(degree_dist.values())
        plt.figure(figsize=(8, 6))
        plt.scatter(degree_x, degree_y, color="blue")
        plt.xscale("log")
        plt.yscale("log")
        plt.xlabel("Degree (log scale)")
        plt.ylabel("Density (log scale)")
        plt.title(f"Degree Distribution (Log-Log Scale) for {graph_type}")
        file_path = str(target_folder / f"{graph_type}_degree_distribution.png")
        plt.savefig(file_path, format="png")
        print(f"📂 {graph_type} - Degree Distribution saved at {file_path}")

        # compute the average clustering coefficient and the clustering coefficient distribution
        avg_clustering = nx.average_clustering(graph)
        print(f"{graph_type} - Avg Clustering Coeff: {avg_clustering:.3f}")
        clustering_coeffs = nx.clustering(graph)
        degree_to_clustering = defaultdict(list)
        for node, coeff in clustering_coeffs.items():
            degree_to_clustering[degrees[node]].append(coeff)
        avg_clustering_dist = {
            degree: np.mean(coeffs) for degree, coeffs in degree_to_clustering.items()
        }
        # sort the degree
        sorted_degree = sorted(avg_clustering_dist.keys())
        sorted_avg_clustering_dist = {
            degree: avg_clustering_dist[degree] for degree in sorted_degree
        }
        clustering_coefficients_x = []
        clustering_coefficients_y = []
        for k, v in sorted_avg_clustering_dist.items():
            if v > 0:
                clustering_coefficients_x.append(k)
                clustering_coefficients_y.append(v)
        plt.figure(figsize=(8, 6))
        plt.plot(clustering_coefficients_x, clustering_coefficients_y)
        plt.xscale("log")
        plt.yscale("log")
        plt.xlabel("Degree of node (k)")
        plt.ylabel("Average Clustering Coefficient")
        plt.title(f"{graph_type} - Average Clustering Coefficient vs Degree")
        file_path = str(
            target_folder / f"{graph_type}_clustering_coefficient_distribution.png"
        )
        plt.savefig(file_path, format="png")
        print(f"📂 Clustering Coefficient Distribution saved at {file_path}")

        # Compute the number of edges and network density
        num_edges = graph.number_of_edges()
        print(f"{graph_type} - Number of edges: {num_edges}")
        density = nx.density(graph)
        print(f"{graph_type} - Network Density: {density:.2f}")

        # Compute the assortativity
        assortativity = nx.degree_assortativity_coefficient(graph)
        print(f"{graph_type} - Assortativity: {assortativity:.2f}")

        # Giant Component: Size of giant component, Diameter of Graph, Average Path Length, Distance Distribution
        num_components = nx.number_connected_components(graph)
        print(f"{graph_type} - Number of Connected Components: {num_components}")
        # Compute size of giant component
        largest_component = max(nx.connected_components(graph), key=len)
        largest_component_size = len(largest_component)
        print(
            f"{graph_type} - Size of Largest Connected Component: {largest_component_size}"
        )
        subgraph = graph.subgraph(largest_component)
        # Find diameter of the subgraph
        diameter = nx.diameter(subgraph)
        print(f"{graph_type} - Diameter of Graph: {diameter}")
        # Find the average path length
        path_lengths = dict(nx.shortest_path_length(subgraph))
        avg_path_length = np.mean(
            [np.mean(list(lengths.values())) for _, lengths in path_lengths.items()]
        )
        if avg_path_length:
            print(
                f"{graph_type} - Avg Shortest Path Length (Largest Component): {avg_path_length:.2f}"
            )
        # Find the distance distribution
        distance = dict(nx.shortest_path_length(subgraph))
        distance_list = []
        for node, distance_dict in distance.items():
            for distance_node, distance_value in distance_dict.items():
                if node != distance_node:  # exclude the distance to itself
                    distance_list.append(distance_value)
        distance_counts = {}
        for distance in distance_list:
            if distance in distance_counts:
                distance_counts[distance] += 1
            else:
                distance_counts[distance] = 1
        total_nodes = len(subgraph.nodes())
        total_pairs = total_nodes * (total_nodes - 1) / 2
        distance_dist = {k: v / total_pairs for k, v in distance_counts.items()}
        distance_x = list(distance_dist.keys())
        distance_y = list(distance_dist.values())
        plt.figure(figsize=(8, 6))
        plt.scatter(distance_x, distance_y, color="b")
        plt.plot(distance_x, distance_y, color="b")
        plt.xlabel("Distance")
        plt.ylabel("Density")
        plt.title(f"{graph_type} - Density vs Distance between two nodes")
        file_path = str(target_folder / f"{graph_type}_distance_distribution.png")
        plt.savefig(file_path, format="png")
        print(
            f"📂 {graph_type} Distance Distribution of Largest Component saved at {file_path}"
        )

    @staticmethod
    def compare_with_other_networks(graph: nx.Graph, result_dir: Path):
        """
        Compares the real collaboration network with Erdős–Rényi (ER) and Barabási–Albert (BA) models.

        Args:
            graph (nx.Graph): The real collaboration network.
            result_dir (Path): The output folder path where visualizations will be saved.
        """
        num_nodes = graph.number_of_nodes()
        num_edges = graph.number_of_edges()

        if num_edges == 0:
            print(
                "⚠️ Network has no edges, creating minimal random networks for comparison."
            )
            num_edges = max(1, num_nodes // 2)  # Ensure at least minimal structure

        # Adjust edge probability to prevent extreme sparsity in ER model
        edge_prob = min(1, (2 * num_edges) / (num_nodes * (num_nodes - 1)))
        er_graph = nx.erdos_renyi_graph(num_nodes, p=edge_prob)

        # Ensure Barabási–Albert model has at least 1 connection per node
        ba_graph = nx.barabasi_albert_graph(num_nodes, m=max(1, num_edges // num_nodes))

        # create target folder
        target_dir = Path(result_dir) / "compare_networks"
        target_dir.mkdir(parents=True, exist_ok=True)

        # compare the networks
        GraphUtils.compute_network_metrics(er_graph, target_dir, "Random_Network")
        GraphUtils.compute_network_metrics(
            ba_graph, target_dir, "Barabasi_Albert_Network"
        )

        # 🔹 Save network visualizations
        GraphUtils.visualize_network(er_graph, str(target_dir / "random_network.png"))
        GraphUtils.visualize_network(
            ba_graph, str(target_dir / "barabasi_albert_network.png")
        )

        print(f"📂 Saved real, ER, and BA network visualizations.")

    @staticmethod
    def visualize_network(graph: nx.Graph, target_file: str):
        """
        Visualizes the collaboration network and saves it as an image.
        Colors are based on country (instead of institution) to avoid overcrowding.
        Enlarges top 8 scientists with distinct colors and name labels.
        """
        plt.figure(figsize=(12, 12))
        pos = nx.spring_layout(graph, k=0.5, seed=42)

        countries = [
            graph.nodes[node].get("country", "Unknown") for node in graph.nodes()
        ]
        unique_countries = sorted(set(countries))
        country_to_color = {
            country: idx for idx, country in enumerate(unique_countries)
        }
        palette = sns.color_palette("tab20", len(unique_countries))
        node_colors = [palette[country_to_color[c]] for c in countries]

        edges = list(graph.edges())
        edge_weights = [graph[u][v].get("weight", 1) for u, v in edges]

        # Identify top 8 nodes
        top_nodes = sorted(graph.degree, key=lambda x: x[1], reverse=True)[:8]
        top_node_ids = [node for node, _ in top_nodes]

        node_sizes = [500 if node in top_node_ids else 100 for node in graph.nodes()]

        nx.draw(
            graph,
            pos,
            node_color=node_colors,
            edge_color="gray",
            alpha=0.6,
            node_size=node_sizes,
            width=[w * 0.2 for w in edge_weights],
            with_labels=False,
        )

        top_labels = {
            node: graph.nodes[node].get("name", f"Node {node}") for node in top_node_ids
        }
        nx.draw_networkx_labels(
            graph, pos, labels=top_labels, font_size=20, font_color="black"
        )

        plt.title("Collaboration Network (Colored by Country)", fontsize=18)
        plt.axis("off")
        plt.savefig(target_file)
        print(f"📂 Network visualization saved at {target_file}")

    @staticmethod
    def update_network(
        graph: nx.Graph, data_scientists: List[DataScientist], year: int
    ) -> nx.Graph:
        """
        Updates the collaboration network with edges corresponding to a given year.
        """
        for data_scientist in data_scientists:
            for collaboration in data_scientist.collaborations:
                if collaboration.year <= year:
                    for co_author_pid in collaboration.coauthors_pid:
                        if co_author_pid in graph.nodes:
                            graph.add_edge(data_scientist.pid, co_author_pid)
        return graph

    @staticmethod
    def create_plot(years, metric, label: str, evolution_dir: Path):
        plt.figure(figsize=(10, 5))
        plt.plot(
            years,
            metric,
        )
        plt.xlabel("Year")
        plt.ylabel(label)
        plt.legend()
        plt.title(f"Temporal Evolution of {label}")
        plt.grid(True)
        file_name = f'Evolution_{label.replace(" ", "_")}'
        plt.savefig(evolution_dir / file_name)
        print(f"📂 Temporal evolution of {label} saved at {evolution_dir / file_name}")

    @staticmethod
    def collaborate_evolution(
        data_scientists: List[DataScientist],
        start_year: int,
        end_year: int,
        output_folder: str,
    ):
        """
        Generates an animated visualization of the network evolving over time and saves it as a GIF.

        Args:
            data_scientists (List[DataScientist]): List of data scientists.
            start_year (int): The starting year for the evolution.
            end_year (int): The ending year for the evolution.
            output_folder (str): Path where the results will be saved.
        """
        if start_year > end_year:
            print("❌ Invalid year range: Start year cannot be greater than end year.")
            return

        evolution_dir = Path(output_folder) / "evolution"
        evolution_dir.mkdir(parents=True, exist_ok=True)

        graphs_dict = {}
        fig, ax = plt.subplots(figsize=(12, 12))
        graph = GraphUtils.initialize_graph(data_scientists)

        num_edges_per_year = []
        avg_degree_per_year = []
        highest_degree_per_year = []
        density_per_year = []
        avg_clustering_coeff_per_year = []
        assortativity_per_year = []
        num_connected_components = []
        giant_component_size_per_year = []

        pos = nx.spring_layout(graph, seed=42)  # Ensure consistent layout across years

        collaboration_dir = evolution_dir / "yearly_collaboration_network_graphs"
        collaboration_dir.mkdir(parents=True, exist_ok=True)

        for year in range(start_year, end_year + 1):
            ax.clear()
            graph = GraphUtils.update_network(graph, data_scientists, year)
            graphs_dict[year] = graph.copy()

            # Collect network statistics
            num_edges_per_year.append(graph.number_of_edges())
            degrees = dict(graph.degree())
            avg_degree_per_year.append(np.mean(list(degrees.values())))
            degree_list = [d for n, d in degrees.items()]
            max_degree = max(degree_list)
            highest_degree_per_year.append(max_degree)
            density_per_year.append(nx.density(graph))
            avg_clustering_coeff_per_year.append(nx.average_clustering(graph))
            assortativity_per_year.append(nx.degree_assortativity_coefficient(graph))
            connected_components = list(nx.connected_components(graph))
            num_connected_components.append(len(connected_components))
            giant_component = max(connected_components, key=len)
            giant_component_size_per_year.append(len(giant_component))

            # Identify the top 5 most connected authors
            top_connected = sorted(graph.degree, key=lambda x: x[1], reverse=True)[:5]

            print(f"\n📌 **Top 5 Most Connected Authors in {year}**")
            for author, degree in top_connected:
                print(f"   - {graph.nodes[author]['name']} ({degree} collaborations)")

            # Identify the top 5 most influential authors (betweenness centrality)
            if graph.number_of_edges() > 0:
                betweenness = nx.betweenness_centrality(graph)
                top_influential = sorted(
                    betweenness.items(), key=lambda x: x[1], reverse=True
                )[:5]

                print(
                    f"\n🔥 **Top 5 Most Important (High Centrality) Authors in {year}**"
                )
                for author, centrality in top_influential:
                    print(
                        f"   - {graph.nodes[author]['name']} (Centrality: {centrality:.4f})"
                    )

            # Save yearly network visualization
            save_path = collaboration_dir / f"Collaboration_Network_{year}.png"
            plt.figure(figsize=(12, 12))
            nx.draw_networkx(
                graph,
                pos,
                node_size=50,
                edge_color="grey",
                alpha=0.6,
                with_labels=False,
            )
            plt.title(f"Collaboration Network in {year}", fontsize=14)
            plt.axis("off")
            plt.savefig(save_path)
            plt.close()
            print(f"📂 Network saved: {save_path}")

        # Define the update function for animation
        def update(frame_year):
            ax.clear()
            ax.set_title(f"Collaboration Network in {frame_year}", fontsize=14)
            nx.draw_networkx(
                graphs_dict[frame_year],
                pos,
                node_size=50,
                edge_color="grey",
                alpha=0.6,
                with_labels=False,
                ax=ax,
            )
            ax.set_xticks([])
            ax.set_yticks([])

        # Save the evolution as a GIF
        gif_path = evolution_dir / "Collaboration_Evolution.gif"
        animation = FuncAnimation(
            fig, update, frames=range(start_year, end_year + 1), interval=500
        )
        animation.save(gif_path, writer="pillow")

        print(f"✅ Collaboration evolution animation saved at: {gif_path}")

        # 📈 Save each evolution metrics as a graph
        years = list(range(start_year, end_year + 1))
        metrics_list = [
            (num_edges_per_year, "Number of Edges"),
            (avg_degree_per_year, "Average Degree"),
            (highest_degree_per_year, "Highest Degree"),
            (density_per_year, "Density"),
            (avg_clustering_coeff_per_year, "Average Clustering Coefficient"),
            (assortativity_per_year, "Assortativity"),
            (num_connected_components, "Number of Connected Components"),
            (giant_component_size_per_year, "Size of Giant Component"),
        ]
        for metric, label in metrics_list:
            GraphUtils.create_plot(years, metric, label, evolution_dir)

        # ✅ Run centrality evolution tracking after graph construction
        GraphUtils.compute_centrality_evolution(graphs_dict, evolution_dir)

    @staticmethod
    def compute_centrality_evolution(
        graphs_dict: dict, evolution_dir: Path, top_n: int = 8, min_appearance: int = 5
    ):
        """
        Computes and stores centrality metrics for each year in the evolution.
        Creates:
        - Centrality over time line plots with top authors highlighted
        - Degree vs Betweenness scatter plot per year
        - Correlation plot: Degree vs Betweenness
        """
        from statistics import mean

        yearly_centrality_scores = defaultdict(lambda: defaultdict(dict))
        top_nodes_per_metric = defaultdict(list)
        correlations = []

        timeline = []
        degree_vs_betweenness_pairs = []

        degree_betweenness_dir = evolution_dir / "degree_vs_betweenness_graphs"
        degree_betweenness_dir.mkdir(parents=True, exist_ok=True)

        for year, G in graphs_dict.items():
            print(f"\n🧠 Processing centralities for {year}")
            if G.number_of_edges() == 0:
                continue

            degrees = dict(G.degree())
            betweenness = nx.betweenness_centrality(G)
            closeness = nx.closeness_centrality(G)
            try:
                eigenvector = nx.eigenvector_centrality(G, max_iter=1000)
            except nx.PowerIterationFailedConvergence:
                eigenvector = {n: 0 for n in G.nodes()}

            x = list(degrees.values())
            y = [betweenness[n] for n in G.nodes()]
            if len(x) > 1 and len(set(y)) > 1:
                r, _ = pearsonr(x, y)
                correlations.append((year, r))

            timeline.append(year)
            degree_vs_betweenness_pairs.append((mean(x), mean(y)))

            # Save scatter plot for the year
            plt.figure()
            plt.scatter(x, y, alpha=0.6)
            plt.xlabel("Degree Centrality")
            plt.ylabel("Betweenness Centrality")
            plt.title(f"Degree vs Betweenness Centrality in {year}")
            plt.grid(True)
            plt.savefig(degree_betweenness_dir / f"Degree_vs_Betweenness_{year}.png")
            plt.close()

            for node in G.nodes():
                yearly_centrality_scores["degree"][node][year] = degrees.get(node, 0)
                yearly_centrality_scores["betweenness"][node][year] = betweenness.get(
                    node, 0
                )
                yearly_centrality_scores["closeness"][node][year] = closeness.get(
                    node, 0
                )
                yearly_centrality_scores["eigenvector"][node][year] = eigenvector.get(
                    node, 0
                )

            for metric, centrality in zip(
                ["degree", "betweenness", "closeness", "eigenvector"],
                [degrees, betweenness, closeness, eigenvector],
            ):
                top_nodes = sorted(
                    centrality.items(), key=lambda x: x[1], reverse=True
                )[:top_n]
                top_nodes_per_metric[metric] = [
                    node for node, _ in top_nodes
                ]  # overwrite each year

        distinct_colors = sns.color_palette("tab10", top_n)

        for metric, node_scores in yearly_centrality_scores.items():
            top_nodes = top_nodes_per_metric[metric]
            plt.figure(figsize=(12, 6))
            for i, node in enumerate(top_nodes):
                scores = node_scores[node]
                years = sorted(scores.keys())
                values = [scores[y] for y in years]
                plt.plot(
                    years,
                    values,
                    label=node,
                    color=distinct_colors[i % len(distinct_colors)],
                    linewidth=2.5,
                )

            plt.title(f"{metric.capitalize()} Centrality Over Time")
            plt.xlabel("Year")
            plt.ylabel(f"{metric.capitalize()} Centrality")
            plt.grid(True)
            plt.legend(loc="center left", bbox_to_anchor=(1, 0.5), fontsize="medium")
            plt.tight_layout()
            plt.savefig(evolution_dir / f"{metric}_centrality_over_time.png")
            plt.close()

        if correlations:
            years, corr_vals = zip(*correlations)
            plt.figure()
            plt.plot(years, corr_vals, marker="o")
            plt.title("Correlation Between Degree and Betweenness Over Time")
            plt.xlabel("Year")
            plt.ylabel("Pearson Correlation")
            plt.grid(True)
            plt.savefig(evolution_dir / "Degree_Betweenness_Correlation.png")
            plt.close()

        # Temporal evolution of Degree vs Betweenness (mean)
        if degree_vs_betweenness_pairs:
            mean_degrees, mean_betweenness = zip(*degree_vs_betweenness_pairs)
            plt.figure(figsize=(10, 5))
            plt.plot(timeline, mean_degrees, label="Mean Degree Centrality", marker="o")
            plt.plot(
                timeline,
                mean_betweenness,
                label="Mean Betweenness Centrality",
                marker="s",
            )
            plt.title("Temporal Evolution of Mean Centralities")
            plt.xlabel("Year")
            plt.ylabel("Mean Centrality Value")
            plt.legend()
            plt.grid(True)
            plt.tight_layout()
            plt.savefig(evolution_dir / "Temporal_Mean_Degree_vs_Betweenness.png")
            plt.close()

        print("\n✅ Centrality analysis complete. Plots saved.")

    @staticmethod
    def compute_transformation_metrics_graph(
        original_graph: nx.Graph, transformed_graph: nx.Graph, output_path: Path
    ):
        """
        Computes and stores transformation metrics comparing the original and transformed graphs.
        The metrics are saved in a readable JSON format.
        """

        def extract_metrics(graph, label):
            print(f"\n🔍 Extracting metrics for {label}...")
            components = list(nx.connected_components(graph))
            giant_component = max(components, key=len)
            size_gc = len(giant_component)

            degree_dict = dict(graph.degree())
            max_degree = max(degree_dict.values())

            isolates = [node for node, degree in degree_dict.items() if degree == 0]
            bridges = list(nx.bridges(graph))

            countries = set(nx.get_node_attributes(graph, "country").values())
            institutions = set(nx.get_node_attributes(graph, "institution").values())
            expertise = set(nx.get_node_attributes(graph, "expertise").values())

            print(
                f"📌 {label} - Nodes: {graph.number_of_nodes()}, Edges: {graph.number_of_edges()}"
            )
            print(
                f"📌 {label} - Max Degree: {max_degree}, Giant Component Size: {size_gc}"
            )
            print(f"📌 {label} - Isolates: {len(isolates)}, Bridges: {len(bridges)}")
            print(
                f"📌 {label} - Diversity → Countries: {len(countries)}, Institutions: {len(institutions)}, Expertise: {len(expertise)}"
            )

            return {
                "num_nodes": graph.number_of_nodes(),
                "num_edges": graph.number_of_edges(),
                "max_degree": max_degree,
                "size_of_giant_component": size_gc,
                "number_of_isolates": len(isolates),
                "bridge_count": len(bridges),
                "isolated_node_ids": isolates,
                "diversity": {
                    "countries": len(countries),
                    "institutions": len(institutions),
                    "expertise": len(expertise),
                },
            }

        original = extract_metrics(original_graph, "Original Network")
        transformed = extract_metrics(transformed_graph, "Transformed Network")

        def pct_change(old, new):
            if old == 0:
                return None
            return round(((new - old) / old) * 100, 2)

        metrics = {
            "original_network": original,
            "transformed_network": transformed,
            "percentage_change": {
                "max_degree": pct_change(
                    original["max_degree"], transformed["max_degree"]
                ),
                "size_of_giant_component": pct_change(
                    original["size_of_giant_component"],
                    transformed["size_of_giant_component"],
                ),
                "number_of_isolates": pct_change(
                    original["number_of_isolates"], transformed["number_of_isolates"]
                ),
                "bridge_count": pct_change(
                    original["bridge_count"], transformed["bridge_count"]
                ),
                "diversity": {
                    "countries": pct_change(
                        original["diversity"]["countries"],
                        transformed["diversity"]["countries"],
                    ),
                    "institutions": pct_change(
                        original["diversity"]["institutions"],
                        transformed["diversity"]["institutions"],
                    ),
                    "expertise": pct_change(
                        original["diversity"]["expertise"],
                        transformed["diversity"]["expertise"],
                    ),
                },
            },
        }

        json_file = output_path / "transformation_metrics.json"
        with open(json_file, "w") as f:
            json.dump(metrics, f, indent=4)

        print(f"\n✅ Transformation metrics JSON file saved at: {json_file}")
        print("📊 Key percentage changes:")
        for key, val in metrics["percentage_change"].items():
            if isinstance(val, dict):
                for subkey, subval in val.items():
                    original_val = original["diversity"][subkey]
                    transformed_val = transformed["diversity"][subkey]
                    print(
                        f"    - Diversity [{subkey}]: {subval}% (original: {original_val} → transformed: {transformed_val})"
                    )
            else:
                original_val = original[key]
                transformed_val = transformed[key]
                print(
                    f"    - {key.replace('_', ' ').title()}: {val}% (original: {original_val} → transformed: {transformed_val})"
                )

        # Create comparison bar chart
        import matplotlib.pyplot as plt

        categories = [
            "Max Degree",
            "Giant Component Size",
            "Isolates",
            "Bridge Count",
            "Countries",
            "Institutions",
            "Expertise",
        ]
        original_vals = [
            original["max_degree"],
            original["size_of_giant_component"],
            original["number_of_isolates"],
            original["bridge_count"],
            original["diversity"]["countries"],
            original["diversity"]["institutions"],
            original["diversity"]["expertise"],
        ]
        transformed_vals = [
            transformed["max_degree"],
            transformed["size_of_giant_component"],
            transformed["number_of_isolates"],
            transformed["bridge_count"],
            transformed["diversity"]["countries"],
            transformed["diversity"]["institutions"],
            transformed["diversity"]["expertise"],
        ]

        x = np.arange(len(categories))
        width = 0.35

        plt.figure(figsize=(10, 6))
        plt.bar(x - width / 2, original_vals, width, label="Original", color="skyblue")
        plt.bar(
            x + width / 2, transformed_vals, width, label="Transformed", color="salmon"
        )

        plt.ylabel("Metric Value")
        plt.title("Original vs Transformed Network Metrics")
        plt.xticks(x, categories, rotation=30)
        plt.legend()
        plt.tight_layout()

        chart_path = output_path / "transformation_metrics_comparison.png"
        plt.savefig(chart_path)
        plt.close()

        print(f"📊 Visual comparison chart saved at: {chart_path}")
        return json_file
