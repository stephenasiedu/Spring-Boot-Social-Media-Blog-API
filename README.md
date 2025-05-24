# Social Media Blog API

## Overview

This project implements a RESTful backend API for a social media application using the Spring Boot framework. The API manages user accounts and messages, providing endpoints for user registration, authentication, message creation, retrieval, update, and deletion. The application leverages Spring Boot, Spring Data JPA, and follows best practices for dependency injection and RESTful design.

> **This project was developed in collaboration with Revature.**

## Features

- User registration and authentication
- Message creation, retrieval, update, and deletion
- Retrieve all messages or messages by a specific user
- Input validation and error handling
- Persistent storage using a relational database (configured via `application.properties`)

## Technologies Used

- Java 11+
- Spring Boot
- Spring Data JPA
- H2 (in-memory database, configurable)
- Maven

## Database Schema

The following tables are initialized on application startup:

### Account

```sql
accountId integer primary key auto_increment,
username varchar(255) not null unique,
password varchar(255)
```

### Message

```sql
messageId integer primary key auto_increment,
postedBy integer,
messageText varchar(255),
timePostedEpoch long,
foreign key (postedBy) references Account(accountId)
```

## API Endpoints

### User Registration

- **POST** `/register`
- Registers a new user account.
- Returns `200 OK` with the created account, `409 Conflict` for duplicate usernames, or `400 Bad Request` for invalid input.

### User Login

- **POST** `/login`
- Authenticates a user.
- Returns `200 OK` with account details or `401 Unauthorized` if authentication fails.

### Create Message

- **POST** `/messages`
- Creates a new message.
- Returns `200 OK` with the created message or `400 Bad Request` for invalid input.

### Retrieve All Messages

- **GET** `/messages`
- Returns a list of all messages (`200 OK`).

### Retrieve Message by ID

- **GET** `/messages/{messageId}`
- Returns the message with the specified ID (`200 OK`), or empty if not found.

### Delete Message

- **DELETE** `/messages/{messageId}`
- Deletes the specified message.
- Returns `200 OK` with `1` if deleted, or empty if not found.

### Update Message

- **PATCH** `/messages/{messageId}`
- Updates the message text.
- Returns `200 OK` with `1` if updated, or `400 Bad Request` if invalid.

### Retrieve Messages by User

- **GET** `/accounts/{accountId}/messages`
- Returns all messages posted by the specified user (`200 OK`).

## Getting Started

1. **Clone the repository**
2. **Configure the database** in `src/main/resources/application.properties` if needed.
3. **Build and run the application:**
   ```sh
   mvn spring-boot:run
   ```
4. **Access the API** at `http://localhost:8080/`

## Project Structure

- `controller/` – REST controllers for API endpoints
- `entity/` – JPA entity classes
- `repository/` – Spring Data JPA repositories
- `service/` – Business logic and service classes
- `exception/` – Custom exception classes

## Notes

- All endpoints return appropriate HTTP status codes and JSON responses.
- The application uses standard Spring Boot conventions for configuration and dependency management.

---