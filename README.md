# Store API

REST API for managing users and products.

## Technologies

- Java 17
- Spring Boot
- PostgreSQL
- Spring Security
- JUnit
- Mockito

## Features

- Product and user management
- Paginated product and user listings
- Request validation and global exception handling
- Stateless HTTP Basic authentication
- Role-based access control for CUSTOMER and ADMIN users
- BCrypt password hashing
- Unit tests for UserService

## Setup

The application requires a PostgreSQL database.

Configure the following environment variables:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

An initial admin account can optionally be created by setting:

- `ADMIN_EMAIL`
- `ADMIN_PASSWORD`

If the admin variables are not provided, the application starts without creating an admin account.

Run the application:

Windows: `.\mvnw.cmd spring-boot:run`

Linux/macOS: `./mvnw spring-boot:run`

Run the tests:

Windows: `.\mvnw.cmd clean verify`

Linux/macOS: `./mvnw clean verify`