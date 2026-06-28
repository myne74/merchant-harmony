# Architecture

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document describes the high-level architecture of Merchant Harmony for the current MVP.

---

## Architecture Style

Merchant Harmony follows a microservice architecture. Each service owns its business capabilities, APIs, and data while communicating over REST for the MVP.

---

## Services

### auth-service

Responsibilities:

- Merchant registration
- Customer registration
- SMS OTP verification
- JWT generation
- Authentication APIs

### engagement-service

Responsibilities:

- Merchant QR Code
- Merchant-Customer association
- Feedback threads
- Thread comments
- Merchant responses

---

## Technology Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- Docker

---

## High-Level Flow

1. Merchant registers.
2. Merchant receives a permanent QR Code.
3. Customer registers.
4. Customer scans the Merchant QR Code.
5. Association is created.
6. Customer creates a feedback thread.
7. Merchant and customer exchange comments.
8. Merchant closes the thread.

---

## Design Principles

- Stateless services
- REST-first APIs
- JWT-secured endpoints
- Database per service
- Clear separation of responsibilities
- Incremental evolution without overengineering

---

## Future Evolution

Future capabilities such as notifications, promotions, loyalty, analytics, and AI integration will be introduced without changing the core service boundaries.

---

## Related Documents

- ProductRequirements.md
- BusinessRules.md
- ApiSpecification.md
- DataModel.md
- Authentication.md
- DecisionLog.md
