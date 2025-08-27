Task Management System (TMS)
📌 Project Overview

The Task Management System (TMS) is a Java + MySQL based project developed as part of Semester-2 coursework.
It helps users manage their tasks efficiently with features like task creation, assignment, logging, authentication, and encryption for secure data handling.

This project demonstrates core Object-Oriented Programming (OOP) concepts, database integration using JDBC, and a simple manual queue implementation for managing tasks.

🚀 Features

🔑 User Authentication (Register/Login system with encryption)

📋 Task Management

Add, update, remove, and assign tasks

Prioritize tasks (Low, Medium, High)

Track task status (Pending, In Progress, Completed)

📝 Task Logs (Records all actions performed on tasks)

👥 Assignment Requests (Users can request task assignment)

📂 Queue Implementation (Manual TaskQueue without built-in Java collections)

🔒 Encryption & Decryption for sensitive data

🗄 Database Integration with MySQL

🛠 Tech Stack

Language: Java (JDK 8+)

Database: MySQL 8.0+

Connectivity: JDBC

Tools: IntelliJ IDEA / Eclipse / NetBeans, MySQL Workbench

Version Control: Git

📂 Project Structure
<img width="775" height="333" alt="image" src="https://github.com/user-attachments/assets/46ea60eb-89bf-4a07-a28c-9c266de5772e" />
🗄 Database Schema

Main tables in taskmanagement database:

users → Stores user credentials & details

tasks → Contains tasks with priority, status, due date, and assigned user

tasklogs → Keeps a record of actions performed on tasks

assignmentrequests → Stores task assignment requests

👉 Schema available in taskmanagement.sql

⚙️ Setup Instructions

Clone the project
<img width="1258" height="132" alt="image" src="https://github.com/user-attachments/assets/f2d81c17-7f95-448c-b9c9-f7c280e69d80" />
Import Database

Open MySQL Workbench

Run the script from taskmanagement.sql

Configure Database Connection

Open DBConnection.java

Update database credentials:
<img width="708" height="152" alt="image" src="https://github.com/user-attachments/assets/15f79ddd-2a00-416e-9b93-09a24562dde3" />
Compile & Run
<img width="753" height="129" alt="image" src="https://github.com/user-attachments/assets/84d2a21b-3095-4101-8beb-ddbb81a2a8d4" />
📖 Usage

Login/Register as a new user

Create Tasks with name, description, priority, due date

Assign Tasks to users

View Tasks grouped by due date or status

Mark Tasks as completed or remove them

Track Logs of all task operations

🔮 Future Enhancements

Add Graphical User Interface (GUI)

Email notifications for due tasks

Role-based access (Admin, User, Manager)

Export tasks and logs to CSV/PDF

👨‍💻 Author

Meet Patel
📧 [meetpatel814116@gmail.com .]
🎓 Semester-2 Student | AIML Branch
