# 📢 NoticeHub — Digital Notice Board System

A full-stack notice board application for educational institutions, built with **Spring Boot** (Java) on the backend and **React** on the frontend. It supports role-based access control so Admins, Faculty, and Students each see and can do only what their role permits.

> Built as an academic project (MCA, Internet Programming) to practice a complete full-stack workflow: REST APIs, JWT authentication, relational data modeling, and a React SPA consuming a secured backend.

---

## ✨ Features

- **Three roles, three experiences**
  - **Admin** — create notices, view all notices, delete any notice
  - **Faculty** — create notices, view all notices
  - **Student** — read-only view of notices targeted at students
- **JWT-based authentication** — stateless login, token sent on every request
- **Password hashing** with BCrypt (no plaintext passwords, ever)
- **Role-aware REST API** — every endpoint checks the caller's role server-side, not just in the UI
- **Responsive React UI** with a clean, componentized structure

---

## 🛠️ Tech Stack

| Layer     | Technology |
|-----------|------------|
| Backend   | Java 17+ (built/tested on Java 25), Spring Boot 3.4.1, Spring Security, Spring Data JPA / Hibernate |
| Database  | MySQL 8.0 |
| Frontend  | React 18, Axios |
| Auth      | JWT (`io.jsonwebtoken` / jjwt) + BCrypt password hashing |
| Build     | Maven (backend), npm (frontend) |

---

## 🏗️ Architecture

```
Browser (React SPA, :3000)
        │  Axios + JWT in Authorization header
        ▼
Spring Boot REST API (:8080)
   ├── AuthController      /api/auth/register, /api/auth/login
   ├── NoticeController    /api/notices  (GET, POST, DELETE /{id})
   ├── Spring Security     validates JWT on every request
   └── Spring Data JPA     ──►  MySQL (users, notices)
```

---

## 📁 Project Structure

```
NoticeHub/
├── backend/noticehub/
│   ├── src/main/java/com/noticehub/
│   │   ├── config/        # Security, CORS, JWT filter
│   │   ├── controller/    # REST endpoints (Auth, Notice)
│   │   ├── dto/           # Request/response payloads
│   │   ├── entity/        # JPA entities (User, Notice, Role)
│   │   ├── repository/    # Spring Data repositories
│   │   ├── service/       # Business logic
│   │   └── util/          # JwtUtil (token generation/validation)
│   ├── src/main/resources/application.properties
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/    # Login, Dashboard, NoticeBoard, CreateNotice, Navbar
│   │   ├── services/api.js
│   │   └── App.jsx
│   └── package.json
│
├── DATABASE_SETUP.sql     # Optional manual schema (Hibernate can auto-create it)
├── SETUP_GUIDE.md         # Detailed local setup walkthrough
└── QUICK_START.md         # Condensed version of the same steps
```

---

## 🔐 Security & Configuration (read this before running)

This project reads all sensitive configuration — database credentials and the JWT signing secret — from **environment variables**, with local-only placeholder defaults so nothing real ever lives in source control.

| Variable         | Used for                          | Where |
|------------------|------------------------------------|-------|
| `DB_URL`         | MySQL connection string            | backend |
| `DB_USERNAME`    | MySQL username                     | backend |
| `DB_PASSWORD`    | MySQL password                     | backend |
| `JWT_SECRET`     | Signing key for JWTs               | backend |
| `JWT_EXPIRATION` | Token lifetime in ms (default 24h) | backend |
| `REACT_APP_API_URL` | Backend base URL for the frontend | frontend |

Templates are provided at `backend/noticehub/.env.example` and `frontend/.env.example` — copy them, fill in your own values, and never commit the copies (both `.env` files are already excluded via `.gitignore`).

**Why this matters:** the original version of this project had a real-looking JWT secret and DB password committed directly in `application.properties`. That's a common but avoidable mistake — anyone with the GitHub link could see them. This version keeps the repo safe to make public.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+ (JDK)
- Maven 3.9+
- Node.js 16+ and npm
- MySQL 8.0+

### 1. Clone and configure
```bash
git clone https://github.com/<your-username>/noticehub.git
cd noticehub

# Backend: copy the example env file and fill in your own DB password / JWT secret
cp backend/noticehub/.env.example backend/noticehub/.env

# Frontend: copy the example env file (default works for local dev as-is)
cp frontend/.env.example frontend/.env
```

Export the backend variables in your shell (or configure them in your IDE's run config) before starting Spring Boot, for example:
```bash
export DB_URL="jdbc:mysql://localhost:3306/noticehub_db"
export DB_USERNAME="noticehub_user"
export DB_PASSWORD="your_own_password"
export JWT_SECRET="$(openssl rand -base64 48)"
```

### 2. Set up MySQL
```sql
CREATE DATABASE noticehub_db;
CREATE USER 'noticehub_user'@'localhost' IDENTIFIED BY 'your_own_password';
GRANT ALL PRIVILEGES ON noticehub_db.* TO 'noticehub_user'@'localhost';
FLUSH PRIVILEGES;
```
(Hibernate will auto-create the tables on first run. `DATABASE_SETUP.sql` is available if you'd rather create them manually.)

### 3. Run the backend
```bash
cd backend/noticehub
./mvnw spring-boot:run
```
Runs on **http://localhost:8080**.

### 4. Run the frontend
```bash
cd frontend
npm install
npm start
```
Runs on **http://localhost:3000** and talks to the backend automatically.

For more detail (including troubleshooting), see [`SETUP_GUIDE.md`](./SETUP_GUIDE.md).

---

## 📡 API Overview

| Method | Endpoint             | Access          | Description |
|--------|-----------------------|-----------------|--------------|
| POST   | `/api/auth/register`  | Public          | Create a new user |
| POST   | `/api/auth/login`     | Public          | Log in, returns a JWT |
| GET    | `/api/notices`        | Authenticated   | List notices (Admin sees all; others see notices targeted at their role) |
| POST   | `/api/notices`        | Admin, Faculty  | Create a notice |
| DELETE | `/api/notices/{id}`   | Admin only      | Delete a notice |

All routes under `/api/notices` require a valid `Authorization: Bearer <token>` header.

---

## 🧪 Testing

```bash
cd backend/noticehub
./mvnw test
```
Tests run against an in-memory H2 database (`application-test.properties`), so they never touch your real MySQL instance.

---

## 🗺️ Possible Next Steps

- Add pagination and search/filtering to the notice board
- Add unit tests for controllers/services (currently minimal test coverage)
- Containerize with Docker Compose (app + MySQL) for one-command startup
- Add refresh tokens / token revocation on logout

---

## 📝 License

This project was built for educational purposes as part of an MCA coursework assignment.

## 🙋 Author

**Your Name** — feel free to reach out via [LinkedIn](#) or [email](#).
