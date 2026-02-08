-- TinyGuard Database Setup Script
-- Run this if you want to manually create the database

CREATE DATABASE IF NOT EXISTS tinyguard_db;
USE tinyguard_db;

-- Tables will be auto-created by Hibernate
-- This script is for reference only

-- Users table structure
-- CREATE TABLE users (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     full_name VARCHAR(100) NOT NULL,
--     email VARCHAR(255) UNIQUE NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     role VARCHAR(20) NOT NULL,
--     active BOOLEAN DEFAULT TRUE,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     last_active TIMESTAMP
-- );

-- Children table structure
-- CREATE TABLE children (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     full_name VARCHAR(100) NOT NULL,
--     date_of_birth DATE NOT NULL,
--     gender VARCHAR(10) NOT NULL,
--     blood_type VARCHAR(10),
--     allergies VARCHAR(500),
--     primary_contact_name VARCHAR(100),
--     primary_contact_relation VARCHAR(50),
--     primary_contact_phone VARCHAR(20),
--     primary_contact_email VARCHAR(100),
--     secondary_contact_name VARCHAR(100),
--     secondary_contact_relation VARCHAR(50),
--     secondary_contact_phone VARCHAR(20),
--     pediatrician_name VARCHAR(100),
--     pediatrician_clinic VARCHAR(100),
--     pediatrician_phone VARCHAR(20),
--     pediatrician_address VARCHAR(200),
--     parent_id BIGINT NOT NULL,
--     FOREIGN KEY (parent_id) REFERENCES users(id)
-- );

-- Vaccines table structure
-- CREATE TABLE vaccines (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     name VARCHAR(100) NOT NULL,
--     age_milestone VARCHAR(50) NOT NULL,
--     dose_number VARCHAR(50) NOT NULL,
--     date_range VARCHAR(100) NOT NULL,
--     description VARCHAR(500),
--     age_in_days INT NOT NULL
-- );

-- Vaccination Records table structure
-- CREATE TABLE vaccination_records (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     child_id BIGINT NOT NULL,
--     vaccine_id BIGINT NOT NULL,
--     due_date DATE NOT NULL,
--     given_date DATE,
--     status VARCHAR(20) NOT NULL,
--     notes VARCHAR(500),
--     FOREIGN KEY (child_id) REFERENCES children(id),
--     FOREIGN KEY (vaccine_id) REFERENCES vaccines(id)
-- );

-- Clinics table structure
-- CREATE TABLE clinics (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     name VARCHAR(200) NOT NULL,
--     location VARCHAR(200) NOT NULL,
--     contact_person VARCHAR(100) NOT NULL,
--     phone VARCHAR(20),
--     email VARCHAR(100),
--     status VARCHAR(20) NOT NULL
-- );

-- Appointments table structure
-- CREATE TABLE appointments (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     child_id BIGINT NOT NULL,
--     clinic_id BIGINT,
--     appointment_date DATE NOT NULL,
--     appointment_time TIME NOT NULL,
--     doctor_name VARCHAR(100) NOT NULL,
--     location VARCHAR(200) NOT NULL,
--     notes VARCHAR(500),
--     summary VARCHAR(1000),
--     status VARCHAR(20) NOT NULL,
--     FOREIGN KEY (child_id) REFERENCES children(id),
--     FOREIGN KEY (clinic_id) REFERENCES clinics(id)
-- );

-- Growth Records table structure
-- CREATE TABLE growth_records (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     child_id BIGINT NOT NULL,
--     record_date DATE NOT NULL,
--     weight DOUBLE NOT NULL,
--     height DOUBLE,
--     age_in_months INT NOT NULL,
--     FOREIGN KEY (child_id) REFERENCES children(id)
-- );

-- System Settings table structure
-- CREATE TABLE system_settings (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     platform_name VARCHAR(100) NOT NULL,
--     support_email VARCHAR(100) NOT NULL,
--     time_zone VARCHAR(50) NOT NULL,
--     maintenance_mode BOOLEAN DEFAULT FALSE,
--     email_notifications BOOLEAN DEFAULT TRUE,
--     sms_notifications BOOLEAN DEFAULT TRUE,
--     two_factor_auth BOOLEAN DEFAULT TRUE
-- );
