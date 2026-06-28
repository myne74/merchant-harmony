# Data Model

| Property | Value |
|----------|-------|
| Version | 2.3 |
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

A merchant owns customer associations, feedback threads, conversations, and enabled feedback topics. Each merchant is uniquely identified by a permanent Merchant QR Code.

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
├── MerchantTopic (1:N)
├── FeedbackThread (1:N)
└── Customer (N:N via MerchantCustomer)
```

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

- Association is created when a customer scans a Merchant QR Code through Merchant Landing.
- Duplicate associations are not allowed.
- Association remains permanent for the current product.

### Related Entities

```text
MerchantCustomer
├── Merchant (N:1)
└── Customer (N:1)
```

---

# FeedbackTopicMaster

## Description

Represents centrally managed feedback topics by merchant category.

Feedback Topic Master avoids redundant merchant-created topics and allows merchants to be operational immediately after registration.

### Attributes

| Field | Notes |
|------|-------|
| topicId | UUID |
| merchantCategory | RESTAURANT, GROCERY, SALON, RETAIL, OTHER |
| name | Topic display name |
| type | Topic classification |
| displayOrder | Display order within category |
| active | Whether topic is currently available as master data |
| createdAt | Audit |
| updatedAt | Audit |

### Business Rules

- Active master topics are used to initialize merchant topics during merchant registration.
- `displayOrder` controls how topics appear in Merchant Landing.
- `defaultEnabled` is not required because all active master topics for a merchant category are enabled during registration.

### Example Topics

```text
RESTAURANT
1 Food
2 Service
3 Billing
4 Cleanliness
5 General

SALON
1 Haircut
2 Staff
3 Appointment
4 Billing
5 General
```

### Related Entities

```text
FeedbackTopicMaster
└── MerchantTopic (1:N)
```

---

# MerchantTopic

## Description

Represents the feedback topics enabled for a specific merchant.

Merchant topics are created from active Feedback Topic Master records during merchant registration.

### Attributes

| Field | Notes |
|------|-------|
| merchantTopicId | UUID |
| merchantId | FK |
| topicId | FK to FeedbackTopicMaster |
| active | Whether customers can select this topic |
| createdAt | Audit |
| updatedAt | Audit |

### Business Rules

- Merchant topics are initialized from active master topics for the merchant category.
- Merchants may enable or disable their topics.
- A merchant must always have at least one active feedback topic.
- Every feedback thread must reference exactly one active merchant topic.

### Related Entities

```text
MerchantTopic
├── Merchant (N:1)
├── FeedbackTopicMaster (N:1)
└── FeedbackThread (1:N)
```

---

# FeedbackThread

## Description

Represents a conversation initiated by a customer for a specific merchant topic.

### Attributes

| Field | Notes |
|------|-------|
| threadId | UUID |
| merchantId | FK |
| customerId | FK |
| merchantTopicId | FK |
| status | OPEN / CLOSED |
| createdAt | Audit |
| closedAt | Nullable |

### Business Rules

- Every feedback thread belongs to exactly one merchant.
- Every feedback thread belongs to exactly one customer.
- Every feedback thread references exactly one active merchant topic.
- Multiple threads per merchant/customer are allowed.
- Only merchants can close a thread.
- Closed threads become read-only.

### Related Entities

```text
FeedbackThread
├── Merchant (N:1)
├── Customer (N:1)
├── MerchantTopic (N:1)
└── Comment (1:N)
```

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
FeedbackTopicMaster
        │
        └── MerchantTopic
                  │
Merchant ─────────┼──────── FeedbackThread ───── Comment
   │              │                │
   └── MerchantCustomer ─────── Customer
```

---

## Design Principles

- UUID primary keys
- Audit timestamps
- Foreign key integrity
- Normalized schema
- Database per service
- Master data avoids redundant merchant setup
- Incremental evolution without breaking compatibility

---

## Related Documents

- ProductRequirements.md
- BusinessRules.md
- Architecture.md
- ApiSpecification.md
- Authentication.md
