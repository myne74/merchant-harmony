# Authentication

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

# Purpose

This document describes the authentication architecture for Merchant Harmony.

Authentication verifies identity. Authorization determines what an authenticated user is allowed to access.

---

# Authentication Overview

Merchant Harmony supports two user types:

- Merchant
- Customer

Each uses an independent authentication flow based on SMS One-Time Password (OTP).

Passwords are not used in the MVP.

---

# Authentication Flow

```text
User enters phone number
        │
        ▼
Generate OTP
        │
        ▼
Verify OTP
        │
        ▼
Issue JWT
        │
        ▼
Access secured APIs
```

---

# Merchant Authentication

Endpoints

```text
POST /api/v1/auth/merchants/register
POST /api/v1/auth/merchants/login
POST /api/v1/auth/merchants/verify-otp
```

Business Rules

- Phone number must be unique within merchants.
- Merchant registration generates a permanent Merchant QR Code.
- JWT contains Merchant identity and role.

---

# Customer Authentication

Endpoints

```text
POST /api/v1/auth/customers/register
POST /api/v1/auth/customers/login
POST /api/v1/auth/customers/verify-otp
```

Business Rules

- Phone number must be unique within customers.
- Customer QR Code is not part of the MVP.
- JWT contains Customer identity and role.

---

# JWT

Typical claims:

```text
sub        User ID
role       MERCHANT | CUSTOMER
userType   MERCHANT | CUSTOMER
iat        Issued At
exp        Expiration
```

Business data is never embedded inside the token.

---

# Security Principles

- HTTPS required
- Short-lived OTPs
- Short-lived JWTs
- JWT validated on every secured request
- Ownership validation after authentication
- Stateless authentication

---

# Future Enhancements

- Refresh Tokens
- Token Revocation
- MFA
- OAuth2 / Social Login
- Passkeys

---

# Related Documents

- Authorization.md
- ApiSpecification.md
- BusinessRules.md
- ADR-002-Authentication.md
