# Conference Management API

A RESTful backend service built with Spring Boot for managing conference speakers and talks. It enforces strict request validation and domain business rules, provides standardized error handling, manages database migrations with Liquibase, and automates background reporting and status transitions.

## Tech Stack

* **Language & Framework:** Java 21, Spring Boot 4.1.1 (Spring Web MVC, Spring Data JPA, Spring Scheduling)
* **Database & Migration:** PostgreSQL, Liquibase
* **Validation & Mapping:** Jakarta Bean Validation, MapStruct, Lombok
* **API Documentation:** SpringDoc OpenAPI 3 (Swagger UI)
* **Build Tool:** Gradle

## Key Features

* **Layered Architecture:** Clean separation across Config, Controller, Service, Repository, Mapper, and Scheduler layers.
* **Two-Layer Validation:**
  * Request-level validation (`@Valid`, `@Validated`) enforcing field constraints and positive path variables before reaching the service layer.
  * Domain business rules enforcing unique speaker emails (`409 Conflict`), maximum 3 talks per speaker (`400 Bad Request`), and 15–90 minute talk durations.
* **Global Exception Handling:** Centralized `@RestControllerAdvice` returning consistent `ApiError` payloads (`400`, `404`, `409`, `500`).
* **Database Migrations:** Version-controlled schema changes and rollback definitions managed via Liquibase YAML changelogs.
* **Interactive API Docs:** Customized OpenAPI 3 specification (`OpenApiConfig`) with endpoint summaries, response codes, and `ApiError` schemas.
* **Automated Background Tasks:** Periodic statistics aggregation and cron-based status updates running on a dedicated scheduler thread pool (`pool.size=2`).

## Getting Started

### Prerequisites
* Java 21+
* PostgreSQL 15+

### Configuration
Configure your database, Liquibase, and scheduler properties in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=<your_password>

spring.liquibase.change-log=classpath:liquibase/changelog-master.yaml

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