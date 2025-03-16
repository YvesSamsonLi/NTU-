## 📌 **HDB Resale Price Prediction & Model Analysis**
A **machine learning** and **deep learning**-based approach to **predicting HDB resale prices in Singapore** using structured datasets. This project explores **data preprocessing, model training, explainability, and data drift detection** to improve predictive accuracy.

---

## 📖 **Table of Contents**
- [📌 Introduction](#-introduction)
- [🛠️ Methodology](#%EF%B8%8F-methodology)
- [📊 Data Preprocessing](#-data-preprocessing)
- [🧠 Model Training & Evaluation](#-model-training--evaluation)
- [📈 Explainability Analysis](#-explainability-analysis)
- [🚨 Data Drift Detection](#-data-drift-detection)
- [🔍 Findings & Insights](#-findings--insights)
- [📝 Conclusion](#-conclusion)
- [📂 Repository Structure](#-repository-structure)

---

## 📌 **Introduction**
The goal of this project is to **predict resale prices of HDB flats in Singapore** based on key features like **flat size, lease duration, and distance to MRT stations**. 

Additionally, we **analyze feature importance, detect data drift over time, and improve model robustness** by training on different data periods. The project provides an **end-to-end machine learning pipeline**, from **data preprocessing** to **interpretability analysis**.

---

## 🛠️ **Methodology**
We follow a structured workflow to **ensure high-quality predictions and explainable AI (XAI):**

1. **Data Preprocessing** → Cleaning, encoding categorical features, and scaling numerical features.
2. **Model Training** → Training a **PyTorch Tabular Model** using different datasets.
3. **Performance Evaluation** → Computing **R² score** and **RMSE** for different training sets.
4. **Explainability Analysis** → Using **Captum** to understand **feature importance**.
5. **Data Drift Detection** → Applying **TabularDrift** to compare **training (≤2019) vs. test (2023)**.

---

## 📊 **Data Preprocessing**
To ensure high-quality inputs for model training, we perform:

- **Feature Selection** → Dropping irrelevant columns (`full_address`, `nearest_stn`).
- **Categorical Encoding** → Using **label encoding** for categorical features (`town`, `flat_model_type`).
- **Numerical Scaling** → Applying **StandardScaler** to features like `floor_area_sqm` and `remaining_lease_years`.
- **Data Splitting**:
  - **Training Set** (≤2019)
  - **Validation Set** (2020)
  - **Testing Set** (2023)

---

## 🧠 **Model Training & Evaluation**
We use **PyTorch Tabular Models** for regression:

### 🔹 **Different Training Approaches**
| Training Set | Test Set | R² Score | RMSE |
|-------------|---------|---------|----------|
| Year ≤ 2020 | 2023 | 0.0792 | 164,759.52 |
| Year ≤ 2021 | 2023 | 0.3734 | 135,911.73 |
| 2017 | 2023 | 0.2023 | N/A |

### 🔍 **Key Observations**
- **Training on older data (≤2020) leads to poor performance on 2023**.
- **Adding 2021 data improves accuracy significantly**.
- **Models trained on 2017 data perform well only for nearby years** but degrade over time.

---

## 📈 **Explainability Analysis**
To interpret the model’s decisions, we use **Captum** for feature attribution:

### 🔹 **Feature Importance (Standard Model)**
| Feature | Importance Score |
|---------|----------------|
| **Degree Centrality** | 🔴 High Influence |
| Floor Area (sqm) | 🟠 Moderate Influence |
| Remaining Lease Years | 🟠 Moderate Influence |
| Distance to MRT | 🔵 Low Influence |

🔹 **Findings:**
- **Degree Centrality dominates** but is not meaningful for price prediction.
- **Location & lease duration play a significant role** but were underweighted.
- **Standardization improves attribution**, making `floor_area_sqm`, `remaining_lease_years`, and `distance_to_MRT` more prominent.

### 🔹 **Visualizing Feature Importance**
1. **Heatmaps** → Show which features contribute the most to model decisions.
2. **Violin Plots** → Illustrate **distribution of attribution scores**, helping detect outliers.

---

## 🚨 **Data Drift Detection**
To assess if the model is affected by market shifts, we apply **TabularDrift**.

| Feature | Drift Detected? |
|---------|----------------|
| **Distance to MRT** | 🚨 Yes, Significant Drift |
| **Remaining Lease Years** | 🚨 Yes, Significant Drift |
| **Floor Area (sqm)** | 🚨 Yes, Significant Drift |

### 🔹 **Key Observations**
- **Location, lease duration, and flat size** have significantly shifted.
- **Older training data (≤2019) does not capture new trends**.
- **A model trained on data closer to 2021 generalizes better**.

---

## 🔍 **Findings & Insights**
### ✅ **Key Takeaways**
- **Training on recent data (≤2021) improves performance on 2023.**
- **Degree Centrality is over-weighted initially**, but standardization corrects it.
- **Violin plots and heatmaps help identify meaningful feature contributions.**
- **Data drift affects key pricing factors, requiring model retraining over time.**

### 🏆 **Best Strategy for Prediction**
1. **Train on data closer to the test year (e.g., ≤2021 for 2023).**
2. **Standardize numerical features to avoid dominance of irrelevant variables.**
3. **Continuously monitor data drift to detect market shifts.**

---

## 📝 **Conclusion**
This project highlights the importance of **training on recent data**, **explaining model decisions**, and **detecting data drift** to improve resale price prediction accuracy.

By integrating **Captum for explainability** and **TabularDrift for model monitoring**, we ensure that the model remains both **accurate and interpretable**.
