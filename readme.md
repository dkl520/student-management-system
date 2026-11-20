# 学生管理系统 / Student Management System

[中文](#中文说明) | [English](#english-documentation)

---

## 中文说明

### 项目简介
学生管理系统（Student Management System）是一个基于 **Spring Boot**、**Spring Data JPA** 和 **MySQL** 构建的现代化后端 RESTful API 服务。系统采用分层架构设计，集成了 **DTO（Data Transfer Object）** 模式，实现表现层与持久化实体的解耦，提供安全、高效、易扩展的学生信息管理能力。

### 核心特性
- **RESTful API 规范**：提供标准的 CRUD 接口，支持学生的创建、查询、更新与删除。
- **DTO 架构设计**：通过 `StudentDto` 与 `StudentMapper` 实现 API 交互契约与数据库实体解耦，避免批量赋值漏洞（Over-Posting）并提高扩展性。
- **Spring Data JPA 持久化**：依托 JpaRepository 实现高效的数据访问与自动表结构更新（Hibernate `ddl-auto=update`）。
- **完善的单元测试**：针对 Controller、Service、Mapper 均编写了自动化单元测试（基于 JUnit 5 与 Mockito）。

### 技术栈
- **核心框架**：Spring Boot
- **持久层**：Spring Data JPA (Hibernate)
- **数据库**：MySQL
- **代码简化**：Lombok
- **构建工具**：Maven (Java 17)
- **测试框架**：JUnit 5, Mockito, Spring Boot Test

### 项目结构
```text
src/main/java/org/project/student_management_system/
├── controller/       # 控制器层 (RESTful API 接口定义)
│   └── StudentController.java
├── service/          # 业务逻辑层
│   └── StudentService.java
├── repository/       # 数据访问层 (Spring Data JPA Repository)
│   └── StudentRepository.java
├── entity/           # 数据库持久化实体
│   └── Student.java
├── dto/              # 数据传输对象 (请求入参与响应出参)
│   └── StudentDto.java
├── mapper/           # Entity 与 DTO 相互转换映射器
│   └── StudentMapper.java
└── StudentManagementSystemApplication.java # 应用启动入口
```

### API 接口列表

基础路径：`/api/v1/students`

| 请求方法 | 路径 | 描述 | 请求体 | 响应状态码 |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/students/create` | 创建新学生信息 | `StudentDto` (JSON) | `201 Created` |
| `GET` | `/api/v1/students` | 获取所有学生列表 | 无 | `200 OK` |
| `GET` | `/api/v1/students/{id}` | 根据 ID 获取单个学生详情 | 无 | `200 OK` |
| `PUT` | `/api/v1/students/{id}` | 根据 ID 更新学生信息 | `StudentDto` (JSON) | `200 OK` |
| `DELETE` | `/api/v1/students/{id}` | 根据 ID 删除学生 | 无 | `204 No Content` |

查询、更新或删除不存在的学生时返回 `404 Not Found`，响应示例：
```json
{"status": 404, "message": "Student not found with ID: 42"}
```
学生列表为空时仍返回 `200 OK` 和空数组；无效 ID 或格式错误的 JSON 由框架返回 `400 Bad Request`。

#### 数据传输对象示例 (StudentDto JSON)
```json
{
  "id": 1,
  "firstName": "三",
  "lastName": "张",
  "email": "zhangsan@example.com",
  "major": "计算机科学与技术"
}
```

### 快速开始

#### 1. 环境准备
- Java 17+
- MySQL 8.0+
- Maven 3.8+ (或使用项目内置的 `./mvnw`)

#### 2. 数据库配置
在 MySQL 中创建数据库：
```sql
CREATE DATABASE student_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

检查 `src/main/resources/application.properties` 中的数据库配置并按需修改：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### 3. 构建与运行
```bash
# 编译并打包
./mvnw clean package

# 运行测试
./mvnw test

# 启动应用程序
./mvnw spring-boot:run
```

---

## English Documentation

### Introduction
**Student Management System** is a modern RESTful API backend service built with **Spring Boot**, **Spring Data JPA**, and **MySQL**. It adopts a layered architecture decoupled with the **DTO (Data Transfer Object)** pattern to ensure high security, maintainability, and scalability for managing student records.

### Key Features
- **RESTful API Standards**: Standard CRUD endpoints for creating, retrieving, updating, and deleting student records.
- **DTO Pattern Architecture**: Decouples external API contracts from internal database entities via `StudentDto` and `StudentMapper`, preventing over-posting vulnerabilities and simplifying schema evolution.
- **Spring Data JPA**: Efficient persistence management with automatic table creation/updates using Hibernate (`ddl-auto=update`).
- **Comprehensive Unit Tests**: High test coverage across Controller, Service, and Mapper layers powered by JUnit 5 and Mockito.

### Tech Stack
- **Framework**: Spring Boot
- **Persistence**: Spring Data JPA (Hibernate)
- **Database**: MySQL
- **Boilerplate Reduction**: Project Lombok
- **Build Tool**: Maven (Java 17)
- **Testing**: JUnit 5, Mockito, Spring Boot Test

### Project Architecture
```text
src/main/java/org/project/student_management_system/
├── controller/       # Controller Layer (REST API Endpoints)
│   └── StudentController.java
├── service/          # Business Logic Layer
│   └── StudentService.java
├── repository/       # Data Access Layer (Spring Data JPA)
│   └── StudentRepository.java
├── entity/           # JPA Persistence Entities
│   └── Student.java
├── dto/              # Data Transfer Objects (Request/Response models)
│   └── StudentDto.java
├── mapper/           # Object mapping between Entity and DTO
│   └── StudentMapper.java
└── StudentManagementSystemApplication.java # Application Entry Point
```

### API Endpoints

Base URL: `/api/v1/students`

| Method | Endpoint | Description | Request Body | Response Status |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/students/create` | Create a new student | `StudentDto` (JSON) | `201 Created` |
| `GET` | `/api/v1/students` | Get all students | None | `200 OK` |
| `GET` | `/api/v1/students/{id}` | Get student by ID | None | `200 OK` |
| `PUT` | `/api/v1/students/{id}` | Update student by ID | `StudentDto` (JSON) | `200 OK` |
| `DELETE` | `/api/v1/students/{id}` | Delete student by ID | None | `204 No Content` |

Reading, updating, or deleting a missing student returns `404 Not Found`, for example:
```json
{"status": 404, "message": "Student not found with ID: 42"}
```
An empty student list still returns `200 OK` with an empty array. Invalid IDs and malformed JSON return `400 Bad Request` from the framework.

#### Data Transfer Object Example (StudentDto JSON)
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "major": "Computer Science"
}
```

### Quick Start

#### 1. Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.8+ (or use the included `./mvnw` wrapper)

#### 2. Database Setup
Create database in MySQL:
```sql
CREATE DATABASE student_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Verify or update the configuration in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### 3. Build & Run
```bash
# Build the project
./mvnw clean package

# Run unit tests
./mvnw test

# Start the application
./mvnw spring-boot:run
```
