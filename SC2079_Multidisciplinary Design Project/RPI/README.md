# Little Timmy - Raspberry Pi (RPi) Module

## Overview
The **Raspberry Pi (RPi) Module** in the **Little Timmy Project** acts as the central hub, handling:
- **Communication** between Android, STM32, and the main PC.
- **Processing Image Recognition requests** by interfacing with a Flask API.
- **Executing movement commands** by relaying instructions to STM32.
- **Handling Bluetooth communication** with the Android app.

## 🏗️ Project Structure
```plaintext
rpi/
├── rpi_client.py         # Handles client-side communication with the main PC
├── rpi_server.py         # Acts as a server to receive commands from the main PC
├── settings.py           # Configuration file for RPi networking and STM32
├── link.py               # Abstract class for communication links
├── Stm32.py              # Handles STM32 serial communication
├── STMConnectionA1_Checklist.py  # RPi-STM32 connection test
├── Task1.py              # Main task execution script
├── Task2.py              # Handles Task 2, including obstacle detection
├── android.py            # Manages Bluetooth communication with Android
├── android_link.py       # Wrapper for AndroidLink class
├── consts.py             # Constant values (symbols, mappings, etc.)
├── logger.py             # Logging utilities
```

## 📌 Features
- **Client-Server Model**: Implements **socket-based communication** between RPi and the main PC.
- **Bluetooth Integration**: Handles **bi-directional messaging** between RPi and Android.
- **STM32 Serial Communication**: Sends **movement commands** and receives **acknowledgments**.
- **Flask API for Image Recognition**: Sends **captured images** for processing.
- **Multi-Process Execution**: Uses Python's `multiprocessing` to run concurrent tasks.

## 🔗 Communication Workflow
### 1️⃣ **Main PC ↔ RPi** (via Sockets)
- `RPiClient` initiates a **socket connection** to the **main PC**.
- `RPiServer` listens for incoming **path planning data**.
- Obstacle data is transmitted, processed, and sent back to RPi.

### 2️⃣ **RPi ↔ STM32** (via Serial UART)
- **RPi sends movement commands** to STM32 (`STMLink`).
- STM32 **acknowledges commands** after execution.
- Commands include **movement, turns, and stopping**.

### 3️⃣ **RPi ↔ Android** (via Bluetooth)
- **RPi receives control commands** from the Android app (`AndroidLink`).
- **Android receives real-time updates** on status and image recognition results.
- Message format:
  ```json
  {"cat": "obstacles", "value": {"obstacles": [{"x": 5, "y": 10, "id": 1, "d": 2}], "mode": "0"}}
  ```

### 4️⃣ **RPi ↔ Image Recognition API** (via HTTP Requests)
- Captured images are **sent to the Image Recognition API**.
- API returns **detected objects and classifications**.
- Example API call:
  ```python
  url = f"http://{API_IP}:{API_PORT}/image"
  response = requests.post(url, files={"file": open("obstacle1.jpg", 'rb')})
  ```

## 🛠️ Configuration (settings.py)
```python
# STM32 BOARD SERIAL CONNECTION
SERIAL_PORT = "/dev/serial/by-id/usb-Silicon_Labs_CP2102_USB_to_UART_Bridge_Controller_0002-if00-port0"
BAUD_RATE = 115200

# IMAGE REC API DETAILS
API_IP = '192.168.23.28'
API_PORT = 5000

# ALGO DETAILS
PC_HOST = '192.168.23.25'
PC_PORT = 57832

RPI_HOST = "192.168.23.23"
RPI_PORT = 56472
```

## 🚀 Running the Raspberry Pi Module
### 📌 Running Task 1 (Path Execution & STM32 Control)
```bash
python Task1.py
```

### 📌 Running Task 2 (Image Recognition & STM32 Integration)
```bash
python Task2.py
```

### 📌 Testing Bluetooth Communication
```bash
python android.py
```

### 📌 Testing Server-Client Communication
#### Start the Server:
```bash
python rpi_server.py
```
#### Start the Client:
```bash
python rpi_client.py
```

## 🎯 Final Outcome
- Successfully established **real-time communication** between **RPi, STM32, and Android**.
- Integrated **image recognition API** for obstacle identification.
- Implemented **multi-threaded execution** for efficient task management.
