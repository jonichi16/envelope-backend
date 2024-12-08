# Envelope Backend

A simple budget tracking application using the **envelope system** to manage expenses. Built with **Spring Boot**, **Lombok**, **PostgreSQL**, and **Docker**.

---

## Features

- User Authentication (optional to add later)
- Envelope-based budget allocation for categories (e.g., Food, Rent, etc.)
- Expense tracking with automatic deduction from corresponding envelopes
- Monthly summary and budget analysis (to be added)
- REST API for interaction with the backend

---

## Tech Stack

- **Java** (JDK 21)
- **Spring Boot** for backend development
- **PostgreSQL** as the database
- **Docker** for containerization
- **Lombok** for boilerplate code reduction

---

## Prerequisites

- Docker and Docker Compose installed
- Java 21 installed
- Maven installed

---

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/jonichi16/envelope-backend.git
cd envelope-backend
```

### 2. Folder Structure
```text
envelope-backend/
|-- src/main/java/com/example/envelope/
|   |-- application/         # Application services (Use cases)
|   |-- domain/              # Domain models and business logic
|   |-- adapters/            # Input (controllers) and Output (repositories)
|   |-- config/              # Spring configurations
|-- src/main/resources/      # Application properties and configurations
|-- src/test/java/           # Unit tests
|-- Dockerfile               # Docker configuration for the app
|-- docker-compose.yml       # Docker Compose file for app and database
|-- pom.xml                  # Maven configuration
```

---