# Future improvements

# Architecture & Design

- [ ] Add DTOs and mappers (MapStruct) instead of exposing JPA entities directly in responses.

# Error Handling

- [ ] Return standardized error responses with errorCode, message, timestamp, traceId (instead of raw ResponseEntity). Implement a problem-details RFC 7807 compliant error model.

# Security Enhancements

- [ ] Support role-based access control (RBAC): e.g., ROLE_ADMIN, ROLE_USER, ROLE_AUDITOR.

- [ ] Add refresh tokens alongside JWTs.

- [ ] Store password history and enforce password policies.

# Testing

- [ ] Add unit tests (Mockito/JUnit) and integration tests (SpringBootTest + Testcontainers for DB).

- [ ] Contract testing with Spring Cloud Contract.

# Observability

- [ ] Add logging with correlation IDs (MDC in SLF4J/Logback).

- [ ] Expose metrics (Micrometer + Prometheus) and health checks.

- [ ] Implement audit logging for transactions.

# Database & Data Handling

- [ ] Use Flyway/Liquibase for DB migrations.

- [ ] Add optimistic locking (via @Version) on entities like Account to prevent race conditions on balance updates.

- [ ] Handle concurrent transactions properly (maybe with pessimistic locking for withdrawals?)

# Event-Driven Architecture (Kafka)

- [ ] Publish an event (TransactionCreatedEvent) whenever a transaction happens.

- [ ] Have a consumer service that processes analytics, fraud detection, or audit logging.

# Async Processing

- [ ] Long-running operations (e.g., statement generation) handled via Spring events or Kafka.

- [ ] Use Spring @Async or virtual threads for concurrency.

# Advanced Banking Features

- [ ] Scheduled payments / recurring transactions.

- [ ] Overdraft protection with configurable limits.

- [ ] Account types (checking, savings, loan).

- [ ] Interest calculation jobs (scheduled task).

- [ ] Transfer between accounts (intra-user and inter-user).

# User Experience

- [ ] Provide OpenAPI/Swagger docs with proper annotations.

- [ ] Add HATEOAS for hypermedia-driven APIs.

- [ ] Implement a GraphQL API in parallel with REST.

# Security / Compliance

- [ ] Add two-factor authentication (2FA) with OTP/email.

- [ ] Implement audit trails (who did what, when).

- [ ] Demonstrate GDPR-like data handling (soft delete / anonymization).

# Cloud / Deployment

- [ ] Containerize with Docker, orchestrate with Kubernetes manifests.

- [ ] Add CI/CD pipeline (GitHub Actions) for tests, linting, and deployment.

- [ ] Externalize configs with Spring Cloud Config or environment variables.

# Scalability Enhancements

- [ ] Introduce caching (Redis) for frequently accessed data (e.g., user lookups).

- [ ] Implement rate limiting (e.g., Bucket4j or Redis) to prevent abuse.
