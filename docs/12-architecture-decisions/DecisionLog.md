# Decision Log

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

# Purpose

This document records significant architectural and product decisions made throughout the Merchant Harmony project.

Detailed reasoning is maintained in individual Architecture Decision Records (ADRs). This document provides a chronological summary.

---

# Decision Principles

Architectural decisions are evaluated using the following priorities:

1. Simplicity over unnecessary flexibility
2. Excellent first-time user experience
3. Clear ownership of data
4. Minimal API surface
5. Incremental evolution
6. Backward compatibility where practical

---

# Decision History

| Date | ID | Decision | Status |
|------|----|----------|--------|
| 2026-06-27 | ADR-001 | Adopt Microservice Architecture (Auth Service + Engagement Service) | Accepted |
| 2026-06-27 | ADR-002 | Separate Merchant and Customer authentication flows | Accepted |
| 2026-06-27 | ADR-003 | Use SMS OTP with JWT authentication | Accepted |
| 2026-06-27 | ADR-004 | Merchant QR Code is the primary customer entry point | Accepted |
| 2026-06-27 | ADR-005 | Introduce Merchant Landing after QR scan | Accepted |
| 2026-06-27 | ADR-006 | Manage Feedback Topics as category master data | Accepted |
| 2026-06-27 | ADR-007 | Automatically provision Merchant Topics during registration | Accepted |
| 2026-06-27 | ADR-008 | Every Feedback Thread belongs to exactly one active Merchant Topic | Accepted |
| 2026-06-27 | ADR-009 | Merchant must always maintain at least one active Feedback Topic | Accepted |
| 2026-06-27 | ADR-010 | Merchant and Customer may share the same phone number | Accepted |
| 2026-06-27 | ADR-011 | Separate Merchant and Customer registration/login APIs | Accepted |
| 2026-06-27 | ADR-012 | Database-per-service architecture | Accepted |

---

# Future Decisions

The following topics are expected to receive ADRs as the platform evolves:

- Loyalty Platform
- Promotions Engine
- Notification Service
- AI-assisted Feedback Analysis
- Event-Driven Architecture (Kafka)
- Distributed Caching
- Search
- Multi-region Deployment
- Multi-tenant Architecture

---

# Related Documents

- ADR-001-ServiceBoundaries.md
- ADR-002-Authentication.md
- ADR-003-FeedbackTopicModel.md
- ADR-004-MerchantLanding.md
- Architecture.md
