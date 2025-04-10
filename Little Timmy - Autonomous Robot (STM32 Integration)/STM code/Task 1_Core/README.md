# Little Timmy - STM32 Task 1 Module

## Overview
The **STM32 Task 1 Module** in the **Little Timmy Project** is responsible for handling **motor control, sensor integration, and UART communication** with the Raspberry Pi (RPi). The module ensures accurate motion execution and obstacle detection through IMU sensors, FreeRTOS task scheduling, and servo-controlled steering.

## 🏗️ Project Structure
```plaintext
stm_task1/
├── inc/                  # Header files (function declarations, macros, structs)
│   ├── comm.h            # UART communication structure
│   ├── imu.h             # IMU sensor definitions
│   ├── motors.h          # Motor control definitions
│   ├── oled.h            # OLED display function headers
│   ├── servo.h           # Servo motor function headers
│   ├── stm32f4xx_hal.h   # STM32 HAL configuration
│   ├── freertos.h        # FreeRTOS configuration headers
│   ├── system_stm32f4xx.h # System clock and setup headers
├── src/                  # Source files (main logic and function implementations)
│   ├── comm.c            # UART communication logic
│   ├── imu.c             # IMU sensor integration logic
│   ├── motors.c          # Motor control and PID regulation
│   ├── oled.c            # OLED display functions for debugging
│   ├── servo.c           # Servo motor control logic
│   ├── main.c            # Main STM32 initialization
│   ├── stm32f4xx_it.c    # Interrupt handling for STM32F4
├── startup/              # Boot and system initialization files
│   ├── startup_stm32f407vetx.s  # Startup assembly code
│   ├── system_stm32f4xx.c # System configuration and clock setup
│   ├── syscalls.c        # Low-level system call implementations
│   ├── sysmem.c          # STM32 system memory management
```

## 📌 Features
- **PID Motor Control**: Uses **PWM-based PID control** for precise speed regulation.
- **IMU Sensor Integration**: Reads acceleration and gyroscope data via **I2C**.
- **FreeRTOS Task Management**: Handles multiple concurrent tasks efficiently.
- **UART Communication**: Sends and receives data from **RPi** over serial communication.
- **OLED Display Debugging**: Displays real-time status updates and sensor values.
- **Servo-Based Steering**: Adjusts direction based on pathfinding commands.
- **Interrupt Handling**: Uses **stm32f4xx_it.c** to manage system interrupts and faults.
- **System Memory Management**: Manages heap allocation via **sysmem.c**.

## 🔍 Folder Breakdown
### 📂 `inc/` (Include Folder)
- Stores **header files** (`.h`) that declare **functions, macros, and data structures**.
- Helps separate function **declarations** from their implementations.

### 📂 `src/` (Source Folder)
- Contains **C source files** (`.c`) with **function implementations**.
- Includes logic for motor control, UART communication, OLED handling, etc.

### 📂 `startup/` (System Initialization Folder)
- Includes **system setup** files (`startup_stm32f407vetx.s`, `system_stm32f4xx.c`).
- Handles **memory allocation (`sysmem.c`) and system calls (`syscalls.c`)**.
- Sets up **clock configurations, interrupt vectors, and low-level system functions**.

## 🔗 Communication Workflow
### 1️⃣ **Motor Control & PID Regulation**
- **PID-based speed control** ensures smooth movement.
- Uses **quadrature encoders** for precise tracking.
- Motor control structure (`MotorData`):
  ```c
  typedef struct {
      uint8_t suspend;
      uint8_t dir;
      uint32_t pwmVal;
  } MotorData;
  ```
- Example function to move a motor:
  ```c
  void mtrA_mov(uint8_t direction, uint16_t speed);
  ```

### 2️⃣ **IMU Sensor Handling**
- **Reads real-time orientation** via **MPU6050**.
- Converts raw data into **acceleration and gyro measurements**.
- Function to read acceleration:
  ```c
  float read_accel_x();
  float read_accel_y();
  float read_accel_z();
  ```

### 3️⃣ **UART Communication (RPi ↔ STM32)**
- STM32 **receives movement commands** from RPi.
- Sends **acknowledgments (ACK) and error messages**.
- Example structured message:
  ```c
  typedef struct {
      uint8_t id;
      uint8_t type;
      int16_t val;
  } Instruction;
  ```

### 4️⃣ **OLED Display & Debugging**
- Used for real-time **debugging and error reporting**.
- Displays messages about **system state and connectivity**.
- Example function to display text:
  ```c
  void OLED_ShowString(uint8_t x, uint8_t y, const uint8_t *p);
  ```

### 5️⃣ **Servo Motor Control**
- Controls steering **using PWM adjustments**.
- Function to set direction:
  ```c
  void turnServo(uint8_t target);
  ```
- Example usage:
  ```c
  turnServo(STRAIGHT);
  ```

## 🛠️ Configuration (freertos.c)
```c
#define configUSE_PREEMPTION   1
#define configMAX_PRIORITIES   56
#define configTICK_RATE_HZ     1000
#define configTOTAL_HEAP_SIZE  15360
#define configUSE_TIMERS       1
```

## 🚀 Running Task 1
### 📌 Flashing the STM32 Firmware
```bash
st-flash write firmware.bin 0x8000000
```

### 📌 Running with FreeRTOS
- **FreeRTOS Tasks** execute concurrently.
- Motor control, IMU, and UART **run as separate threads**.

## 🎯 Final Outcome
- **Accurate motion control** using PID-based regulation.
- **Seamless communication** with Raspberry Pi via UART.
- **Integrated sensor data** for movement tracking and steering adjustments.
