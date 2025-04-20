# 🤝 SC4022 Network Science Project  
**"Transforming Academic Collaboration Networks via Feature-Aware Clustering and Bridge Node Reduction"**

---

## 📘 Overview

This repository contains the full implementation of a network science project developed for **SC4022 – Network Science** at **Nanyang Technological University (NTU)**.  
We analyze and transform a real-world **scientific co-authorship network** with the goal of reducing network over-centralization while preserving collaboration diversity and resilience.

---

## 🎯 Problem Statement

Academic collaboration networks face three main issues:

- **Over-centralization**: A few researchers dominate the entire network's connectivity.
- **Bridge-dependency**: Critical nodes act as "bridges" between clusters—removing them risks fragmentation.
- **Underrepresented diversity**: Loss of institutional, geographical, or domain representation.

We propose a transformation method to:

- 🔻 Reduce **maximum degree** to distribute influence more evenly.
- 🔻 Minimize **bridge nodes** to increase structural resilience.
- ✅ Preserve **diversity** in countries, institutions, and expertise.
- ✅ Maintain a strong **giant component** (overall connectedness).

---

## 🧪 Methodology

### 🔹 1. Data Processing

- Load and clean an Excel dataset of data scientists.
- Download DBLP XML files to retrieve author collaboration data.
- Standardize fields (names, expertise, affiliations).

### 🔹 2. Network Construction

- Each node represents a researcher.
- Each edge represents a co-authorship, weighted by frequency.
- Implemented using `NetworkX`.

### 🔹 3. Temporal Network Evolution

- Simulate growth of the collaboration network from `min_year` to `max_year`.
- Track changes in key centrality metrics:
  - Degree
  - Betweenness
  - Closeness
  - Eigenvector Centrality
- Generate:
  - Evolution animation (GIF)
  - Yearly snapshots
  - Centrality correlation plots

### 🔹 4. Random Model Comparison

To evaluate the uniqueness of the real collaboration network, we compare it with:

- **Erdős–Rényi (ER) Model**  
  Generates a random graph where each edge has a fixed independent probability `p`.  
  This model helps assess **randomness vs. structure** in our real graph.

- **Barabási–Albert (BA) Model**  
  Constructs scale-free networks through **preferential attachment**: nodes with higher degrees attract more connections.  
  Useful for comparing **real-world hub formation**.

### 🔹 5. Network Transformation Algorithm

We implement a **feature-aware clustering & bridge-removal algorithm**:

1. **One-Hot Encoding**:
   - Nodes are encoded using their attributes: `Country`, `Institution`, and `Expertise`.

2. **K-Means Clustering**:
   - Find similar nodes using clustering (`k ≈ 13`, determined by elbow method).
   - Each cluster groups similar researchers.

3. **Selective Node Removal**:
   - Within each cluster:
     - First remove **bridge nodes** if they exist.
     - Otherwise, remove the **highest-degree node**.
   - Ensure each cluster retains a **minimum node count** to preserve diversity.

4. **Stopping Criteria**:
   - Stop when:
     - Maximum degree falls below the cutoff.
     - Or clusters become too small to proceed.

---

## 📊 Key Findings

| Metric                    | Original | Transformed | % Change |
|--------------------------|----------|-------------|----------|
| Max Degree               | 98       | 48          | -51.02%  |
| Giant Component Size     | 962      | 681         | -29.21%  |
| Number of Isolates       | 72       | 111         | +54.17%  |
| Number of Bridges        | 68       | 30          | -55.88%  |
| Countries Represented    | 44       | 40          | -9.09%   |
| Institutions Represented | 641      | 543         | -15.29%  |
| Expertise Types          | 10       | 10          | 0.00% ✅  |

📌 **Conclusion**:  
Our transformation **successfully reduces centralization and fragility**, while **retaining key diversity** across nodes.  
The giant component remains robust, and bridge count is halved—demonstrating improved resilience.

---

## 📂 File & Folder Structure
## 📂 Project File & Folder Structure

```bash
.
├── inputs/
│   └── DataScientists.xls          # Original Excel data
├── outputs/
│   ├── cleaned_data.csv            # Cached dataset
│   ├── xml_folder/                 # DBLP XML files
│   └── results/
│       ├── collaboration/          # Initial network visualization
│       ├── evolution/              # Animated GIFs & centrality plots
│       ├── compare_networks/       # ER & BA model comparisons
│       └── transformation/         # Transformation logs & visuals
│           ├── removed_nodes_log.csv
│           ├── removed_nodes_trend.png
│           ├── optimal_k_cluster.png
│           ├── transformation_metrics.json
│           └── bar_chart_comparison.png
├── sc4022/
│   ├── dataset/
│   │   └── clustering.py           # Clustering & embedding engine
│   └── utils/
│       └── graph_utils.py          # All graph construction and analysis functions
└── main.py                         # CLI entry point

```

---

## 📈 Visual Outputs

✅ `Collaboration_Evolution.gif` — Year-by-year growth of the collaboration network  
✅ Degree distribution & clustering coefficient plots  
✅ Real vs Random model (ER/BA) comparison visualizations  
✅ Centrality correlation plots over time  
✅ `optimal_k_cluster.png` — Elbow method for KMeans clustering  
✅ `bar_chart_comparison.png` — Before vs after metric transformation

---

## 🧰 Tools & Libraries Used

- Python 3.10+
- `networkx`
- `matplotlib`, `seaborn`
- `scikit-learn`
- `pandas`, `numpy`
- `argparse`, `pathlib`
- `tqdm`, `collections`, `json`, `os`

---

## 👥 Team

Developed by students of **SC4022 – Network Science**  
Nanyang Technological University (NTU), AY2024/25

- **Tham Zeng Lam**  
- **Yves Samson Li**  
- **Zhang Kaichen**


## 🏁 How to Run This Project

### 🔧 1. Install the Environment

Clone the repo and install dependencies in **editable mode**:

```bash
git clone https://github.com/your-username/sc4022-network-project.git
cd sc4022-network-project
pip install -e .


## Installation

```bash
conda create -n sc4022 python=3.11
cd SC4022-Network-Science;
python3 -m pip install -e .
```

## Develop

```bash
git checkout -b dev/xxx

## Update your code
git fetch origin main
git rebase origin/main
git push -f origin dev/xxx
```

Update the deps in `pyproject.toml`. Add it in optional if the deps is optional.

## Format your code
```bash
python3 -m pip install pre-commit
pre-commit install
pre-commit run --all-files
```

## Unittest
```bash
python test/run_suite.py
```
