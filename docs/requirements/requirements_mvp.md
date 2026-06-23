# Merchant Customer Loyalty Platform (MVP)

Version: 1.0  
Status: Draft

---

## 1. Introduction

### Vision

Build a lightweight Merchant-Customer engagement platform that enables Merchants and Customers to establish long-term relationships.

The platform allows:

- Merchant self-registration
- Customer self-registration
- SMS OTP authentication
- QR-based Merchant-Customer association
- Customer feedback collection
- Merchant and Customer conversation through threaded comments

The initial focus is to provide a minimal yet usable product while keeping the architecture extensible for future features.

---

### Goals

- Allow merchants to onboard themselves.
- Allow customers to onboard themselves.
- Enable Merchant-Customer association.
- Allow customers to submit feedback.
- Allow merchants to respond to feedback.
- Maintain feedback history.
- Expose REST APIs.
- Be built using microservices.

---

### Assumptions

- Merchant represents a single physical store.
- Merchant hierarchy is not part of MVP.
- Customer can belong to multiple Merchants.
- Merchant can have multiple Customers.
- Association once created remains active.
- Feedback comments are text only.
- QR codes are permanent.
- SMS OTP is the only authentication method.

---

## 2. Business Context

The platform centralizes customer feedback and allows merchants to:

- Capture feedback directly.
- Respond to customers.
- Maintain conversation history.
- Build long-term customer relationships.

QR codes provide a simple mechanism to establish Merchant-Customer relationships.

---

## 3. Actors

### Customer

Customer can:

- Register
- Login
- Scan Merchant QR
- Associate with Merchant
- Create Feedback Thread
- View own Feedback Threads
- Reply to Feedback Threads

### Merchant

Merchant can:

- Register
- Login
- Display QR Code
- View associated customers
- View feedback threads
- Reply to feedback threads
- Close feedback threads

---

## 4. Authentication and Authorization

Authentication shall be performed using SMS OTP.

Login Flow:

1. User enters phone number.
2. System sends OTP.
3. User enters OTP.
4. Auth Service validates OTP.
5. Auth Service generates JWT token.
6. JWT token returned to client.
7. Client sends JWT on all subsequent requests.

Authorization Header:

```text
Authorization: Bearer <jwt>
```

JWT Claims:

| Claim | Description |
|---|---|
| sub | User Id |
| role | CUSTOMER or MERCHANT |
| exp | Expiration |

---

## 5. Merchant

Merchant represents a single physical business location.

Merchant Category:

- RESTAURANT
- GROCERY
- SALON
- RETAIL
- OTHER

### Merchant Table

| Column | Type | Description |
|---|---|---|
| merchant_id | UUID | Primary Key |
| store_name | VARCHAR(100) | Store Name |
| phone_number | VARCHAR(20) | Unique |
| email | VARCHAR(100) | Optional |
| address | VARCHAR(255) | Physical Address |
| category | VARCHAR(30) | Merchant Category |
| qr_code | VARCHAR(100) | Unique |
| created_date | TIMESTAMP | Created Time |

---

## 6. Customer

### Customer Table

| Column | Type | Description |
|---|---|---|
| customer_id | UUID | Primary Key |
| phone_number | VARCHAR(20) | Unique |
| name | VARCHAR(100) | Customer Name |
| email | VARCHAR(100) | Optional |
| qr_code | VARCHAR(100) | Unique |
| created_date | TIMESTAMP | Created Time |

---

## 7. Merchant Customer Association

Business Rules:

- A Customer may associate with multiple Merchants.
- A Merchant may associate with multiple Customers.
- Association is permanent for MVP.
- Association removal is not supported.

Association Flow:

Customer scans Merchant QR.

If association does not exist:

- Create association.

If association exists:

- Create Feedback
- View Previous Feedback

### Association Table

| Column | Type | Description |
|---|---|---|
| association_id | UUID | Primary Key |
| merchant_id | UUID | FK Merchant |
| customer_id | UUID | FK Customer |
| created_date | TIMESTAMP | Created Time |

Constraint:

```text
UNIQUE(merchant_id, customer_id)
```

---

## 8. Feedback Management

A Customer may create multiple Feedback Threads with same Merchant.

Thread Status:

- OPEN
- CLOSED

### Feedback Thread Table

| Column | Type | Description |
|---|---|---|
| thread_id | UUID | Primary Key |
| merchant_id | UUID | FK Merchant |
| customer_id | UUID | FK Customer |
| subject | VARCHAR(255) | Thread Subject |
| status | VARCHAR(20) | OPEN/CLOSED |
| created_date | TIMESTAMP | Created Time |
| last_updated_date | TIMESTAMP | Last Updated |

---

## 9. Comments

Comments are:

- Text only
- Immutable
- Not editable
- Not deletable

### Comment Table

| Column | Type | Description |
|---|---|---|
| comment_id | UUID | Primary Key |
| thread_id | UUID | FK Feedback Thread |
| created_by | VARCHAR(20) | CUSTOMER/MERCHANT |
| message | TEXT | Comment |
| created_date | TIMESTAMP | Created Time |

---

## 10. Microservices

### Auth Service

- OTP generation
- OTP validation
- JWT generation
- JWT validation

### Engagement Service

- Merchant registration
- Customer registration
- Merchant Customer Association
- QR generation
- Feedback Threads
- Comments

---

## 11. Non Functional Requirements

- Microservices
- REST APIs
- Stateless Services
- HTTPS
- JWT Authentication
- OTP expiration
- Relational Database
- Horizontal Scaling
- Health Checks
- Request Logging
- Correlation IDs

---

## 12. Out Of Scope

- Merchant hierarchy
- Promotions
- Loyalty points
- Rewards
- Coupons
- Notifications
- Merchant scans Customer QR
- LLM integration
- Images
- Attachments
- Comment edit/delete
- Email login
- Social login
- Admin portal
- Analytics dashboard
- Rate limiting
- DDoS protection
