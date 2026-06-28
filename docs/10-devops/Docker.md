# Docker

| Property | Value |
|----------|-------|
| Version | 1.1 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the Docker approach for local development of Merchant Harmony.

---

## Local Development Containers

Docker Compose manages infrastructure containers for local development.
Services (auth-service, engagement-service) run from the IDE or Maven during development.

Current containers:

- PostgreSQL for auth-service (auth-db)
- PostgreSQL for engagement-service (engagement-db)

Future containers (when Dockerfiles are added):

- auth-service
- engagement-service

---

## Recommended Ports

| Component | Port |
|----------|------|
| auth-service | 8081 |
| engagement-service | 8082 |
| auth-db | 5433 |
| engagement-db | 5434 |

---

## Docker Compose Location

```text
docker/docker-compose.yml
```

## Running Locally

Start all infrastructure containers:

```bash
cd docker && docker compose up -d
```

Stop all containers:

```bash
cd docker && docker compose down
```

Stop and remove volumes (wipes database data):

```bash
cd docker && docker compose down -v
```

---

## Environment Configuration

Service configuration uses environment variable substitution in `application.properties`:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5433/auth_db}
spring.datasource.username=${DB_USERNAME:merchantharmony}
spring.datasource.password=${DB_PASSWORD:merchantharmony}
```

The default values work for local development. Override via environment variables for containerised or production deployments.

Secrets must not be committed.

---

## Future Enhancements

- Redis
- Kafka
- Observability stack
- Local API gateway
- Testcontainers alignment

---

## Related Documents

- DeploymentStrategy.md
- Environments.md
- BuildPipeline.md
