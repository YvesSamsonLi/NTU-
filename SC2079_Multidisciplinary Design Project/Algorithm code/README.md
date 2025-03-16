# Little Timmy - Algorithm Module

## Overview
The **Algorithm Module** of the **Little Timmy Project** is responsible for computing optimized paths for the robot and executing commands for navigation and obstacle avoidance. The system is built using Python and integrates with the **Raspberry Pi**, **STM32**, and an **Android application**.

## 🏗️ Project Structure
```plaintext
algorithm/
├── main.py            # Entry point for the algorithm execution
├── settings.py        # Configuration settings for grid, pathfinding, and hardware connections
├── test_rpi.py        # Test script for Raspberry Pi communication
├── app.py             # Core application logic for algorithm execution and GUI simulation
├── entities/
│   ├── assets/
│   │   ├── direction.py       # Defines directions (e.g., LEFT, RIGHT, FORWARD, BACKWARD)
│   ├── commands/
│   │   ├── straight_command.py  # Handles straight movement
│   │   ├── turn_command.py      # Handles turning actions
│   ├── connection/
│   │   ├── rpi_client.py        # Handles Raspberry Pi client communication
│   │   ├── rpi_server.py        # Handles Raspberry Pi server setup
│   ├── grid/
│   │   ├── grid.py              # Defines the grid environment
│   │   ├── obstacle.py          # Defines obstacles on the grid
│   │   ├── position.py          # Defines the robot's position
│   ├── robot/
│   │   ├── robot.py             # Core robot logic (movement, pathfinding, execution)
│   ├── brain/
│   │   ├── pathfinding.py       # Implements Hamiltonian Path and A* Algorithm
│   │   ├── commands.py          # Converts movement logic to executable commands
```

## 📌 Features
- **Path Optimization**: Uses **Hamiltonian Path Algorithm** and a **Modified A* Algorithm** to determine efficient routes.
- **Obstacle Handling**: Dynamically processes obstacles received from **Raspberry Pi sensors**.
- **Command Execution**: Converts movement commands into STM32-compatible signals.
- **Simulation & Debugging**: Provides a **graphical simulator** (using **Pygame**) for testing navigation.

## 🛠️ Pathfinding Algorithms
### 1️⃣ **Hamiltonian Path Algorithm**
- Determines an **optimal sequence** for visiting all obstacles.
- Ensures **no unnecessary revisits**, minimizing travel distance.

### 2️⃣ **Modified A* Algorithm**
- Computes the **least-cost path** based on Manhattan Distance:
  \[ d = |x_1 - x_2| + |y_1 - y_2| \]
- Applies **penalties to turns** to favor straight paths.

### Path Execution Logic
- The algorithm receives obstacle data from the **Raspberry Pi**.
- Converts it into an optimized sequence.
- Generates a **command list** formatted for **STM32 execution**.
- Sends movement instructions to the **robot**.

## 🔗 Communication Workflow
### 1️⃣ **Receiving Data from Raspberry Pi**
- `RPiServer` listens for obstacle data.
- `RPiClient` establishes a connection to send messages.
- Obstacle positions are formatted into a **standardized list**.

### 2️⃣ **Executing Navigation Commands**
- Commands follow a structured format:
  ```plaintext
  [Action Type, Direction, Distance, Turn Angle]
  ```
  **Example Commands:**
  ```plaintext
  [1, 0, 90]    # Move forward 90 units
  [2, 1, -90]   # Turn left
  [3, 0, 10]    # Move forward 10 units
  ```
- Commands are **executed sequentially** until reaching the final destination.

## 🚀 Running the Algorithm
### 📌 Running the Simulator
```bash
python main.py
```
This starts the **GUI-based simulator** where the robot navigates an arena.

### 📌 Running with Raspberry Pi
```bash
python test_rpi.py
```
This initializes communication with **Raspberry Pi** and simulates real-world execution.

## ⚙️ Configuration (settings.py)
The `settings.py` file defines important parameters:
```python
SCALING_FACTOR = 4  # Grid scaling factor
FRAMES = 60  # Simulation FPS
WINDOW_SIZE = (800, 800)  # Display size

RPI_HOST = "192.168.23.23"  # Raspberry Pi Host
RPI_PORT = 56472  # Raspberry Pi Port

ROBOT_SPEED_PER_SECOND = 50 * SCALING_FACTOR
ROBOT_LEFT_TURN_RADIUS = 19 * SCALING_FACTOR
ROBOT_RIGHT_TURN_RADIUS = 27 * SCALING_FACTOR
```

## 🎯 Final Outcome
- Successfully computed and executed **optimized paths**.
- Integrated **Raspberry Pi communication** for real-world operation.
- Developed a **graphical simulation environment** for debugging and testing.
