# Little Timmy - Autonomous Robot Project

## 📌 Overview
In January 2024, a team of eight convened in **Software Lab 1** of the **SCSC building** to enhance "Little Timmy," an **autonomous mobile robot**. The project focused on optimizing both hardware and software across multiple subsystems:

- **STM32 (Microcontroller Programming & Embedded Systems)**
- **Android App Development (Robot Interface & Control)**
- **Raspberry Pi (Processing & Communications)**
- **Image Recognition (Computer Vision & Machine Learning)**
- **Algorithm Design (Pathfinding & Optimization)**

The final system was capable of autonomously **traversing a predefined arena, detecting obstacles, and optimizing its path** using advanced computational models.

---
## 🏁 Task Breakdown
### **Task 1: Autonomous Navigation & Image Recognition**
Task 1 required **Little Timmy** to move autonomously within a **predefined arena** while completing the following objectives:

1. **Obstacle Avoidance**: The robot navigates through an environment populated with obstacles and dynamically adjusts its path.
2. **Image Recognition**: Using **computer vision and machine learning**, the robot must detect and classify **predefined markers** placed at strategic locations.
3. **Communication with External Systems**:
   - Receives navigation and obstacle data from **STM32 sensors**.
   - Transmits image data to a **laptop-based processing unit** via **Raspberry Pi**.
4. **Efficient Route Execution**: Navigation is performed using a **modified Hamiltonian path algorithm**, ensuring each critical point is visited with **minimal travel cost**.

### **Task 2: Optimized Path Execution (Fastest Route)**
Task 2 focused on optimizing **Little Timmy’s movement efficiency** by computing the **fastest possible path** while avoiding obstacles and reducing sharp turns.

1. **Fastest Path Execution**:
   - A **Modified A* Algorithm** was implemented to minimize the **overall travel time**.
   - The cost function integrated **turn penalties**, prioritizing **straight-line movement**.
2. **Real-Time Decision Making**:
   - Sensor data dynamically updated **the robot’s route**.
   - FreeRTOS-enabled **multi-threaded execution** allowed simultaneous navigation and decision-making.
3. **Higher Speed with Stability**:
   - **PID-based motor control** adjusted acceleration and braking to prevent overshooting.
   - **Interrupt-driven processing** ensured minimal delay in response to environmental changes.

---
## ⚙️ System Architecture Breakdown
Each component was carefully engineered to contribute to the **autonomous navigation system**, ensuring real-time performance, robustness, and modularity.

### 🛠 **1. STM32 (Low-Level Embedded System Control)**
The **STM32 microcontroller** was responsible for **real-time motor control, sensor integration, and serial communication** with the **Raspberry Pi**.

#### **Core Features**:
- **Motor Control**:
  - **PWM (Pulse Width Modulation)** used for precise speed regulation.
  - **PID Control Algorithm** enabled smooth acceleration and deceleration.
- **Sensor Integration**:
  - **IMU (MPU6050)** for detecting **tilt, acceleration, and orientation**.
  - **Ultrasonic Sensors** provided **real-time obstacle detection**.
  - **IR Sensors** allowed **precise alignment with the arena boundaries**.
- **UART Communication**:
  - Bi-directional data exchange with the **Raspberry Pi**.
  - **Real-time status updates** on motor position, sensor readings, and system health.
- **Real-Time Task Execution**:
  - **FreeRTOS Multi-threading** handled concurrent sensor polling and motor adjustments.
  - **Interrupt-driven processing** ensured minimal latency for critical tasks.

📂 **STM32 Repository Structure**
```plaintext
stm32/
├── inc/                  # Header files (Function declarations, macros, data structures)
│   ├── imu.h             # IMU sensor definitions
│   ├── motors.h          # Motor control logic
│   ├── sensors.h         # Obstacle detection handling
│   ├── uart.h            # UART communication handlers
│   ├── freertos.h        # FreeRTOS configuration
├── src/                  # Source files (Function implementations)
│   ├── main.c            # System initialization
│   ├── motors.c          # Motor control functions
│   ├── imu.c             # IMU integration logic
│   ├── sensors.c         # Sensor management
│   ├── uart.c            # UART communication implementation
│   ├── freertos_tasks.c  # Task scheduling in FreeRTOS
```

---
### 💻 **2. Raspberry Pi (High-Level Processing & Communications)**
The **Raspberry Pi** acted as the **central processing unit**, managing **real-time image processing, pathfinding, and communication**.

#### **Core Features**:
- **Multi-threaded Execution**:
  - Efficient task execution using **multi-threading and multiprocessing**.
- **Data Handling & Communication**:
  - Aggregated sensor data and sent navigation updates to STM32.
  - Managed communication with the **Android application via Bluetooth**.
- **Image Transmission & Recognition**:
  - Captured images from the onboard camera.
  - **Transferred images to a laptop** for advanced machine learning processing.

📂 **Raspberry Pi Repository Structure**
```plaintext
raspberry_pi/
├── server/               # Main RPi server scripts
│   ├── rpi_server.py     # Central communication hub
│   ├── sensor_processing.py # Handles sensor input
│   ├── motor_controller.py # Sends movement commands to STM32
│   ├── image_transfer.py  # Sends image data to the laptop
├── client/               # Remote Android communication
│   ├── rpi_client.py     # Connects with Android controller
```

---
### 🤖 **3. Image Recognition (Computer Vision & Machine Learning)**
This module handled **image detection, classification, and decision-making** based on **predefined training datasets**.

#### **Core Features**:
- **Machine Learning Pipeline**:
  - Utilized **TensorFlow/PyTorch** for training image classifiers.
- **Efficient Image Recognition**:
  - Used **OpenCV-based contour detection** for rapid classification.
  - Optimized **bounding box detection** to work within the **Raspberry Pi’s constraints**.

📂 **Computer Vision Repository Structure**
```plaintext
computer_vision/
├── dataset/              # Image dataset for training
├── model/                # Pre-trained model files
├── train_model.py        # ML training pipeline
├── detect.py             # Real-time object detection
```

---
## 🎯 **Final Outcome**
- **Little Timmy successfully completed both Task 1 and Task 2**.
- **Real-time sensor feedback** allowed precise movement adjustments.
- **Optimized path planning algorithms** resulted in efficient travel routes.
- **Integrated communication between STM32, Raspberry Pi, and Android Controller**.

📽️ **Video Demonstration**: [[Link to Project Video](https://youtu.be/beqk203k624)]
