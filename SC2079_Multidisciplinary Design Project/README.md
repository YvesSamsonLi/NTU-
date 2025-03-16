# Little Timmy - Autonomous Robot Project

## Overview
In January 2024, a team of eight convened in **Software Lab 1 of the SCSC building** to enhance "Little Timmy" the robot. The project involved implementing **hardware and software optimizations**, focusing on the following subsystems:

- **STM32 (Microcontroller Programming & Embedded Systems)**
- **Android App Development (Robot Interface & Control)**
- **Raspberry Pi (Processing & Communications)**
- **Image Recognition (Computer Vision & Machine Learning)**
- **Algorithm Design (Pathfinding & Optimization)**

The team developed an **autonomous mobile robot** capable of traversing a predefined arena, identifying obstacles, and optimizing its path using advanced algorithms.

---
## 📱 Android Application Features
The **Android app** served as the primary control interface for "Little Timmy," enabling remote operations, debugging, and real-time monitoring.

### Key Components:
- **Bluetooth Connection Status**: Displays whether the robot is connected.
- **Arena View**: A graphical representation of the robot’s environment.
- **Start Buttons**: Triggers Task 1 and Task 2 execution.

### Controller Tabs:
- **Main Actions**:
  - Set the robot's **initial position**.
  - Place and clear obstacles.
  - Edit obstacle position and orientation dynamically.
- **Manual Drive**: Allows for **manual override** and direct control of the robot.
- **Settings**:
  - Toggle **Debug Mode**.
  - Send **debugging scripts** to the robot.
- **Chat Section**:
  - Displays **status messages** (e.g., Bluetooth connection, image recognition logs).
  - Allows direct **text-based commands**.
- **Portal Component**:
  - Maintains a list of **paired devices**.
  - Enables quick **debugging and reconnection**.

---
## ⚙️ Algorithm Development
A **simulator** was developed to test the navigation algorithms before deploying them onto the robot.

### Path Optimization Strategies:
1. **Hamiltonian Path Algorithm**
   - Determines the optimal **sequence for visiting obstacles**.
2. **Modified A* Algorithm**
   - Computes the **least-cost path**, incorporating heuristics.
   - Uses the **Manhattan Distance** metric:
     \[ d = |x_1 - x_2| + |y_1 - y_2| \]
   - Penalizes **turns** while encouraging **straight-line movements**.

### Key Adjustments:
- Implemented **higher penalties for turns**.
- Ensured **smooth transitions between waypoints**.
- Prioritized **efficiency in real-time execution**.

---
## 💻 Raspberry Pi Implementation
The **Raspberry Pi (RPi)** acted as the robot's central processing unit, handling **data communication and multi-threaded execution**.

### Technical Features:
- Implemented **multi-threading** and **multi-processing**.
- Optimized task execution by **efficiently switching between child processes**.
- Handled **sensor data aggregation** and **communication with STM32**.

---
## 🕹️ STM32 Challenges and Solutions
The **STM32** microcontroller was responsible for motor control and low-level processing.

### Issues Encountered:
1. **Initial Debugging Issue**
   - Team **overlooked hardware checks**.
   - Spent **two weeks troubleshooting** before realizing it was a **hardware fault**.
   - **Solution**: Replaced the cable.
2. **RPi-STM32 Communication Failure** (3 days before competition)
   - **Issue**: Raspberry Pi failed to communicate with STM32.
   - **Solution**: Debugged the connection, updated firmware, and verified **protocol adherence**.

---
## 📡 Sensor Integration (STM32)
### Problem:
- Sensor integration suffered from **overload connections** and **improper quality settings**.

### Solution:
- Implemented **categorization and prioritization** of sensor data.
- Adjusted **settings for optimized data transmission**.

---
## 🤖 Computer Vision
### Initial Plan:
- Deploy **image recognition model** on Raspberry Pi.
- Process images locally on the Pi.

### Challenge:
- The **RPi lacked computational power** to run image recognition efficiently.

### Solution:
- **Switched to a laptop** for image processing.
- RPi **transmitted images** to the laptop for real-time processing.

---
## 🎯 Final Team Effort & Outcome
The team collaborated to:
- Debug **complex software bugs**.
- **Calibrate sensors** for optimal performance.
- Utilize **resources like Stack Overflow** to troubleshoot issues.
- Successfully complete **both tasks** despite **not ranking in the top ten**.

### Final Result:
- **Little Timmy successfully navigated the arena** and completed its objectives.
- The project provided **valuable insights** into embedded systems, computer vision, and robotics.

---
## 📽️ Video Demonstration
Watch the full **Little Timmy Project Demo** here:
[![Little Timmy Project](https://img.youtube.com/vi/beqk203k624/0.jpg)](https://youtu.be/beqk203k624)

---
## 🛠️ Technical Requirements
### Hardware:
- **STM32 Microcontroller**
- **Raspberry Pi 4B**
- **Android Smartphone** (for app control)
- **Bluetooth Module** (for connectivity)
- **Motor Drivers & Sensors** (IR, Ultrasonic, Camera)

### Software & Libraries:
- **Android Studio** (Java/Kotlin for the app)
- **Python (NumPy, OpenCV, SciPy, Flask)**
- **Embedded C (STM32 Programming)**
- **Linux (Raspberry Pi OS)**
- **Machine Learning (TensorFlow/PyTorch for Image Recognition)**
