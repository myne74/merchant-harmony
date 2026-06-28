# Data Model

| Property | Value |
|----------|-------|
| Version | 2.1 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document defines the core business entities for Merchant Harmony. It serves as the blueprint for the database schema, JPA entities, REST models, and future platform evolution.

---

# Merchant

## Description

Represents a single physical business location registered within Merchant Harmony.

A merchant owns customer associations, feedback threads, and conversations. Each merchant is uniquely identified by a permanent Merchant QR Code.

### Identity

| Field | Required | Notes |
|--------|----------|-------|
| merchantId | System | UUID |
| businessName | Yes | Legal or business name |
| displayName | No | Public-facing name |
| category | Yes | RESTAURANT, GROCERY, SALON, RETAIL, OTHER |
| status | System | ACTIVE / INACTIVE |

### Contact Information

| Field | Required | Notes |
|--------|----------|-------|
| phoneNumber | Yes | OTP authentication |
| email | No | Future communications |
| website | No | Business website |

### Location

| Field | Required | Notes |
|--------|----------|-------|
| addressLine1 | Yes | Street address |
| addressLine2 | No | Apartment / Suite |
| city | Yes | |
| stateProvince | Yes | |
| postalCode | Yes | |
| country | Yes | |

### QR Information

| Field | Required | Notes |
|--------|----------|-------|
| qrCode | System | Permanent identifier |
| qrCodeGeneratedAt | System | Audit |

### Audit

- createdAt
- updatedAt

### Related Entities

```text
Merchant
│
├── MerchantCustomer (1:N)
├── FeedbackThread (1:N)
└── Customer (N:N via MerchantCustomer)
```

### Future Expansion

- logoUrl
- businessDescription
- businessHours
- supportEmail
- supportPhone
- socialMediaLinks
- timezone
- preferredLanguage
- brandingTheme
- notificationPreferences
- customFeedbackCategories

---

# Customer

## Description

Represents an end user who interacts with one or more merchants.

### Identity

| Field | Required | Notes |
|--------|----------|-------|
| customerId | System | UUID |
| name | Yes | Customer name |

### Contact Information

| Field | Required | Notes |
|--------|----------|-------|
| phoneNumber | Yes | OTP authentication |
| email | No | Optional contact |

### Audit

- createdAt
- updatedAt

### Related Entities

```text
Customer
│
├── MerchantCustomer (1:N)
├── FeedbackThread (1:N)
└── Merchant (N:N via MerchantCustomer)
```

### Future Expansion

- profilePhoto
- preferredLanguage
- notificationPreferences
- marketingConsent

---

# MerchantCustomer

## Description

Represents the permanent association between a merchant and a customer.

### Attributes

- associationId (UUID)
- merchantId
- customerId
- associatedAt

### Business Rules

- Association is created by customer QR scan.
- Duplicate associations are not allowed.
- Association remains permanent for the MVP.

### Related Entities

```text
MerchantCustomer
├── Merchant (N:1)
└── Customer (N:1)
```

---

# FeedbackThread

## Description

Represents a conversation initiated by a customer for a specific merchant.

### Attributes

| Field | Notes |
|------|-------|
| threadId | UUID |
| merchantId | FK |
| customerId | FK |
| category | Feedback category |
| status | OPEN / CLOSED |
| createdAt | Audit |
| closedAt | Nullable |

### Business Rules

- Multiple threads per merchant/customer are allowed.
- Only merchants can close a thread.
- Closed threads become read-only.

### Related Entities

```text
FeedbackThread
├── Merchant (N:1)
├── Customer (N:1)
└── Comment (1:N)
```

### Future Expansion

- priority
- sentiment
- assignedTo
- tags
- resolutionSummary

---

# Comment

## Description

Represents an individual message within a feedback thread.

### Attributes

- commentId (UUID)
- threadId
- authorType (CUSTOMER, MERCHANT)
- message
- createdAt

### Business Rules

- Text only.
- No edit.
- No delete.
- Cannot be added to CLOSED threads.

### Related Entities

```text
Comment
└── FeedbackThread (N:1)
```

---

## Overall Relationship View

```text
Merchant
│
├── MerchantCustomer ───── Customer
│
└── FeedbackThread
         │
         └── Comment
```

---

## Design Principles

- UUID primary keys
- Audit timestamps
- Foreign key integrity
- Normalized schema
- Database per service
- Incremental evolution without breaking compatibility

---

## Related Documents

- ProductRequirements.md
- BusinessRules.md
- Architecture.md
- ApiSpecification.md
- Authentication.md
