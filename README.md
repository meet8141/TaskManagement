# 📌 Task Management System (TMS)

## 📖 Project Overview
The **Task Management System (TMS)** is a **Java + MySQL** based project developed as part of Semester-2 coursework.  
It helps users manage their tasks efficiently with features like task creation, assignment, logging, authentication, and encryption for secure data handling.

This project demonstrates:
- **Object-Oriented Programming (OOP)**
- **JDBC integration with MySQL**
- **Manual Queue implementation**
- **Basic security with encryption**

---

## 🚀 Features
- 🔑 **User Authentication**
  - Registration & Login
  - Encrypted password storage  
- 📋 **Task Management**
  - Add, update, remove tasks
  - Assign tasks to users
  - Prioritize tasks (`Low`, `Medium`, `High`)
  - Track status (`Pending`, `In Progress`, `Completed`)
- 📝 **Task Logs**
  - Record of every task action performed  
- 👥 **Assignment Requests**
  - Users can request task assignment  
- 📂 **Queue System**
  - Manual **TaskQueue** (no built-in Java collections)  
- 🔒 **Encryption & Decryption**
  - For sensitive user data  
- 🗄 **Database Integration**
  - MySQL relational database backend  

---

## 🛠 Tech Stack
- **Language:** Java (JDK 8 or above)  
- **Database:** MySQL 8.0+  
- **Connectivity:** JDBC  
- **Tools/IDE:** IntelliJ IDEA / Eclipse / NetBeans  
- **Version Control:** Git  

---

## 📂 Project Structure

<pre>├── AuthService.java             # Handles user authentication
├── DBConnection.java            # Database connection utility
├── Encryption_Decryption.java   # Provides encryption & decryption
├── OperationManager.java        # Main logic for task operations
├── Task.java                    # Task entity class
├── TaskQueue.java               # Manual queue implementation for tasks
├── TaskLog.java                 # Records task logs
├── User.java                    # User entity class
├── TMSApp.java                  # Entry point (main program)
└── taskmanagement.sql           # Database schema & sample data</pre>


## 🗄 Database Schema

Main tables in taskmanagement database:

- **users** → Stores user credentials & details

- **tasks** → Contains tasks with priority, status, due date, and assigned user

- **tasklogs** → Keeps a record of actions performed on tasks

- **assignmentrequests** → Stores task assignment requests

👉 Schema available in [`taskmanagement.sql`](./taskmanagement.sql)

---

## ⚙️ Setup Instructions

- **Clone the project**
<pre>git clone https://github.com/meet8141/TaskManagement.git
cd TaskManagement </pre>


- **Import Database**
   - Open MySQL Workbench
   - Run the script from taskmanagement.sql

- **Configure Database Connection**
   - Open DBConnection.java
   - Update database credentials:

     <img width="708" height="152" alt="image" src="https://github.com/user-attachments/assets/15f79ddd-2a00-416e-9b93-09a24562dde3" />

- **Compile & Run**


     <img width="753" height="129" alt="image" src="https://github.com/user-attachments/assets/84d2a21b-3095-4101-8beb-ddbb81a2a8d4" />

---
     
## 📖 Usage

- Login/Register as a new user

- Create Tasks with name, description, priority, due date

- Assign Tasks to users

- View Tasks grouped by due date or status

- Mark Tasks as completed or remove them

- Track Logs of all task operations

---

## 🔮 Future Enhancements

- Add Graphical User Interface (GUI)

- Email notifications for due tasks

- Role-based access (Admin, User, Manager)

- Export tasks and logs to CSV/PDF

---

## 👨‍💻 Author

### Meet Patel
### 📧 [meetpatel814116@gmail.com](mailto:meetpatel814116@gmail.com)
### 🎓 Semester-2 Student | AIML Branch
