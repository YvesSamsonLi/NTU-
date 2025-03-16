# Little Timmy - STM32 Task 2 Module

## Overview
The **STM32 Task 2 Module** in the **Little Timmy Project** is responsible for handling **motor control, sensor feedback processing, and communication with the Raspberry Pi (RPi)**. Task 2 includes advanced motion execution, real-time FreeRTOS-based processing, and precise steering using servos.

## 🏗️ Project Structure
```plaintext
stm_task2/
├── inc/                  # Header files (function declarations, macros, and structures)
│   ├── comm.h            # UART communication structure
│   ├── imu.h             # IMU sensor definitions
│   ├── motors.h          # Motor control definitions
│   ├── oled.h            # OLED display function headers
│   ├── oledfont.h        # Font definitions for OLED display
│   ├── servo.h           # Servo motor function headers
│   ├── stm32f4xx_hal_conf.h  # HAL configuration file
│   ├── freertos.h        # FreeRTOS configuration headers
├── src/                  # Source files (main logic and function implementations)
│   ├── comm.c            # UART communication logic
│   ├── imu.c             # IMU sensor integration logic
│   ├── motors.c          # Motor control and PID tuning
│   ├── oled.c            # OLED display functions for debugging
│   ├── oledfont.c        # Font implementation for OLED display
│   ├── servo.c           # Servo motor control logic
│   ├── main.c            # Main STM32 initialization
│   ├── stm32f4xx_it.c    # Interrupt handling for STM32F4
```

## 📌 Features
- **Advanced PID Motor Control**: Utilizes **PWM-based PID regulation** for smooth movement.
- **IMU Sensor Feedback**: Reads **real-time acceleration and gyroscope data** to improve stability.
- **FreeRTOS Multitasking**: Efficient **concurrent execution of motor control, communication, and sensor processing**.
- **UART Communication**: Handles real-time **data exchange with RPi** for autonomous operations.
- **OLED Debugging Interface**: Displays system state and debugging logs.
- **Servo-Based Steering**: Implements **precise steering** adjustments based on obstacle detection.
- **Interrupt-Based Control**: Uses **stm32f4xx_it.c** for fast response times.

## 🔍 Folder Breakdown
### 📂 `inc/` (Include Folder)
- Stores **header files (`.h`)** that define function prototypes, macros, and data structures.
- This helps maintain **modular and reusable code** by separating **function declarations** from their implementations.
- Ensures that **different source files can include necessary functions** without duplicating definitions.

### 📂 `src/` (Source Folder)
- Contains **C source files (`.c`)** implementing **functionality and logic**.
- Includes the **motor control**, **UART communication**, **IMU handling**, and **OLED display logic**.
- Each `.c` file corresponds to a related `.h` file in `inc/`, ensuring a **clear separation of concerns**.

## 🔗 Communication Workflow
### 1️⃣ **Motor Control & PID Tuning**
- **PID-based speed control** for accurate motion execution.
- Uses **encoders** for real-time speed tracking.
- Motor control structure:
  ```c
  typedef struct {
      uint8_t suspend;
      uint8_t dir;
      uint32_t pwmVal;
  } MotorData;
  ```
- Function to adjust speed:
  ```c
  void mtrA_mov(uint8_t direction, uint16_t speed);
  ```

### 2️⃣ **IMU Sensor Processing**
- Uses **MPU6050 IMU sensor** for detecting angular velocity.
- Function to read accelerometer values:
  ```c
  float read_accel_x();
  float read_accel_y();
  float read_accel_z();
  ```
- Function to read gyroscope values:
  ```c
  float read_gyro_x();
  float read_gyro_y();
  float read_gyro_z();
  ```

### 3️⃣ **UART Communication with RPi**
- Receives movement instructions and transmits acknowledgment packets.
- Structured message example:
  ```c
  typedef struct {
      uint8_t id;
      uint8_t type;
      int16_t val;
  } Instruction;
  ```

### 4️⃣ **OLED Debugging**
- Displays real-time **error messages, connectivity status, and sensor data**.
- Example function to display text:
  ```c
  void OLED_ShowString(uint8_t x, uint8_t y, const uint8_t *p);
  ```

### 5️⃣ **Servo Motor Control**
- Steering adjustments using **servo motor**:
  ```c
  void turnServo(uint8_t target);
  ```
- Example usage:
  ```c
  turnServo(STRAIGHT);
  ```

## 🛠️ FreeRTOS Configuration (freertos.c)
```c
#define configUSE_PREEMPTION   1
#define configMAX_PRIORITIES   56
#define configTICK_RATE_HZ     1000
#define configTOTAL_HEAP_SIZE  15360
#define configUSE_TIMERS       1
```

## 🚀 Running Task 2
### 📌 Flashing the STM32 Firmware
```bash
st-flash write firmware.bin 0x8000000
```

### 📌 Running with FreeRTOS
- **FreeRTOS Tasks** ensure smooth execution of motor control, IMU processing, and UART communication.

## 🎯 Final Outcome
- **Accurate movement and obstacle detection** using sensor feedback.
- **Real-time communication** between STM32 and Raspberry Pi.
- **Smooth servo steering adjustments** for precise navigation.
