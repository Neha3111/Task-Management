CREATE DATABASE IF NOT EXISTS ems_auth CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS ems_employee CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- create sample user in auth DB
USE ems_auth;
CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255) UNIQUE, password VARCHAR(255));
INSERT INTO users(username,password) VALUES ('admin','admin') ON DUPLICATE KEY UPDATE username=username;

CREATE DATABASE IF NOT EXISTS ems_task CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ems_task;
CREATE TABLE IF NOT EXISTS tasks (id BIGINT PRIMARY KEY AUTO_INCREMENT, title VARCHAR(255), description TEXT, status VARCHAR(50), employee_id BIGINT);
