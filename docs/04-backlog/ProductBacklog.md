# Product Backlog

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

The complete implementation checklist for the Merchant Harmony MVP. Every item maps to a specific API endpoint or infrastructure concern. Status is updated as implementation progresses.

Status values: `Pending` · `In Progress` · `Done`

---

## Phase 1 — Foundation ✅

| ID | Item | Status |
|----|------|--------|
| P1-001 | Maven multi-module parent (Spring Boot 4.1, Java 21) | Done |
| P1-002 | auth-service scaffold (pom, main class, application.properties) | Done |
| P1-003 | engagement-service scaffold (pom, main class, application.properties) | Done |
| P1-004 | common module (shared library jar) | Done |
| P1-005 | Docker Compose — auth-db (PostgreSQL 17, port 5433) | Done |
| P1-006 | Docker Compose — engagement-db (PostgreSQL 17, port 5434) | Done |
| P1-007 | Global exception handling (ErrorCode, ErrorResponse, MerchantHarmonyException, GlobalExceptionHandler) | Done |
| P1-008 | JWT infrastructure (JwtProperties, JwtService, JwtAuthenticationFilter, JwtAuthenticationEntryPoint, JwtAccessDeniedHandler) | Done |
| P1-009 | SecurityConfig — auth-service (public auth endpoints, stateless JWT) | Done |
| P1-010 | SecurityConfig — engagement-service (all endpoints require JWT) | Done |
| P1-011 | Flyway — auth_db migrations (merchant, customer, otp_request) | Done |
| P1-012 | Flyway — engagement_db migrations (feedback_topic_master, merchant_customer, merchant_topic, feedback_thread, comment) | Done |
| P1-013 | Flyway — seed feedback_topic_master (24 topics across 5 categories) | Done |

---

## Phase 2 — Auth Service Implementation

### E-001: Merchant Authentication

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P2-001 | Merchant entity + JPA repository | — | Done |
| P2-002 | POST /api/v1/auth/merchants/register | `POST /api/v1/auth/merchants/register` | Done |
| P2-003 | QR Code generation at merchant registration | — | Done |
| P2-004 | Initialize merchant topics in engagement-service at registration | Internal call | Deferred to Phase 3 |
| P2-005 | POST /api/v1/auth/merchants/login (send OTP) | `POST /api/v1/auth/merchants/login` | Done |
| P2-006 | POST /api/v1/auth/merchants/verify-otp (verify + issue JWT) | `POST /api/v1/auth/merchants/verify-otp` | Done |

### E-002: Customer Authentication

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P2-007 | Customer entity + JPA repository | — | Done |
| P2-008 | POST /api/v1/auth/customers/register | `POST /api/v1/auth/customers/register` | Done |
| P2-009 | POST /api/v1/auth/customers/login (send OTP) | `POST /api/v1/auth/customers/login` | Done |
| P2-010 | POST /api/v1/auth/customers/verify-otp (verify + issue JWT) | `POST /api/v1/auth/customers/verify-otp` | Done |

### E-006: Internal — auth-service side

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P2-011 | Internal merchant profile endpoint (consumed by engagement-service) | `GET /api/v1/internal/merchants/{merchantId}` | Done |

---

## Phase 3 — Engagement Service Implementation

### E-003: Merchant Profile & Topic Management

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P3-001 | Merchant topic entity + JPA repository | — | Done |
| P3-002 | FeedbackTopicMaster entity + JPA repository | — | Done |
| P3-003 | GET merchant profile | `GET /api/v1/merchants/me` | Done |
| P3-004 | GET merchant customers | `GET /api/v1/merchants/me/customers` | Done |
| P3-005 | GET master feedback topics by category | `GET /api/v1/feedback-topic-master?category=` | Done |
| P3-006 | GET merchant feedback topics | `GET /api/v1/merchants/me/feedback-topics` | Done |
| P3-007 | PATCH merchant feedback topic status (enable/disable) | `PATCH /api/v1/merchants/me/feedback-topics/{merchantTopicId}` | Done |

### E-004: Customer Profile & Merchant Discovery

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P3-008 | MerchantCustomer entity + JPA repository | — | Done |
| P3-009 | GET customer profile | `GET /api/v1/customers/me` | Done |
| P3-010 | POST merchant landing (QR resolve + association + topics) | `POST /api/v1/customers/me/merchant-landing` | Done |
| P3-011 | GET associated merchants for customer | `GET /api/v1/customers/me/merchants` | Done |

### E-005: Feedback Threads & Conversations

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P3-012 | FeedbackThread entity + JPA repository | — | Done |
| P3-013 | Comment entity + JPA repository | — | Done |
| P3-014 | POST create feedback thread (with first comment) | `POST /api/v1/feedback/threads` | Done |
| P3-015 | GET customer feedback threads | `GET /api/v1/feedback/threads/customer` | Done |
| P3-016 | GET merchant feedback threads | `GET /api/v1/feedback/threads/merchant` | Done |
| P3-017 | GET single feedback thread | `GET /api/v1/feedback/threads/{threadId}` | Done |
| P3-018 | POST add comment to thread | `POST /api/v1/feedback/threads/{threadId}/comments` | Done |
| P3-019 | PATCH close feedback thread | `PATCH /api/v1/feedback/threads/{threadId}/close` | Done |

### E-006: Internal — engagement-service side

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P3-020 | REST client to call auth-service internal merchant profile API | Internal call | Done |
| P3-021 | Topic initialization endpoint (called by auth-service at merchant registration) | Internal call | Done |

---

## Phase 4 — Notification Service

### E-007: SMS OTP Delivery

| ID | Item | Endpoint | Status |
|----|------|----------|--------|
| P4-001 | notification-service Maven module scaffold (pom, main class, application.properties, test) | — | Done |
| P4-002 | `SmsProvider` interface | — | Done |
| P4-003 | `LoggingSmsProvider` (log OTP at INFO, 10% simulated failure via SecureRandom) | — | Done |
| P4-004 | `POST /api/v1/notifications/sms` internal endpoint (no JWT) | `POST /api/v1/notifications/sms` | Done |
| P4-005 | `NotificationServiceClient` in auth-service (RestClient) | — | Done |
| P4-006 | Wire OTP send through notification-service in auth-service (replace WARN log in OtpService) | — | Done |

---

## Summary

| Phase | Total | Done | Remaining |
|-------|-------|------|-----------|
| Phase 1 — Foundation | 13 | 13 | 0 |
| Phase 2 — Auth Service | 11 | 10 | 1 (P2-004 deferred to Phase 3) |
| Phase 3 — Engagement Service | 21 | 21 | 0 |
| Phase 4 — Notification Service | 6 | 6 | 0 |
| **Total** | **51** | **51** | **0** |

---

## Related Documents

- Epics.md
- Milestones.md
- ApiSpecification.md
- ProductRequirements.md
