# Product Requirements

| Property | Value |
|----------|-------|
| Version | 1.1 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document defines the current functional and non-functional requirements of Merchant Harmony.

It is the authoritative source describing what the product supports today. Planned capabilities remain in FutureRequirements.md until they become part of the product.

---

## Product Vision

Merchant Harmony is a Merchant-Customer engagement platform that helps businesses build long-term customer relationships through direct feedback and meaningful interactions.

The product starts with a simple feedback workflow and is designed to evolve into a broader engagement platform with promotions, loyalty, analytics, and AI-assisted insights.

---

## Current Product Scope

The current product includes:

- Merchant self-registration
- Customer self-registration
- SMS OTP authentication
- JWT-based authorization
- Permanent Merchant QR Code generation
- Category-based default feedback topics
- Merchant Landing after customer scans Merchant QR Code
- Merchant-Customer association
- Feedback thread creation
- Threaded text conversations
- Merchant replies to customer feedback
- Merchant closes feedback threads

---

## Functional Requirements

### Merchant Registration

- Merchant can register using business details, phone number, category, and address.
- Merchant phone number must be unique within merchants.
- Merchant may share the same phone number with a customer account.
- Merchant QR Code is generated during registration.
- Default feedback topics are enabled based on merchant category.
- Merchant should be operational immediately after registration.

### Customer Registration

- Customer can register using name and phone number.
- Email is optional.
- Customer phone number must be unique within customers.
- Customer may share the same phone number with a merchant account.
- Customer QR Code is not part of the current product.

### Authentication

- Merchant and Customer authenticate using SMS OTP.
- Merchant and Customer use separate authentication endpoints.
- JWT is issued after successful OTP verification.
- JWT is required for secured APIs.

### Merchant Landing

- Customer scans Merchant QR Code.
- System validates the merchant.
- System creates association if missing.
- System returns merchant summary and active feedback topics.
- Customer uses Merchant Landing to start feedback quickly.

### Feedback Topics

- Feedback topics are based on merchant category master data.
- Merchant topics are enabled automatically during registration.
- Merchant can enable or disable its own topics.
- Merchant must always have at least one active topic.
- Every feedback thread must reference exactly one active merchant topic.

### Feedback Threads

- Customer creates feedback for an associated merchant.
- Customer selects exactly one active merchant topic.
- Thread starts as OPEN.
- Customer and Merchant can exchange text comments.
- Merchant can close the thread.
- Closed threads are read-only.

---

## Non-Functional Requirements

- RESTful APIs
- Stateless microservices
- PostgreSQL persistence
- Docker-ready deployment
- Structured logging
- Health endpoints
- JWT security
- Clean, maintainable architecture

---

## Current Architecture

Services:

- auth-service
- engagement-service

---

## Out of Scope

The following capabilities are intentionally excluded from the current product and are maintained in FutureRequirements.md:

- Customer QR Code
- Merchant hierarchy
- Merchant scans Customer QR
- Promotions
- Loyalty points
- Rewards
- Coupons
- Notifications
- LLM integration
- Analytics dashboard
- Administration portal
- Image attachments
- File attachments
- Rate limiting
- DDoS protection
- Multi-region deployment

---

## Related Documents

- FutureRequirements.md
- Architecture.md
- ApiSpecification.md
- DataModel.md
- BusinessRules.md
- DecisionLog.md

---

## Revision Policy

This document is a living artifact.

Whenever a feature is implemented:

1. Update this document.
2. Remove the feature from FutureRequirements.md if applicable.
3. Update the corresponding architecture, API, database, and decision documents.
4. Record significant architectural decisions in the ADRs and DecisionLog.
