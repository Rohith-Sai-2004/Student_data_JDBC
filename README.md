# JDBC Student Database Project

## Overview
This is a simple Java console application that demonstrates **JDBC connectivity** with MySQL.  
It allows you to perform basic **CRUD operations** (Create, Read, Update, Delete) on a student database.

## Features
- Insert student records (Roll Number, Name, Grade)
- Update student name or grade
- Delete student records
- View all student records in a formatted table

## Tech Stack
- Java 17+
- MySQL 8+
- JDBC API

## Setup Instructions

### Database Setup
1. Install MySQL (if not already installed)
2. Create a database:
```sql
CREATE DATABASE COLLEGE;
```
3. Create table
```sql
CREATE TABLE STUDENTS_DATA (
    ROLL_NO INT PRIMARY KEY,
    NAME VARCHAR(50),
    GRADE VARCHAR(5)
);
```



