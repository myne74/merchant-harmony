# Milestones

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

Milestones mark the completion of a meaningful, deployable slice of the Merchant Harmony MVP. Each milestone represents a state where the system is coherent end-to-end for the capabilities it includes.

---

## Milestone Overview

| ID | Milestone | Status | Backlog Items |
|----|-----------|--------|---------------|
| M-001 | Foundation Complete | Done | P1-001 → P1-013 |
| M-002 | Auth Service Complete | Pending | P2-001 → P2-011 |
| M-003 | MVP Complete | Pending | P3-001 → P3-021 |

---

## M-001 — Foundation Complete ✅

**Completed:** 2026-06-28

**What it means:** Both services start, connect to their own PostgreSQL databases, run all Flyway migrations cleanly, and enforce JWT-based security. The skeleton is in place for all business logic.

**Verification:**
- `./mvnw test` passes — context load tests confirm both services start with Flyway migrations applied
- Docker Compose brings up auth-db and engagement-db with health checks
- `GET /actuator/health` returns 200 on both services (ports 8081 and 8082)
- JWT filter rejects unauthenticated requests to secured endpoints with 401

---

## M-002 — Auth Service Complete

**Target:** Phase 2

**What it means:** Merchants and customers can fully register and authenticate. A valid JWT can be obtained for both user types and used to access secured endpoints in the engagement-service.

**Verification:**
- `POST /api/v1/auth/merchants/register` → creates merchant, returns QR code
- `POST /api/v1/auth/merchants/login` → creates OTP request, returns otpRequestId
- `POST /api/v1/auth/merchants/verify-otp` → validates OTP, returns JWT
- Same flow works end-to-end for customers
- JWT issued by auth-service is accepted by engagement-service's security filter
- Duplicate phone number registration returns 409
- Expired or invalid OTP returns appropriate error
- Merchant topics are initialized in engagement-service at merchant registration

---

## M-003 — MVP Complete

**Target:** Phase 3

**What it means:** The full product loop is working end to end. A merchant registers, a customer scans the QR code, a feedback thread is created, both sides exchange messages, and the merchant closes the thread.

**End-to-End Scenario:**

```
1. Merchant registers → receives QR code, topics initialized
2. Merchant logs in → receives JWT
3. Customer registers
4. Customer logs in → receives JWT
5. Customer scans QR → Merchant Landing returns merchant info + topics
6. Customer creates feedback thread → references one active topic
7. Merchant replies to thread
8. Customer replies back
9. Merchant closes thread → thread becomes read-only
10. Customer attempts to comment on closed thread → 409 THREAD_CLOSED
```

**Verification:**
- All 14 client-facing API endpoints return correct responses
- Ownership rules enforced: customers see only their data, merchants see only their data
- Business rules enforced: last active topic cannot be disabled, closed threads are read-only, customer must be associated before creating a thread
- Internal service communication works: Merchant Landing returns merchant profile fetched from auth-service

---

## Related Documents

- Epics.md
- ProductBacklog.md
- ProductRequirements.md
