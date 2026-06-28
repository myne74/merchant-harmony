# Architecture

| Property | Value |
|----------|-------|
| Version | 1.1 |
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

- Merchant registration authentication flow
- Customer registration authentication flow
- SMS OTP generation
- SMS OTP validation
- JWT generation
- Authentication APIs

### engagement-service

Responsibilities:

- Merchant profile
- Customer profile
- Merchant QR Code
- Merchant-Customer association
- Feedback Topic Master
- Merchant Topics
- Merchant Landing
- Feedback threads
- Thread comments
- Merchant responses

---

## Technology Stack

- Java 21
- Spring Boot 4.1
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- Docker

---

## High-Level Flow

```text
Merchant registers
        ↓
Merchant category is selected
        ↓
Default feedback topics are enabled
        ↓
Merchant QR Code is generated
        ↓
Customer registers and logs in
        ↓
Customer scans Merchant QR Code
        ↓
Merchant Landing creates association if needed
        ↓
Merchant Landing returns active feedback topics
        ↓
Customer selects one topic and submits feedback
        ↓
Merchant and customer exchange comments
        ↓
Merchant closes the thread
```

---

## Domain Flow

```text
FeedbackTopicMaster
        ↓
MerchantTopic
        ↓
MerchantLanding
        ↓
FeedbackThread
        ↓
Comment
```

---

## Design Principles

- Stateless services
- REST-first APIs
- JWT-secured endpoints
- Database per service
- Clear separation of responsibilities
- Master data driven feedback topics
- Simple first-time user experience
- Incremental evolution without overengineering

---

## Important Design Decisions

### Merchant Landing

Merchant Landing is introduced to simplify the first customer interaction after scanning a Merchant QR Code.

It returns:

- Merchant summary
- Association status
- Active feedback topics

This avoids requiring the customer to manually navigate merchant details before submitting feedback.

### Feedback Topics

Feedback topics are maintained as master data by merchant category. Merchant-specific topic records are created during merchant registration.

This avoids redundant topic setup and makes merchants operational immediately.

### Feedback Thread Topic Requirement

Every feedback thread must reference exactly one active Merchant Topic. There is no nullable topic and no separate general feedback endpoint.

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
