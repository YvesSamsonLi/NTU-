# **Camp Application and Management System (CAMs)**
## SC2002: Object-Oriented Design & Programming (AY 2023/2024)

### **Overview**
The **Camp Application and Management System (CAMs)** is an object-oriented (OOP) project designed to facilitate **camp-related activities** for both **staff and students**. The system allows users to manage, register, and oversee various camp functionalities through a **command-line interface (CLI)**. 

This project applies key **OOP principles, SOLID design principles, and software engineering best practices** to ensure **scalability, maintainability, and extensibility**.

🔗 **Demonstration Video**: [Watch Here](https://youtu.be/cFJeNSEB_mM)  

---

## **Features**
- **User Management**: Registration, authentication, and profile updates.
- **Camp Management**: Creation, editing, deletion, and visibility toggling.
- **Role-based Access**:
  - **Staff**: Oversee camp activities, approve suggestions, manage attendees, generate reports.
  - **Student Camp Attendees**: Register for camps, withdraw, and submit inquiries.
  - **Student Camp Committee Members**: Manage suggestions, reply to inquiries, and assist in camp organization.
- **Report Generation**: Exporting participant details and performance evaluations in **CSV or TXT formats**.
- **Enquiry System**: Students can send and edit inquiries, while committee members and staff can respond.
- **Sorting & Searching**: Camps can be sorted or filtered based on different criteria.
- **Data Persistence**: User and camp data are initialized via **file uploads**.

---

## **Object-Oriented Programming (OOP) Concepts Used**
The project is structured using fundamental **OOP principles**:

### **1. Encapsulation**
Encapsulation ensures that class data is hidden and only accessible via **public getter and setter methods**.
- **Example**:  
  - Variables in classes use **private access modifiers**, preventing unauthorized modification.
  - Methods such as `getTotalSlots()` in `Camp.java` control how data is accessed and modified.

### **2. Polymorphism**
Polymorphism enables different classes to be treated as instances of a common base class.
- **Example**:
  - `Staff` and `Student_User` classes both extend a common **User** class.
  - Different types of **camp sorting methods** (e.g., `SortByLocation`) use the same **Sorting()** method but provide different implementations.

### **3. Abstraction**
Abstraction ensures that only essential details are exposed while hiding implementation details.
- **Example**:
  - `Camp.java` contains all **camp-related attributes** but exposes only **relevant methods** like `getTotalSlots()`, hiding unnecessary implementation details.
  - Front-end controllers such as `account_manager.java` interact with `Student_User.java` without needing to know internal workings.

### **4. Inheritance**
Inheritance allows new classes to derive functionality from existing classes, promoting code reusability.
- **Example**:
  - `Committee.java` inherits from `Student_User.java` and adds **committee-specific functionalities** like a point system.
  - `Attendee.java` and `Committee.java` extend from `Student_User.java`, ensuring a **hierarchical structure**.

---

## **SOLID Design Principles Applied**
The project adheres to **SOLID** principles to maintain clean and modular code.

### **1. Single Responsibility Principle (SRP)**
Each class is responsible for **only one functionality**, making modifications easier.
- **Example**:
  - `CSVWriter.java` handles **only** exporting data to `.csv`.
  - `Enquiry.java` and `Suggestion.java` classes are **solely responsible** for handling enquiries and suggestions.

### **2. Liskov Substitution Principle (LSP)**
Subtypes must be **interchangeable** with their base types without issues.
- **Example**:
  - `Attendee` and `Committee` subclasses extend `Student_User` and **can be used interchangeably** without breaking the system.

### **3. Interface Segregation Principle (ISP)**
Interfaces are designed to be **specific**, preventing unnecessary dependencies.
- **Example**:
  - **Sorting and searching** are handled separately:
    - `DisplaybySort` class implements sorting (`SortByLocation`, etc.).
    - `DisplaybySearch` class implements searching (`SearchByKeyword`, etc.).

### **4. Open-Closed Principle (OCP)**
The system is **open for extension** but **closed for modification**, allowing new features without altering existing code.
- **Example**:
  - `SortApp` guides users through sorting camps using a **switch statement**, where new sorting methods can be **added as subclasses** without modifying the existing logic.

### **5. Dependency Inversion Principle (DIP)**
Higher-level modules do not depend on lower-level modules; both depend on abstractions.
- **Example**:
  - **Report generation** is handled by:
    - `GenerateReport` interface
    - `CommitteeGenerateReport.java` for committee members
    - `StaffGenerateReport.java` for staff
  - This abstraction ensures flexibility when modifying report functionalities.

---

## **Scalability and Maintainability**
### **1. Extensibility**
- Adding new user roles (e.g., `Admin`) or **new actions** (e.g., `CampFeedback`) is easy because of **OOP design patterns**.
- New **sorting and filtering** functionalities can be introduced **without modifying the existing structure**.

### **2. Maintainability**
- The **modular approach** ensures easy debugging and testing.
- The **separation of concerns** (e.g., `Camp.java` only handles camp data) improves **code readability** and reusability.

---

## **Key Classes and Responsibilities**
| **Class**                  | **Description** |
|----------------------------|---------------|
| `User.java`                 | Base class for `Student_User` and `Staff` |
| `Student_User.java`         | Represents a generic student user |
| `Attendee.java`             | Subclass of `Student_User`, handles attendee-specific actions |
| `Committee.java`            | Subclass of `Student_User`, manages committee members |
| `Staff.java`                | Staff users can create and manage camps |
| `Camp.java`                 | Stores details about a camp (name, slots, attendees, etc.) |
| `Enquiry.java`              | Handles student inquiries about camps |
| `Suggestion.java`           | Manages suggestions from students |
| `DisplayBySort.java`        | Abstract class for sorting camps |
| `SearchByKeyword.java`      | Implements keyword-based searching |
| `GenerateReport.java`       | Interface for generating reports |
| `CommitteeGenerateReport.java` | Implements committee report generation |
| `StaffGenerateReport.java`  | Implements staff report generation |

---

## **UML Diagrams**
The UML diagrams detailing the **class relationships, inheritance structure, and system flow** can be found in the project folder. The diagrams illustrate:
1. **Class Diagram** (Relationships between classes)
2. **Use Case Diagram** (User interactions)
3. **Sequence Diagram** (Execution flow)

---

## **Installation & Usage**
### **Prerequisites**
- Java Development Kit (JDK) 8 or higher
- IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code)

### **How to Run**
1. **Clone the Repository**  
   ```sh
   git clone https://github.com/huaizhic/OOP-camp-management-system
   cd OOP-camp-management-system
   ```
2. **Compile the project**  
   ```sh
   javac *.java
   ```
3. **Run the main program**  
   ```sh
   java Main
   ```

---

## **Testing & Validation**
The project includes comprehensive **test cases** to validate system functionalities. The test cases cover:
- **Staff operations** (e.g., creating, modifying, deleting camps)
- **Student functionalities** (e.g., registering, withdrawing, submitting enquiries)
- **Committee management** (e.g., generating reports, handling suggestions)
- **Authentication & authorization**
- **Data persistence & retrieval**

---

## **Future Enhancements**
- **Graphical User Interface (GUI)** instead of CLI.
- **Database integration** for persistent storage.
- **More user roles** (e.g., Event Organizer, Volunteer).
- **Automated email notifications** for camp updates.

---

## **Contributors**
- **Tian Qiuzaicheng**
- **Yves Samson Li**
- **Chong Huai Zhi**
- **Lee Jing Yang, Joshua**
- **Tan Jing Han Chad**
