# Docker

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Draft |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the Docker approach for local development of Merchant Harmony.

---

## Local Development Containers

The MVP should support local execution with Docker Compose.

Initial containers:

- PostgreSQL for auth-service
- PostgreSQL for engagement-service
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

## Docker Compose Goals

Docker Compose should allow a developer to start the local environment using:

```bash
docker compose up
```

---

## Environment Configuration

Each service should support local configuration through:

- application-local.yml
- environment variables
- Docker Compose variables

Secrets should not be committed.

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
