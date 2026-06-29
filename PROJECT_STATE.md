# Project State

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document provides the current project snapshot for Merchant Harmony.

It enables developers, reviewers, architects, and future contributors to quickly understand the current state of the project without reviewing implementation details.

This document should be updated at the end of each major milestone or development sprint.

---

## Current Phase

```text
Phase 1 – Foundation Implementation
```

Active implementation has begun. Engineering foundation is complete.

---

## Current Product

Merchant Harmony is a Merchant–Customer engagement platform focused on helping merchants collect customer feedback, maintain customer relationships, and evolve into a broader customer engagement platform.

The current implementation scope includes:

- Merchant self-registration
- Customer self-registration
- SMS OTP authentication
- JWT authorization
- Merchant QR Code generation
- Customer scans Merchant QR Code
- Merchant–Customer association
- Feedback thread creation
- Threaded conversations
- Merchant closes feedback threads

---

## Completed

The following engineering artifacts have been completed or drafted:

- Repository structure
- Engineering Wiki structure
- AI Context
- Product Requirements
- Future Requirements
- Initial Architecture
- Initial API Specification
- Engineering naming conventions
- Repository organization

The following Phase 1 items are complete:

- Maven multi-module parent (Spring Boot 4.1, Java 21)
- auth-service module scaffold
- engagement-service module scaffold
- Dependency stack configured (web, JPA, security, validation, actuator, PostgreSQL, JJWT)
- Port assignments: auth-service 8081, engagement-service 8082
- Docker Compose with PostgreSQL for auth-db (5433) and engagement-db (5434)
- common module (shared library jar)
- Global exception handling in common (ErrorCode, ErrorResponse, MerchantHarmonyException, GlobalExceptionHandler)
- JWT infrastructure in common (UserRole, UserPrincipal, JwtProperties, JwtService, JwtAuthenticationFilter, JwtAuthenticationEntryPoint, JwtAccessDeniedHandler)
- SecurityConfig in auth-service (public auth endpoints, stateless JWT)
- SecurityConfig in engagement-service (all endpoints require authentication)

---

## Current Focus

The current objectives are:

- Flyway migrations + initial schema

---

## Next Milestone

```text
Phase 2 – Auth Service Implementation
```

Planned work:

- Merchant registration
- Customer registration
- SMS OTP flow
- JWT issue and validation
- Auth service integration tests

---

## Current Blockers

None.

---

## Documentation Baseline

Core engineering artifacts include:

- AI_CONTEXT.md
- ProductRequirements.md
- FutureRequirements.md
- Architecture.md
- ApiSpecification.md
- DataModel.md
- Authentication.md
- DecisionLog.md

---

## Update Guidelines

Update this document when:

- The project enters a new phase.
- A major milestone is completed.
- A blocker is introduced or resolved.
- A significant architectural or product decision is made.

Keep this document concise and current. It should always reflect the present state of the project.
