-- NoticeHub Database Setup Script

-- Create Database
CREATE DATABASE IF NOT EXISTS noticehub;

-- Use Database
USE noticehub;

-- Create User (Optional - for production)
-- CREATE USER 'noticehub_user'@'localhost' IDENTIFIED BY 'noticehub_password';
-- GRANT ALL PRIVILEGES ON noticehub.* TO 'noticehub_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Note: Tables will be auto-created by Hibernate on first run
-- But you can manually create them if needed:

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Notices Table
CREATE TABLE IF NOT EXISTS notices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    target_audience VARCHAR(50) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_created_at (created_at DESC),
    INDEX idx_target_audience (target_audience)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample Data (Optional - for testing)
-- INSERT INTO users (username, password, email, full_name, role) VALUES
-- ('admin', '$2a$10$...encrypted...', 'admin@noticehub.com', 'Admin User', 'ADMIN');