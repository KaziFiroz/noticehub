# 🚀 NoticeHub - Complete Setup Guide

Follow these steps to run the NoticeHub application on your local machine.

---

## ⚙️ Prerequisites

Before starting, make sure you have these installed:

### Required Software

1. **Java Development Kit (JDK)**
   - Version: Java 17 or higher (Java 25 recommended)
   - Download: https://adoptium.net/temurin/releases/
   - Verify: `java -version`

2. **Apache Maven**
   - Version: 3.9 or higher
   - Download: https://maven.apache.org/download.cgi
   - Verify: `mvn -version`

3. **Node.js and npm**
   - Version: Node.js 16+ and npm 8+
   - Download: https://nodejs.org/
   - Verify: `node -version` and `npm -version`

4. **MySQL Database**
   - Version: 8.0 or higher
   - Download: https://dev.mysql.com/downloads/mysql/
   - Verify: `mysql --version`

5. **IDE/Code Editor** (Optional but recommended)
   - VS Code: https://code.visualstudio.com/
   - IntelliJ IDEA: https://www.jetbrains.com/idea/

---

## 📦 Installation Steps

### Step 1: Extract the Project
```bash
# Extract the ZIP file to your desired location
# Example: D:/Projects/NoticeHub
```

### Step 2: Database Setup

#### 2.1 Start MySQL Server

**Windows:**
- Open Services → Start MySQL80 service

**macOS:**
```bash
brew services start mysql
```

**Linux:**
```bash
sudo systemctl start mysql
```

#### 2.2 Create Database

Open MySQL Command Line or MySQL Workbench:
```sql
-- Login to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE noticehub;

-- Create user (optional)
CREATE USER 'noticehub_user'@'localhost' IDENTIFIED BY 'noticehub_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON noticehub.* TO 'noticehub_user'@'localhost';
FLUSH PRIVILEGES;

-- Verify
SHOW DATABASES;

-- Exit
EXIT;
```

#### 2.3 Set Your Database Credentials (via environment variables)

The app reads DB credentials and the JWT secret from environment variables rather than
a committed file (see the Security section in the main `README.md`). Copy the example
file and export the values before running the backend:

```bash
cd backend/noticehub
cp .env.example .env   # edit .env with your own values, it is git-ignored

export DB_URL="jdbc:mysql://localhost:3306/noticehub_db"
export DB_USERNAME="noticehub_user"
export DB_PASSWORD="your_own_password"
export JWT_SECRET="$(openssl rand -base64 48)"
```

**Note:** never put your real password or JWT secret directly into `application.properties` — that file is committed to git.

---

### Step 3: Backend Setup

#### 3.1 Navigate to Backend Directory
```bash
cd NoticeHub/backend/noticehub
```

#### 3.2 Clean and Build
```bash
# Clean previous builds
mvn clean

# Install dependencies and build
mvn clean install
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

#### 3.3 Run Backend Server
```bash
mvn spring-boot:run
```

**Expected Output:**
```
Started NoticeHubApplication in X.XXX seconds (JVM running for X.XXX)
```

**Backend will run on:** http://localhost:8080

---

### Step 4: Frontend Setup

**Open a NEW terminal** (keep backend running)

#### 4.1 Navigate to Frontend Directory
```bash
cd NoticeHub/frontend
```

#### 4.2 Install Dependencies
```bash
npm install
```

This will install all required packages (React, Axios, etc.)

#### 4.3 Run Frontend Server
```bash
npm start
```

**Expected Output:**
```
Compiled successfully!

You can now view noticehub-frontend in the browser.

  Local:            http://localhost:3000
