# Epics

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

Epics represent the major capability areas of the Merchant Harmony MVP. Each epic groups related features that together deliver a complete user-facing outcome.

---

## Epic Overview

| ID | Epic | Service | Status |
|----|------|---------|--------|
| E-001 | Merchant Authentication | auth-service | Pending |
| E-002 | Customer Authentication | auth-service | Pending |
| E-003 | Merchant Profile & Topic Management | engagement-service | Pending |
| E-004 | Customer Profile & Merchant Discovery | engagement-service | Pending |
| E-005 | Feedback Threads & Conversations | engagement-service | Pending |
| E-006 | Internal Service Communication | auth-service + engagement-service | Pending |

---

## E-001 — Merchant Authentication

**Goal:** A merchant can register, log in via SMS OTP, and receive a JWT to access secured APIs.

**Scope:**
- Merchant self-registration with full business details
- QR Code generation at registration
- OTP-based login (send OTP)
- OTP verification and JWT issuance

**Endpoints:**
- `POST /api/v1/auth/merchants/register`
- `POST /api/v1/auth/merchants/login`
- `POST /api/v1/auth/merchants/verify-otp`

**Done when:** A merchant can register, receive a QR code, log in with their phone number, verify the OTP, and receive a valid JWT that the engagement-service accepts.

---

## E-002 — Customer Authentication

**Goal:** A customer can register, log in via SMS OTP, and receive a JWT to access secured APIs.

**Scope:**
- Customer self-registration (name, phone, optional email)
- OTP-based login (send OTP)
- OTP verification and JWT issuance

**Endpoints:**
- `POST /api/v1/auth/customers/register`
- `POST /api/v1/auth/customers/login`
- `POST /api/v1/auth/customers/verify-otp`

**Done when:** A customer can register, log in with their phone number, verify the OTP, and receive a valid JWT.

---

## E-003 — Merchant Profile & Topic Management

**Goal:** A merchant can view their profile, see their assigned feedback topics, and toggle topics on or off.

**Scope:**
- Merchant profile retrieval (self)
- List merchant customers (associated)
- List merchant feedback topics
- View master topic catalog by category
- Enable or disable a merchant feedback topic

**Endpoints:**
- `GET /api/v1/merchants/me`
- `GET /api/v1/merchants/me/customers`
- `GET /api/v1/merchants/me/feedback-topics`
- `PATCH /api/v1/merchants/me/feedback-topics/{merchantTopicId}`
- `GET /api/v1/feedback-topic-master?category=`

**Done when:** A logged-in merchant can view their profile, see which customers are associated, manage their active feedback topics, and the system prevents disabling the last active topic.

---

## E-004 — Customer Profile & Merchant Discovery

**Goal:** A customer can view their profile, scan a merchant QR code, get associated, and see the merchant's active topics ready for feedback.

**Scope:**
- Customer profile retrieval (self)
- Merchant Landing: QR code resolution, association creation, active topic list
- List associated merchants

**Endpoints:**
- `GET /api/v1/customers/me`
- `POST /api/v1/customers/me/merchant-landing`
- `GET /api/v1/customers/me/merchants`

**Done when:** A logged-in customer can scan a QR code, land on the merchant's page, get automatically associated (or recognized as already associated), and see the merchant's active feedback topics.

---

## E-005 — Feedback Threads & Conversations

**Goal:** A customer can open a feedback thread with a merchant, both parties can exchange messages, and the merchant can close the thread.

**Scope:**
- Create feedback thread (customer only, with first message)
- Add comments to a thread (customer or merchant)
- List threads from customer's perspective
- List threads from merchant's perspective
- View a single thread
- Close a thread (merchant only)

**Endpoints:**
- `POST /api/v1/feedback/threads`
- `GET /api/v1/feedback/threads/customer`
- `GET /api/v1/feedback/threads/merchant`
- `GET /api/v1/feedback/threads/{threadId}`
- `POST /api/v1/feedback/threads/{threadId}/comments`
- `PATCH /api/v1/feedback/threads/{threadId}/close`

**Done when:** A customer can open a thread, both sides can exchange messages, and the merchant can close it. Closed threads are read-only. Ownership rules are enforced: customers see only their threads, merchants see only their merchant's threads.

---

## E-006 — Internal Service Communication

**Goal:** Services can exchange data they need without exposing internal APIs to clients.

**Scope:**
- auth-service exposes internal merchant profile endpoint for engagement-service
- auth-service calls engagement-service to initialize merchant topics at registration

**Internal Endpoints (not client-facing):**
- `GET /api/v1/internal/merchants/{merchantId}` (auth-service → consumed by engagement-service)
- Topic initialization call at merchant registration (auth-service → engagement-service)

**Done when:** Merchant Landing correctly returns merchant profile data (businessName, displayName, category) by fetching from auth-service. Merchant topics are initialized in engagement-service automatically when a merchant registers in auth-service.

---

## Related Documents

- ProductBacklog.md
- Milestones.md
- ProductRequirements.md
- ApiSpecification.md
