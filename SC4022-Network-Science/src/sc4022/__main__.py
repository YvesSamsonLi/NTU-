import argparse
import copy
import json
from pathlib import Path

from sc4022.dataset.clustering import OneHotClusterEngine
from sc4022.utils import FileUtils, GraphUtils


def parse_argument():
    """
    Parses command-line arguments for input and output file paths.

    Returns:
        argparse.Namespace: Parsed command-line arguments.
    """
    parser = argparse.ArgumentParser(description="SC4022 Assignment")
    parser.add_argument(
        "--input",
        "-i",
        type=str,
        help="Path to the input Excel file",
        default="./inputs/DataScientists.xls",
    )
    parser.add_argument(
        "--output",
        "-o",
        type=str,
        help="Path to the output directory",
        default="./outputs",
    )
    parser.add_argument(
        "--skip-scraping",
        action="store_true",
        help="Skip the scraping process (provided that cleaned_data.csv and xml_files exists)",
    )
    parser.add_argument(
        "--cutoff-value",
        "-c",
        type=float,
        help="Cutoff value for clustering",
        default=4,
    )
    parser.add_argument(
        "--num-clusters",
        "-n",
        type=int,
        help="Number of clusters for KMeans clustering",
        default=13,
    )
    parser.add_argument(
        "--min-degrees",
        "-m",
        type=int,
        help="Minimum number of degrees for clustering that does not consider in removing",
        default=80,
    )
    return parser.parse_args()


def main():
    """
    Main execution function for the SC4022 assignment.
    - Reads and cleans dataset.
    - Downloads and processes XML files.
    - Constructs the collaboration network.
    - Analyzes network metrics.
    - Compares with random networks.
    - Generates time-evolution visualization.
    """
    args = parse_argument()
    input_file = args.input
    skip_scraping = args.skip_scraping
    k = args.num_clusters
    min_degrees = args.min_degrees
    cutoff_value = args.cutoff_value
    output_folder = Path(args.output)

    # Ensure output folder exists
    output_folder.mkdir(parents=True, exist_ok=True)

    print(f"\n📂 Output directory set to: {output_folder}")

    # Set xml folder
    xml_folder = output_folder / "xml_folder"

    # cache path
    cache_path = output_folder / "cleaned_data.csv"

    # Step 1: Load cached dataset if available
    df = FileUtils.load_cached_dataset(cache_path)
    if skip_scraping and df is not None:
        print("✅ Using cached dataset. Skipping data processing.")

    else:
        print(
            "⚠️ No cache found / Web scraping not skipped. Running dataset processing..."
        )
        df = FileUtils.read_excel(input_file)
        if df is None or df.empty:
            print("❌ Error: Dataset is empty or unreadable. Exiting.")
            return

        print(f"📊 Initial dataset loaded with {df.shape[0]} rows.")

        # Clean dataset: remove duplicates and standardize fields
        df = FileUtils.clean_df(df)

        # Step 2: Download XML files
        xml_folder.mkdir(parents=True, exist_ok=True)
        df = FileUtils.download_xml_files(df, xml_folder, cache_path)
        print(f"📊 Dataset after XML processing: {df.shape[0]} rows remain.")

    # Step 3: Read XML files and construct data scientist objects
    data_scientists, min_year, max_year = FileUtils.read_xml_files(df, xml_folder)
    if not data_scientists:
        print("❌ No valid data scientists found after XML processing. Exiting.")
        return

    print(f"✅ Successfully parsed {len(data_scientists)} researchers from XML files.")
    print(f"📆 Collaboration data spans from {min_year} to {max_year}.")

    # Step 4: Construct and visualize the collaboration network
    result_folder = output_folder / "results"
    result_folder.mkdir(parents=True, exist_ok=True)

    print("\n🔧 Constructing collaboration network...")
    graph = GraphUtils.construct_network(data_scientists, result_folder)
    original_graph = copy.deepcopy(graph)  # Preserve original for later comparison

    # Step 5: Generate time evolution visualization
    print("\n🎥 Generating collaboration evolution animation...")
    gif_path = result_folder / "Collaboration_Evolution.gif"
    GraphUtils.collaborate_evolution(
        data_scientists, min_year, max_year, str(result_folder)
    )
    # Centrality evolution analysis is already triggered inside collaborate_evolution

    print(f"🎥 Evolution animation saved as: {gif_path}")

    # Step 6: Compare real network with random networks (Erdős–Rényi & Barabási–Albert)
    print("\n🔍 Comparing real network with other models...")
    GraphUtils.compare_with_other_networks(graph, result_folder)

    # Step 7: Perform network transformation
    print("\n🧪 Starting network transformation process...")
    transformation_folder = result_folder / "transformation"
    transformation_folder.mkdir(parents=True, exist_ok=True)

    one_hot_cluster_engine = OneHotClusterEngine(graph)
    one_hot_cluster_engine.extract_embeddings()
    one_hot_cluster_engine.cluster(
        n_clusters=k, transformation_dir=transformation_folder
    )
    one_hot_cluster_engine.remove_to_cutoff(cutoff=cutoff_value, min_nodes=min_degrees)

    # ✅ Step 8: Compute and save transformation metrics
    print("\n📊 Computing transformation metrics...")
    metrics_path = GraphUtils.compute_transformation_metrics_graph(
        original_graph=original_graph,
        transformed_graph=one_hot_cluster_engine.graph,
        output_path=transformation_folder,
    )

    print(f"📁 Transformation metrics file available at: {metrics_path}")
    print("\n✅ **All tasks completed successfully!** 🚀")


if __name__ == "__main__":
    main()
