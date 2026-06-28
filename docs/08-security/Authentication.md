# Authentication

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document defines the authentication and authorization model for Merchant Harmony.

It explains how users authenticate, how JWT tokens are used, and how access is controlled across services.

---

## Authentication Approach

Merchant Harmony uses SMS OTP authentication for both merchants and customers.

After successful OTP verification, the system issues a JWT token. The client uses this token for all secured API requests.

---

## User Roles

Supported roles:

- MERCHANT
- CUSTOMER

Each authenticated user is associated with exactly one role for the current product.

---

## Login Flow

```text
User enters phone number
        ↓
Auth Service sends OTP
        ↓
User submits OTP
        ↓
Auth Service verifies OTP
        ↓
JWT is generated
        ↓
Client uses JWT for secured APIs
```

---

## JWT Usage

Secured APIs require the following header:

```http
Authorization: Bearer <jwt>
```

The JWT should contain enough information to identify the authenticated user and role.

Recommended claims:

| Claim | Purpose |
|------|---------|
| sub | User identifier |
| role | MERCHANT or CUSTOMER |
| exp | Token expiration |
| iat | Issued time |

---

## Public APIs

The following APIs are public:

- Merchant registration
- Customer registration
- Send OTP
- Verify OTP

Public APIs do not require JWT.

---

## Secured APIs

The following areas require JWT:

- Merchant profile
- Customer profile
- Merchant-Customer association
- Feedback threads
- Comments
- Merchant feedback management

Each secured API must validate both authentication and ownership.

---

## Authorization Rules

### Merchant

A merchant can:

- Access own profile
- View own associated customers
- View own feedback threads
- Reply to own feedback threads
- Close own feedback threads

A merchant cannot:

- Access another merchant's data
- Access unrelated customer feedback
- Create feedback as a customer

### Customer

A customer can:

- Access own profile
- Associate with merchants
- View own associated merchants
- Create feedback for associated merchants
- Comment on own feedback threads

A customer cannot:

- Access another customer's data
- Access merchant-only APIs
- Close feedback threads

---

## Auth Service Responsibilities

The auth-service owns:

- OTP generation
- OTP validation
- JWT generation
- Authentication APIs
- Authentication-related validation

The auth-service does not own merchant feedback, customer associations, or feedback threads.

---

## Engagement Service Responsibilities

The engagement-service validates JWT claims and enforces authorization rules for business operations.

It owns:

- Merchant profile access
- Customer profile access
- Associations
- Feedback threads
- Comments
- Ownership checks

---

## Security Principles

- Never trust client-provided user IDs when JWT is available.
- Resolve user identity from JWT claims.
- Enforce role checks at API boundaries.
- Enforce ownership checks in service logic.
- Do not log OTP values or sensitive tokens.
- Treat phone numbers as sensitive data.
- Keep authentication stateless after JWT issuance.

---

## Future Enhancements

Future authentication capabilities may include:

- Refresh tokens
- Email/password login
- Google login
- Apple login
- Multi-factor authentication
- Session revocation
- Device trust

---

## Related Documents

- ProductRequirements.md
- BusinessRules.md
- Architecture.md
- ApiSpecification.md
- DataModel.md
- DecisionLog.md
