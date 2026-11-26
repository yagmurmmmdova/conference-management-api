# Conference Management API

A RESTful backend service built with Spring Boot for managing conference speakers and talks. It enforces domain business rules, provides standardized error handling, and automates background reporting and talk status transitions.

## Tech Stack

* **Language & Framework:** Java 21, Spring Boot 4.1.1 (Spring Web MVC, Spring Data JPA, Spring Scheduling)
* **Database:** PostgreSQL
* **Validation & Mapping:** Jakarta Bean Validation, MapStruct, Lombok
* **API Documentation:** SpringDoc OpenAPI (Swagger UI)
* **Build Tool:** Gradle

## Key Features

* **Layered Architecture:** Clean separation across Controller, Service, Repository, Mapper, and Scheduler layers.
* **Domain Business Rules:**
  * Enforces unique email addresses per speaker (`409 Conflict`).
  * Limits each speaker to a maximum of 3 talks (`400 Bad Request`).
  * Validates talk duration between 15 and 90 minutes.
* **Global Exception Handling:** Centralized `@RestControllerAdvice` returning consistent `ApiError` payloads (`400`, `404`, `409`, `500`).
* **Automated Background Tasks:** Periodic statistics aggregation and cron-based status updates running on a dedicated scheduler thread pool (`pool.size=2`).

## Getting Started

### Prerequisites
* Java 21+
* PostgreSQL 15+

### Configuration
Configure your database and scheduler properties in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=<your_password>

conference.report.rate-ms=30000
spring.task.scheduling.pool.size=2
```

### Build & Run
```bash
./gradlew bootRun
```

Once started, access the interactive Swagger UI at:
`http://localhost:8080/swagger-ui.html`

## API Reference

### Speakers
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/speakers` | Register a new speaker |
| `GET` | `/speakers` | Retrieve all speakers |
| `GET` | `/speakers/{id}` | Retrieve a speaker by ID |
| `GET` | `/speakers/{id}/talks` | Retrieve all talks for a specific speaker |

### Talks
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/talks` | Schedule a new talk (default status: `SCHEDULED`) |
| `GET` | `/talks` | Retrieve all talks |
| `GET` | `/talks/{id}` | Retrieve a talk by ID |
| `PUT` | `/talks/{id}` | Update an existing talk |
| `DELETE` | `/talks/{id}` | Delete a talk |

## Scheduled Jobs

| Job | Schedule | Description |
| :--- | :--- | :--- |
| `TalkStatisticsJob` | Every 30s (10s initial delay) | Logs total speaker/talk counts and talk distribution grouped by room. |
| `TalkStatusJob` | Every minute (`0 * * * * *`, `Asia/Baku`) | Transitions `SCHEDULED` talks to `FINISHED` once `scheduledAt + durationMinutes` has elapsed. |