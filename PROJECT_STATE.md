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
Phase 3 – Engagement Service Implementation (Complete)
```

MVP implementation is complete. All core endpoints implemented across both services.

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
- Flyway migrations for auth_db (merchant, customer, otp_request)
- Flyway migrations for engagement_db (feedback_topic_master, merchant_customer, merchant_topic, feedback_thread, comment + seed data)
- Auth service implementation: Merchant entity/repo/service/controller, Customer entity/repo/service/controller, OTP flow (initiate + verify), JWT issuance, internal merchant profile endpoint
- common domain enums: MerchantCategory, MerchantStatus
- Lombok wired via annotationProcessorPaths in root pom
- spring-boot-flyway added (Spring Boot 4.1 auto-config split)
- Phase 3 — Engagement service implementation: all 21 backlog items complete
- FeedbackTopicMaster, MerchantTopic, MerchantCustomer, FeedbackThread, Comment entities
- Merchant profile/topics/customers endpoints
- Customer profile, merchant landing (QR resolve + association + topics), associated merchants endpoints
- Full feedback thread lifecycle: create, list, read, comment, close
- AuthServiceClient (RestClient) for cross-service calls from engagement-service
- EngagementServiceClient in auth-service; topic init wired at merchant registration (P2-004 resolved)
- Internal topic initialization endpoint in engagement-service (permitted without JWT)
- Internal customer profile endpoint added to auth-service
- Internal merchant-by-QR endpoint added to auth-service
- Role-based path security in engagement-service SecurityConfig
- spring.jpa.open-in-view=false on both services

---

## Current Focus

Phase 3 complete. MVP backend is fully implemented. Next: integration testing and end-to-end verification.

---

## Next Milestone

```text
MVP Verification – End-to-End Integration Test
```

Planned work:

- Manual end-to-end API test: register merchant → login → scan QR → create feedback thread → merchant responds → close thread
- Review Spring Boot 4.1 open-in-view=false impact
- Consider adding OpenAPI/Swagger documentation

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