```

**Frontend will automatically open in your browser at:** http://localhost:3000

---

## 🧪 Testing the Application

### Create Test Users

1. **Open browser:** http://localhost:3000
2. **Click "Register here"**

#### Test User 1: Admin
- Username: `admin`
- Password: `admin123`
- Email: `admin@noticehub.com`
- Full Name: `Admin User`
- Role: `ADMIN`

#### Test User 2: Faculty
- Username: `faculty1`
- Password: `faculty123`
- Email: `faculty1@noticehub.com`
- Full Name: `John Faculty`
- Role: `FACULTY`

#### Test User 3: Student
- Username: `student1`
- Password: `student123`
- Email: `student1@noticehub.com`
- Full Name: `Jane Student`
- Role: `STUDENT`

### Test Scenarios

1. **Login as Admin:**
   - Should see "Create Notice" button
   - Can create notices for any role
   - Can delete any notice

2. **Login as Faculty:**
   - Should see "Create Notice" button
   - Can create notices
   - Cannot delete notices

3. **Login as Student:**
   - Should NOT see "Create Notice" button
   - Can only view notices targeted to students
   - Cannot delete notices

---

## 🐛 Troubleshooting

### Backend Issues

**Problem:** `Port 8080 already in use`
```bash
# Solution: override the port via environment variable
export SERVER_PORT=8081
```

**Problem:** `Access denied for user 'root'@'localhost'`
```bash
# Solution: Reset MySQL password or update application.properties
```

**Problem:** `Cannot connect to database`
```bash
# Solution: Make sure MySQL is running
# Windows: Check Services
# macOS: brew services list
# Linux: sudo systemctl status mysql
```

### Frontend Issues

**Problem:** `Port 3000 already in use`
```bash
# Solution: Use different port
PORT=3001 npm start
```

**Problem:** `CORS errors in console`
```bash
# Solution: Verify backend CorsConfig.java includes:
config.addAllowedOrigin("http://localhost:3000");
```

**Problem:** `Cannot connect to backend`
```bash
# Solution: Make sure backend is running on port 8080
# Test: Open http://localhost:8080 in browser
```

---

## 📱 Application URLs

- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080
- **API Documentation:** 
  - Auth Endpoints: http://localhost:8080/api/auth/*
  - Notice Endpoints: http://localhost:8080/api/notices/*

---

## 🔐 Default Configuration

### JWT Settings
- **Secret Key:** Set in application.properties
- **Token Expiration:** 24 hours (86400000 ms)

### Database Settings
- **Host:** localhost
- **Port:** 3306
- **Database:** noticehub
- **Hibernate:** Auto-update schema

---

## 📊 Project Structure
```
NoticeHub/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/noticehub/
│   │   │   │   ├── config/         # Security & CORS
│   │   │   │   ├── controller/     # REST APIs
│   │   │   │   ├── dto/            # Data Transfer Objects
│   │   │   │   ├── entity/         # Database Models
│   │   │   │   ├── repository/     # JPA Repositories
│   │   │   │   ├── service/        # Business Logic
│   │   │   │   └── util/           # JWT Utility
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
│
└── frontend/
    ├── public/
    ├── src/
    │   ├── components/    # React Components
    │   ├── services/      # API Services
    │   ├── App.js
    │   └── App.css
    └── package.json
```

---

## 🎯 Key Features Implementation

### Authentication Flow
1. User registers → Password encrypted with BCrypt
2. User logs in → JWT token generated
3. Token sent with each request → Validated by backend
4. Token expires after 24 hours

### Authorization Flow
1. JWT contains user role
2. Backend validates role for each endpoint
3. Frontend shows/hides features based on role

### Notice Management
1. Admin/Faculty create notices
2. Notices targeted to specific roles
3. Users only see relevant notices
4. Admin can delete any notice

---

## 📞 Support

If you encounter any issues:

1. Check the troubleshooting section above
2. Verify all prerequisites are installed
3. Check console logs for error messages
4. Ensure MySQL is running and accessible

---

## ✅ Success Checklist

- [ ] Java installed and verified
- [ ] Maven installed and verified
- [ ] Node.js and npm installed
- [ ] MySQL installed and running
- [ ] Database created
- [ ] Backend builds successfully
- [ ] Backend runs on port 8080
- [ ] Frontend builds successfully
- [ ] Frontend runs on port 3000
- [ ] Can register new user
- [ ] Can login successfully
- [ ] Can create notice (Admin/Faculty)
- [ ] Can view notices
- [ ] Can delete notice (Admin only)

---

**Congratulations! Your NoticeHub application is now running! 🎉**