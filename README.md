# auth-service - README.md

# 🛡️ Auth Service – Spring Boot + MongoDB

A simple authentication microservice built with **Spring Boot** and **MongoDB**. It manages `Employee` users with roles, departments, and credentials.

---

## 🚀 Features

- Employee user model
- MongoDB integration
- Admin user seeding on startup
- BCrypt password hashing
- Ready to extend for JWT or session-based auth

---

## 📦 Tech Stack

- Java 17+
- Spring Boot 3+
- MongoDB
- Spring Data MongoDB
- Spring Security (for password hashing only)

---

## ✅ Prerequisites

- Java 17+
- Maven
- MongoDB installed and running locally on `localhost:27017`

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

git clone <your-repo-url>
cd auth_service


### 2. Build the Project

mvn clean install

### 3. Run the Application

mvn spring-boot:run

### 4. Access the app on
http://localhost:8000

### 5. Admin User Seeding
When the app starts, it checks if an admin user exists. If not, it creates one with the following:

Email: admin@company.com

Password: admin123 (hashed using BCrypt)

Role: ADMIN

You can find this logic in:

src/main/java/com/africahr/auth_service/config/DataSeeder.java

Change the email or password in this class if you want to customize it.
