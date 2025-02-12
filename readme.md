# EzyRide Backend

The EzyRide Backend is a Spring Boot application with a PostgreSQL database that handles taxi availability and ride request management for a transportation solution at IIIT Nagpur. This repository powers the mobile application built using Flutter, providing seamless communication and real-time updates between the app and the backend.

### My Role:
I played a significant role in implementing the backend services, including User Authentication, Authorization, and Role-Based Access Control (RBAC). I developed the authentication and authorization mechanisms using **JWT** for secure session management and implemented detailed RBAC to restrict access based on user roles. I also integrated the backend with a PostgreSQL database to store user and ride data.

### Key Features:
- User Authentication: Secure user registration, login, and logout processes using JWT (JSON Web Token) for session management. Passwords are securely hashed and stored, ensuring strong protection against unauthorized access.

- Role-Based Access Control (RBAC):
  - Users are assigned roles (e.g., Admin, User, Moderator), and access to specific resources or endpoints is granted based on these roles.
  - Admin users have full access to all endpoints, while User and Moderator roles have restricted access based on permissions.

- Authorization:
  - Access control mechanisms ensure that only users with the correct roles can perform actions or access specific resources. Unauthorized users receive **Access Denied** responses for restricted endpoints.

- JWT Authentication:
  - Used for stateless user sessions across API requests. Tokens are issued after successful login and are required to access protected endpoints.

- Secure Communication:
  - HTTPS is used for secure communication between the client and server to protect sensitive data during transmission.

- Role Permissions:
  - Detailed permissions are implemented for each role to restrict access to specific API resources. Admins can manage users, while Users can view personal data and request rides.

- Redis-Caching:
  - Implemented Redis-caching for faster data retrieval and reduction API requests to the DB, making the system more efficient.
  
- Customizable System:
  - The system is designed to be flexible, allowing for easy addition of new roles and permissions without significant changes to the codebase.

---

Here are the step-by-step instructions on how to download the GitHub code and run it on any device. You can add these instructions to your README file for users to easily set up the project.

Step-by-Step Instructions to Download and Run the EzyRide Backend Project:
Prerequisites
Before you begin, ensure you have the following software installed on your system:
Java 17 or higher - EzyRide Backend is built using Spring Boot, which requires Java.
Maven - Used for building and managing the project.
Database - No need to install any database to run this project, H2-in_memory-database is used in this project which means you can access the database by the following link-
http://localhost:8081/h2-console

(put your port instead of 8081 after running the springboot project)
Git - To clone the repository.
IDE (optional) - You can use any IDE like IntelliJ IDEA, Eclipse, or VS Code for better development experience.
___

Step 1: Clone the Repository
To download the project, you need to clone the GitHub repository to your local machine.

Open your terminal/command prompt.

Use the following command to clone the repository:
git clone https://github.com/your-username/ezyride-backend.git

Step 2: Build the Project
Navigate to the project directory in the terminal.
Run the following command to build the project:
mvn clean install

Step3: Run the Project
Run the Application
mvn spring-boot:run
___
### Swagger UI Documentation:
You can access the API documentation via the Swagger UI at the following link:
http://localhost:8081/swagger-ui/index.html

(put your port instead of 8081 after running the springboot project)

### Project Presentation:
For a detailed overview of the project, including architecture and design decisions, check out the presentation here:
https://drive.google.com/drive/folders/1taq1dB9D5cQKqTJ8uMgrbP0FAgVGgGdM

---
