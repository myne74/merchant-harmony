# Repository Structure

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the repository and module structure for Merchant Harmony.

It is intended to guide the initial Spring Boot project setup and keep the codebase organized as the platform grows.

---

## Repository Model

Merchant Harmony uses one Git repository with a Maven multi-module structure.

```text
merchant-harmony/
│
├── pom.xml
├── README.md
├── AI_CONTEXT.md
├── PROJECT_STATE.md
│
├── auth-service/
├── engagement-service/
├── common/
│
├── docs/
├── docker/
└── scripts/
```

---

## Maven Modules

### Parent Project

```text
merchant-harmony/pom.xml
```

Purpose:

- Central dependency management
- Java version management
- Spring Boot version management
- Maven plugin configuration
- Module declaration

Packaging:

```text
pom
```

---

### auth-service

Spring Boot application.

Responsibilities:

- Merchant registration authentication flow
- Customer registration authentication flow
- OTP generation
- OTP validation
- JWT generation
- Authentication APIs

Suggested package:

```text
com.merchantharmony.auth
```

---

### engagement-service

Spring Boot application.

Responsibilities:

- Merchant profile
- Customer profile
- Merchant QR Code
- Merchant-Customer association
- Feedback Topic Master
- Merchant Topics
- Merchant Landing
- Feedback Threads
- Comments

Suggested package:

```text
com.merchantharmony.engagement
```

---

### common

Shared modules used across services.

Initial recommendation:

```text
common/
├── common-api/
├── common-security/
└── common-test/
```

Use common modules only when duplication appears. Do not overuse shared libraries early.

---

## Recommended Service Structure

Each service should follow a package structure similar to:

```text
src/main/java/com/merchantharmony/<service>/
│
├── config/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── mapper/
├── exception/
└── util/
```

Tests:

```text
src/test/java/com/merchantharmony/<service>/
```

Resources:

```text
src/main/resources/
├── application.yml
├── application-local.yml
└── db/migration/
```

---

## Database Migration Location

Flyway migrations should live inside each service that owns the database.

```text
auth-service/src/main/resources/db/migration/

engagement-service/src/main/resources/db/migration/
```

Each service owns and evolves its own schema.

---

## Docker Structure

```text
docker/
├── docker-compose.yml
├── auth-service/
└── engagement-service/
```

Local development should support:

```bash
docker compose up
```

---

## Documentation Structure

Engineering documentation is maintained under:

```text
docs/
```

Documentation should be updated only when implementation changes product behavior, architecture, APIs, data model, or operational behavior.

---

## Scripts

```text
scripts/
```

Used for local setup and developer automation.

Examples:

- database setup
- local cleanup
- smoke tests
- utility scripts

---

## Naming Conventions

### Maven

```text
groupId: com.merchantharmony
artifactId: merchant-harmony
```

Service artifacts:

```text
auth-service
engagement-service
common-api
common-security
common-test
```

### Java Packages

```text
com.merchantharmony.auth
com.merchantharmony.engagement
com.merchantharmony.common
```

### Git Branches

Recommended branch naming:

```text
feature/<short-description>
docs/<short-description>
fix/<short-description>
```

Examples:

```text
feature/merchant-registration
docs/repository-structure
fix/jwt-validation
```

---

## Initial Development Recommendation

Start with:

1. Parent Maven project
2. auth-service module
3. engagement-service module
4. Docker Compose with PostgreSQL databases
5. Health endpoints
6. Merchant registration vertical slice

Avoid adding unnecessary shared modules until duplication appears.

---

## Related Documents

- Architecture.md
- ADR-001-ServiceBoundaries.md
- ADR-005-DatabasePerService.md
- Docker.md
- BuildPipeline.md
