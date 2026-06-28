# Authorization

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

# Purpose

This document defines how Merchant Harmony authorizes authenticated users to access protected resources.

Authentication verifies identity. Authorization verifies permissions and ownership.

---

# Authorization Model

Merchant Harmony uses Role-Based Access Control (RBAC) combined with resource ownership validation.

Supported roles:

- MERCHANT
- CUSTOMER

Every secured request requires:

1. Valid JWT
2. Valid role
3. Resource ownership verification

---

# Role Responsibilities

## Merchant

Can:

- View own profile
- View associated customers
- View merchant topics
- Enable/disable merchant topics
- View feedback threads
- Reply to feedback
- Close feedback threads

Cannot:

- Access another merchant's resources
- Access customer profiles
- Modify customer data

---

## Customer

Can:

- View own profile
- Scan Merchant QR Codes
- Access Merchant Landing
- View associated merchants
- Create feedback threads
- Reply to own feedback threads

Cannot:

- Access merchant administration APIs
- View another customer's data
- Close feedback threads

---

# Ownership Validation

Role validation alone is not sufficient.

Examples:

Merchant:

```text
JWT Merchant A
        │
Requests Thread T1
        │
System verifies:
Thread belongs to Merchant A
```

Customer:

```text
JWT Customer B
        │
Requests Thread T2
        │
System verifies:
Thread belongs to Customer B
```

Requests failing ownership validation return:

```http
403 Forbidden
```

---

# Endpoint Access Matrix

| Resource | Merchant | Customer |
|----------|:--------:|:--------:|
| Merchant Profile | ✔ Own | ✖ |
| Customer Profile | ✖ | ✔ Own |
| Merchant Landing | ✖ | ✔ |
| Merchant Topics | ✔ Own | Read via Landing |
| Feedback Threads | ✔ Own | ✔ Own |
| Comments | ✔ Own | ✔ Own |

---

# Security Principles

- Deny by default
- Least privilege
- Ownership before data access
- Never trust client identifiers
- Resolve identity from JWT whenever possible

---

# Future Expansion

Potential future roles:

- ADMIN
- SUPPORT
- READ_ONLY_AUDITOR

Authorization policies should evolve without changing existing APIs.

---

# Related Documents

- Authentication.md
- BusinessRules.md
- ApiSpecification.md
- ADR-002-Authentication.md
