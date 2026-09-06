# ⚡ NoticeHub - Quick Start (5 Minutes)

## Prerequisites Installed?
- [ ] Java 17+
- [ ] Maven
- [ ] Node.js
- [ ] MySQL

## Super Quick Setup

### 1. Database (30 seconds)
```sql
mysql -u root -p
CREATE DATABASE noticehub;
EXIT;
```

### 2. Backend (2 minutes)
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
Wait for "Started NoticeHubApplication"

### 3. Frontend (2 minutes)
**New terminal:**
```bash
cd frontend
npm install
npm start
```

### 4. Test (30 seconds)
- Open http://localhost:3000
- Register as Admin/Faculty/Student
- Login and explore!

---

## That's it! 🎉

For detailed setup, see SETUP_GUIDE.md
```

---

## 📧 Email Template to Send to Friends
```
Subject: NoticeHub Project - Digital Notice Board System 🚀

Hey [Friend's Name],

I'm sharing my NoticeHub project with you! It's a web-based notice board system with role-based access control.

📦 What's Included:
- Complete source code (Backend + Frontend)
- Detailed setup guide
- Database scripts
- Documentation

🛠️ Tech Stack:
- Backend: Spring Boot + MySQL
- Frontend: React.js
- Security: JWT Authentication

📋 What You Need:
1. Java 17+ (I used Java 25)
2. Maven 3.9+
3. Node.js 16+
4. MySQL 8+

📖 Setup Instructions:
Everything is in SETUP_GUIDE.md - should take about 10 minutes to set up!

Quick Start:
1. Extract the ZIP
2. Create MySQL database
3. Run backend: `mvn spring-boot:run`
4. Run frontend: `npm start`

🎯 Test Users:
- Admin: admin/admin123
- Faculty: faculty1/faculty123
- Student: student1/student123

📁 Files Attached:
- NoticeHub.zip (Project source code)
- README.md (Project overview)
- SETUP_GUIDE.md (Detailed instructions)
- DATABASE_SETUP.sql (Database script)

Let me know if you face any issues!

Happy coding! 😊

[Your Name]