# Scope

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document defines the current scope of Merchant Harmony and establishes the functional boundaries for the current release.

---

## In Scope

### Merchant

- Self-registration
- SMS OTP authentication
- JWT-based authorization
- Permanent Merchant QR Code
- View associated customers
- View feedback threads
- Reply to customer feedback
- Close feedback threads

### Customer

- Self-registration
- SMS OTP authentication
- JWT-based authorization
- Scan Merchant QR Code
- Associate with merchants
- Create feedback threads
- Participate in threaded conversations
- View personal feedback history

### Feedback

- Thread-based conversations
- Text-only comments
- OPEN and CLOSED thread status
- Merchant-owned feedback
- Customer-owned feedback

### Platform

- RESTful APIs
- Spring Boot microservices
- PostgreSQL persistence
- Docker-ready deployment
- JWT security

---

## Out of Scope

The following capabilities are intentionally deferred and maintained in **FutureRequirements.md**:

- Customer QR Code
- Merchant hierarchy
- Merchant scans Customer QR
- Promotions
- Loyalty programs
- Rewards
- Coupons
- Notifications
- LLM integration
- Analytics dashboard
- Administration portal
- File and image attachments
- Rate limiting
- DDoS protection
- Multi-region deployment

---

## Scope Principles

- Deliver a complete and functional MVP.
- Keep the implementation simple and maintainable.
- Build an extensible foundation for future growth.
- Defer non-essential capabilities to future releases.
- Evolve the platform incrementally without major redesign.

---

## Related Documents

- Vision.md
- ProblemStatement.md
- Goals.md
- ProductRequirements.md
- FutureRequirements.md
