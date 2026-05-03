# Keyloop Assessment

A Spring Boot 4 REST API for managing sales leads and their follow-up activities.

## Overview

This application provides APIs to:

- create leads
- list the lead inbox with filtering and pagination
- view lead details
- log follow-up activities
- update lead status
- update activity type

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL for runtime persistence
- H2 for tests
- Maven Wrapper for builds

## Prerequisites

Before running the application, make sure you have:

- Docker and Docker Compose installed

If you want to run the application without Docker, you can still use Java 21 and a local PostgreSQL instance. The default local database settings are:

- URL: `jdbc:postgresql://localhost:5439/postgres?sslmode=disable`
- Username: `admin`
- Password: `123`

## Project Structure

- `src/main/java/keyloop/assessment/controller` - REST controllers
- `src/main/java/keyloop/assessment/service` - business logic
- `src/main/java/keyloop/assessment/repo` - JPA repositories
- `src/main/java/keyloop/assessment/model` - entities and enums
- `src/main/java/keyloop/assessment/dto` - request and response DTOs
- `src/main/java/keyloop/assessment/exception` - API error handling
- `src/test/java` - unit tests
- `openapi.yaml` - API contract
- `seed.sql` - sample seed data

## Build

Use the Maven Wrapper if you want to build and test locally without Docker.

### Windows

```powershell
mvnw.cmd clean package
```

### macOS / Linux

```bash
./mvnw clean package
```

This compiles the code, runs tests, and creates the application JAR in `target/`.

## Run

### Run with Docker Compose

This project includes a `Dockerfile` and `docker-compose.yml` so you can run the API and PostgreSQL together.

Start everything:

```powershell
docker-compose up --build
```

The services will be available at:

- API: `http://localhost:8080`
- PostgreSQL: `localhost:5439`

To run in the background:

```powershell
docker-compose up -d --build
```

To stop and remove containers:

```powershell
docker-compose down
```

To stop and remove containers plus the database volume:

```powershell
docker-compose down -v
```

To view logs:

```powershell
docker-compose logs -f app
docker-compose logs -f postgres
```

### Run from source

### Windows

```powershell
mvnw.cmd spring-boot:run
```

### macOS / Linux

```bash
./mvnw spring-boot:run
```

The application starts on port `8080`.

### Run the built JAR

After packaging, run the JAR from the `target/` directory:

```bash
java -jar target/assessment-0.0.1-SNAPSHOT.jar
```

When running locally from source, you still need PostgreSQL running on `localhost:5439` with the database credentials configured in `src/main/resources/application.properties`.

## Test

Run all tests with:

### Windows

```powershell
mvnw.cmd test
```

### macOS / Linux

```bash
./mvnw test
```

Tests use H2 in-memory support and Mockito-based unit tests, so they do not require the PostgreSQL database to be running.

## Instruction

### AI Collaboration Narrative

#### My Strategy for Directing AI

I use Claude as a senior engineer for brainstorming and architecture decisions, not as a code generator. My approach:

**Prompt with Context, Not Just Task:**
Instead of asking "generate LeadService.java", I provide full context—business requirements, entity design, chosen conventions—then ask about specific implementation decisions. For example: "Given this Lead entity and these requirements, what should LeadService.getLeads() handle?"

**Ask About Trade-Offs, Not Just Solutions:**
For each major decision (SQL vs. NoSQL, 2 entities vs. 3 entities, pagination strategy), I ask Claude to analyze pros and cons rather than asking "which one should I use?". The final decision is always mine after evaluating the trade-offs.

**Break Down into Small Tasks:**
Instead of prompting "build the entire application", I divide work into phases: entity design → repository queries → service logic → controller → error handling → tests. Each phase is reviewed before moving to the next.

#### My Process for Verifying AI Output

**Entity and Database:**
- Review each field to ensure type and constraints are correct
- Verify indexes are created on the correct columns

**Service Layer:**
- Manually trace the logic of each method
- Identify and fix issues: for example, AI initially missed the `@Transactional` annotation on `logActivity()`—I added it after recognizing that activity persistence requires a clear transaction boundary
- Verify exceptions are thrown in the right place and of the correct type

**Tests:**
- Review test cases to confirm they cover the intended business logic

#### How I Ensured Final Quality

- Run the full test suite before submission: `mvn test`
- Manually test all happy paths and error cases using Postman
- Re-read the README from the perspective of someone unfamiliar with the project to ensure instructions are clear and complete

### Test Suite

The project includes a suite of tests that validate the core business logic, primarily under `src/test/java`.

- unit tests for the service layer
- Mockito-based tests for business rules and repository interactions
- H2-backed tests where persistence behavior needs to be exercised without PostgreSQL

These tests are intended to protect the lead management flows, including creating leads, updating lead status, and handling lead activities.

## Sample Data

The file `seed.sql` contains sample leads and lead activities.

When you run the stack with Docker Compose, the seed file is mounted into the PostgreSQL container and executed automatically on first startup.

If you run PostgreSQL manually, you can load the seed data yourself after creating the schema tables:

```bash
psql -h localhost -p 5439 -U admin -d postgres -f seed.sql
```

## API Endpoints

Base path: `/api/v1/leads`

- `GET /api/v1/leads` - list leads with optional `dealershipId`, `leadStatus`, `page`, and `size`
- `GET /api/v1/leads/{leadId}` - get lead details
- `POST /api/v1/leads` - create a lead
- `POST /api/v1/leads/{leadId}/activities` - log an activity
- `PATCH /api/v1/leads/{leadId}/status` - update lead status
- `PATCH /api/v1/leads/{leadId}/activities/{activityId}/type` - update activity type

## Example Requests

### Create a lead

```bash
curl -X POST http://localhost:8080/api/v1/leads ^
  -H "Content-Type: application/json" ^
  -d "{
    \"fullName\": \"John Doe\",
    \"email\": \"john.doe@example.com\",
    \"phoneNumber\": \"0901234567\",
    \"vehicleOfInterest\": \"Toyota Camry\",
    \"source\": \"Website\",
    \"notes\": \"Interested in a test drive\",
    \"dealershipId\": \"DEALER-001\"
  }"
```

### Update lead status

```bash
curl -X PATCH http://localhost:8080/api/v1/leads/1/status ^
  -H "Content-Type: application/json" ^
  -d "{ \"status\": \"QUALIFIED\" }"
```

## Error Handling

The API returns standardized JSON responses for success and error cases through a global exception handler.

- validation errors return `400 Bad Request`
- missing resources return `404 Not Found`
- unexpected failures return `500 Internal Server Error`

## Notes

- The application uses `spring.jpa.hibernate.ddl-auto=update`, so tables are updated automatically at startup.
- For API details and request/response schemas, refer to `openapi.yaml`.
- The Docker Compose setup uses the same application port `8080` and PostgreSQL port `5439` as the local configuration.
