# 📌 **GTZAN Audio Genre Classification - Deep Learning Approach**
A **deep learning-based approach** for classifying **music genres** using **feedforward neural networks (DNNs)** with **PyTorch**. This project explores **feature extraction, model optimization, explainability (SHAP), and hyperparameter tuning** to enhance classification performance.

---

## 📖 **Table of Contents**
- [📌 Introduction](#-introduction)
- [🛠️ Methodology](#%EF%B8%8F-methodology)
- [📊 Data Preprocessing](#-data-preprocessing)
- [🧠 Model Training & Evaluation](#-model-training--evaluation)
- [⚙️ Hyperparameter Tuning](#%EF%B8%8F-hyperparameter-tuning)
  - [🔹 Batch Size Optimization](#-batch-size-optimization)
  - [🔹 Number of Hidden Neurons](#-number-of-hidden-neurons)
- [📈 Model Explainability with SHAP](#-model-explainability-with-shap)
- [🔍 Findings & Insights](#-findings--insights)
- [📝 Conclusion](#-conclusion)
- [📂 Repository Structure](#-repository-structure)

---

## 📌 **Introduction**
This project aims to **classify music genres** (blues vs. metal) using **deep neural networks** trained on the **GTZAN dataset**. The classification is achieved by **extracting numerical features** from the audio and feeding them into a **fully connected feedforward neural network (DNN)**.

Additionally, we:
- **Optimize the model** through **batch size tuning** and **hidden neuron selection**.
- **Interpret model decisions** using **SHAP (SHapley Additive Explanations)** to **understand feature importance**.
- **Use early stopping** to avoid overfitting and improve model generalization.

---

## 🛠️ **Methodology**
The project follows a structured deep learning pipeline:

1. **Data Preprocessing** → Extract features from audio files and normalize input data.
2. **Model Definition** → Design a **DNN with three hidden layers** using **ReLU activations** and **dropout regularization**.
3. **Training & Optimization** → Train the model using **Adam optimizer** with **early stopping**.
4. **Hyperparameter Tuning** → Optimize batch size and neuron count for improved performance.
5. **Model Explainability** → Use **SHAP force plots** to analyze which features influence predictions.

---

## 📊 **Data Preprocessing**
To ensure high-quality input for training, we **preprocess the dataset** as follows:

- **Feature Extraction** → Extract key audio features (MFCCs, chroma, etc.) from the GTZAN dataset.
- **Label Encoding** → Convert the genre labels to binary (`0 = blues`, `1 = metal`).
- **Feature Scaling** → Standardize numerical features using `StandardScaler` to improve training efficiency.
- **Data Splitting** → Train-test split in **70:30** ratio.

---

## 🧠 **Model Training & Evaluation**
The **deep neural network (DNN)** consists of:
- **Three hidden layers** with **128 neurons each**.
- **ReLU activation function** for non-linearity.
- **Dropout (0.2 probability)** to prevent overfitting.
- **Sigmoid activation** for binary classification.

### 🔹 **Training Configuration**
| Parameter        | Value |
|-----------------|-------|
| **Optimizer**   | Adam  |
| **Learning Rate** | 0.001 |
| **Batch Size** | 128 |
| **Loss Function** | Binary Cross-Entropy (BCE) |
| **Training Epochs** | 100 |
| **Early Stopping** | Patience = 3 |

### 🔹 **Training Results**
| Epoch | Train Accuracy | Test Accuracy | Train Loss | Test Loss |
|--------|--------------|-------------|-------------|-------------|
| 1 | 78.93% | 81.67% | 0.6082 | 0.4801 |
| 5 | 95.57% | 92.50% | 0.1251 | 0.1844 |
| 10 | 98.86% | 95.50% | 0.0441 | 0.1403 |
| 16 | 99.50% | 96.33% | 0.0193 | 0.1542 |

- **Training stopped early at epoch 16** (early stopping).
- **Final test accuracy: 96.33%** → Model generalizes well.
- **Test loss stabilized**, confirming good performance.

---

## ⚙️ **Hyperparameter Tuning**
### 🔹 **Batch Size Optimization**
To find the **optimal batch size**, we tested **32, 64, 128, and 256**, using **5-fold cross-validation**.

| Batch Size | Average Loss | Average Accuracy | Time Taken (s) |
|------------|-------------|-----------------|--------------|
| **32** | 0.0610 | 98.29% | 16.25s |
| **64** | 0.0602 | 98.36% | 11.43s |
| **128** | 0.0440 | **98.43%** | 16.68s |
| **256** | 0.0433 | 98.29% | 17.04s |

✅ **Optimal Batch Size: 128**
- **Best accuracy** (98.43%) with **low loss (0.0440)**.
- **Efficient training time** compared to **256 batch size**.

---

### 🔹 **Number of Hidden Neurons**
We evaluated **64, 128, and 256 neurons** in the **first hidden layer**.

| Neurons | Average Loss | Average Accuracy | Time Taken (s) |
|---------|-------------|-----------------|--------------|
| **64** | 0.0528 | **98.00%** | 15.73s |
| **128** | **0.0493** | 97.86% | 19.49s |
| **256** | 0.0511 | **98.00%** | 20.54s |

✅ **Optimal Neurons: 256**
- **Highest accuracy** (98.00%) with **balanced loss**.
- **Stable training across epochs**.

---

## 📈 **Model Explainability with SHAP**
To **interpret the model’s decisions**, we used **SHAP (SHapley Additive Explanations)**:

### 🔹 **Test Audio Prediction**
- **Predicted Label**: `1` (metal genre)
- **SHAP Analysis**:
  - `mfcc9_mean (-9.87)`: Strong negative contribution to **increase the probability**.
  - `mfcc14_mean (-2.25)`: Moderate negative impact.
  - `chroma_stft_var (0.08)`: Slight positive contribution.

### 🔹 **Force Plot Interpretation**
- **Red features** → Pushed the prediction **towards Class 1 (metal)**.
- **Blue features** → Tried to pull the prediction towards Class 0 (blues).
- **Base Value (0.5)** → Model threshold.
- **Final Predicted Value: ~0.51** → The prediction barely crosses **the decision boundary**.

🔹 **Findings**
- **MFCC features dominate the prediction**, indicating they are the most crucial for genre classification.
- **Potential model bias** towards certain spectral features.
- **Further tuning could enhance feature weighting** to improve robustness.

---

## 🔍 **Findings & Insights**
### ✅ **Key Takeaways**
- **Deep neural networks effectively classify GTZAN music genres** with high accuracy.
- **Batch size 128 optimizes accuracy and training efficiency**.
- **256 neurons in the first hidden layer provide the best generalization**.
- **SHAP analysis reveals that MFCCs are the dominant features** influencing predictions.

### 🏆 **Best Strategy for Classification**
1. **Use batch size 128 and 256 neurons for best performance.**
2. **Leverage SHAP for model explainability and debugging.**
3. **Regularize with dropout to prevent overfitting.**

---

## 📝 **Conclusion**
This project successfully implemented a **deep learning model** for **music genre classification**. We explored:
- **Model optimization** (batch size, neuron count).
- **Performance evaluation** (accuracy, loss).
- **Feature importance analysis** using **SHAP**.

By incorporating **explainability tools**, we gained valuable insights into **how the model makes decisions**, ensuring it remains **interpretable and robust**
