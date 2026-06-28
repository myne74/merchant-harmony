# JWT Design

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines how JWT tokens are structured, issued, and validated in Merchant Harmony.

---

## JWT Usage

JWT is issued after successful SMS OTP verification.

Clients send JWT on secured APIs using:

```http
Authorization: Bearer <token>
```

---

## Claims

Recommended claims:

| Claim | Purpose |
|------|---------|
| sub | Authenticated user ID |
| role | MERCHANT or CUSTOMER |
| userType | MERCHANT or CUSTOMER |
| iat | Issued at |
| exp | Expiration time |
| jti | Token identifier |

Business data should not be stored in JWT.

---

## Token Lifetime

MVP recommendation:

- Access token: short-lived
- Refresh token: future scope

Exact token duration can be configured through application properties.

---

## Validation Rules

Every secured request must validate:

- Token signature
- Expiration
- Required claims
- Role
- User identity
- Resource ownership

---

## Spring Security Flow

```text
Request
  ↓
JWT Filter
  ↓
Validate Token
  ↓
Extract Claims
  ↓
Build Authentication Context
  ↓
Controller
  ↓
Service Ownership Validation
```

---

## Future Enhancements

- Refresh tokens
- Token revocation
- Device sessions
- Redis-backed token blacklist
- Rotating signing keys

---

## Related Documents

- Authentication.md
- Authorization.md
- ADR-002-Authentication.md
