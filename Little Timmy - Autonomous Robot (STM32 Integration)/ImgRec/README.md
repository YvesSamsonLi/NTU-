# Little Timmy - Image Recognition Module

## Overview
The **Image Recognition Module** in the **Little Timmy Project** is responsible for detecting and classifying objects using **YOLOv5**. It integrates a **Flask-based API**, allowing real-time image processing and command generation for obstacle detection.

## 🏗️ Project Structure
```plaintext
image_recognition/
├── datasetGet.py    # Handles dataset retrieval from Roboflow
├── main.py          # Main API server for image recognition
├── model.py         # YOLOv5 model loading and prediction logic
├── test.py          # Script for testing API endpoints
├── uploads/         # Stores uploaded images for processing
├── runs/            # Stores detection results
```

## 📌 Features
- **Object Detection** using **YOLOv5**.
- **Flask API** for real-time image classification.
- **Roboflow Integration** to retrieve datasets.
- **Image Stitching** for combining multiple detections.
- **Task-Specific Models** for improved performance.

## 📥 Dataset Retrieval
Dataset is retrieved from **Roboflow** and used for model training.
```python
from roboflow import Roboflow
rf = Roboflow(api_key="your_api_key")
project = rf.workspace("mdp2024ir").project("mdp2024imgrec")
dataset = project.version(13).download("yolov5")
```

## 🧠 Model Execution
**YOLOv5** is used for real-time image detection. The model is loaded from local weights:
```python
import torch
model = torch.hub.load('./', 'custom', path='Weights/task2.pt', source='local')
model = model.to(torch.float)
```

### 🖼️ Image Prediction for Task 2
```python
def predict_image_task_2(image, model):
    img = Image.open(os.path.join('uploads', image))
    results = model(img)
    df_results = results.pandas().xyxy[0]
    df_results = df_results.sort_values('bboxArea', ascending=False)
    pred = df_results.iloc[0] if not df_results.empty else 'NA'
    return str(pred['name']) if isinstance(pred, dict) else 'NA'
```

### 🖼️ Image Prediction for Task 1
```python
def predict_image(image, model):
    img = Image.open(os.path.join('uploads', image))
    results = model(img)
    df_results = results.pandas().xyxy[0]
    pred = df_results.iloc[0] if len(df_results) > 0 else 'NA'
    return str(pred['name']) if isinstance(pred, dict) else 'NA'
```

## 🌐 API Endpoints
The **Flask API** provides real-time image classification:

### 1️⃣ **Check Server Status**
```http
GET /status
```
#### Response:
```json
{"result": "ok"}
```

### 2️⃣ **Predict Image**
```http
POST /image
```
#### Request Body:
- `file`: Image file to be processed.
#### Response:
```json
{"image_id": "38"}
```

### 3️⃣ **Stitch Images**
```http
GET /stitch
```
#### Response:
```json
{"result": "ok"}
```

## 🔍 Testing API
To validate the API, use `test.py`:
```python
import json, requests
api_port = "5000"
url = f"http://localhost:{api_port}/image"
response = requests.post(url, files={"file": open("test1.jpg", 'rb')})
print(response.json())
```

## 🚀 Running the Image Recognition Server
Run the **Flask API**:
```bash
python main.py
```

## 🎯 Final Outcome
- Successfully detects and classifies obstacles.
- API integration enables **real-time processing**.
- Improved **task-based model performance**.
