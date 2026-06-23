# SystemDesign.md

# Merchant Customer Loyalty Platform - MVP System Design

Version: 1.0  
Status: Draft

---

## 1. Purpose

This document describes the MVP system design for the Merchant Customer Loyalty Platform.

The MVP supports:

- Merchant registration
- Customer registration
- SMS OTP authentication
- JWT-based authorization
- QR-based Merchant-Customer association
- Feedback threads
- Text comments
- Merchant response and thread closure

---

## 2. High-Level Architecture

The MVP will use two backend services:

```text
Auth Service
Engagement Service
```

For MVP, the system is intentionally kept small to avoid over-engineering while still demonstrating microservice architecture.

---

## 3. Services

### 3.1 Auth Service

The Auth Service is responsible for authentication and token management.

Responsibilities:

- Generate OTP
- Validate OTP
- Issue JWT tokens
- Support CUSTOMER and MERCHANT roles
- Validate basic authentication-related rules

---

### 3.2 Engagement Service

The Engagement Service is responsible for the core business capabilities.

Responsibilities:

- Merchant registration
- Customer registration
- Merchant QR generation
- Customer QR generation
- Merchant-Customer association
- Feedback thread management
- Comment management

---

## 4. Architecture Diagram

```text
Client / Postman
      |
      v
API Gateway / Load Balancer
      |
      +--------------------+
      |                    |
      v                    v
Auth Service          Engagement Service
      |                    |
      v                    v
Auth DB              Engagement DB
```

For local MVP development, the API Gateway may be skipped and requests may directly hit the services.

---

## 5. Authentication Design

### 5.1 Login Flow

```text
1. User submits phone number
2. Auth Service generates OTP
3. OTP is sent through SMS provider or mocked SMS component
4. User submits OTP
5. Auth Service validates OTP
6. Auth Service generates JWT
7. Client uses JWT for secured APIs
```

### 5.2 JWT Usage

All secured APIs require the following header:

```http
Authorization: Bearer <jwt>
```

### 5.3 JWT Claims

```json
{
  "sub": "user-id",
  "role": "CUSTOMER",
  "exp": 1710000000
}
```

Supported roles:

```text
CUSTOMER
MERCHANT
```

---

## 6. Database Design

### 6.1 Auth DB

#### otp_verification

| Column | Type | Notes |
|---|---|---|
| otp_id | UUID | Primary Key |
| phone_number | VARCHAR(20) | User phone number |
| otp_code | VARCHAR(10) | OTP value |
| purpose | VARCHAR(30) | LOGIN / REGISTER |
| expires_at | TIMESTAMP | OTP expiry time |
| verified | BOOLEAN | Verification status |
| created_date | TIMESTAMP | Created time |

### 6.2 Engagement DB

#### merchant

| Column | Type | Notes |
|---|---|---|
| merchant_id | UUID | Primary Key |
| store_name | VARCHAR(100) | Store name |
| phone_number | VARCHAR(20) | Unique |
| email | VARCHAR(100) | Optional |
| address | VARCHAR(255) | Store address |
| category | VARCHAR(30) | RESTAURANT / GROCERY / SALON / RETAIL / OTHER |
| qr_code | VARCHAR(100) | Unique QR identifier |
| created_date | TIMESTAMP | Created time |

#### customer

| Column | Type | Notes |
|---|---|---|
| customer_id | UUID | Primary Key |
| phone_number | VARCHAR(20) | Unique |
| name | VARCHAR(100) | Customer name |
| email | VARCHAR(100) | Optional |
| qr_code | VARCHAR(100) | Unique QR identifier |
| created_date | TIMESTAMP | Created time |

#### merchant_customer_association

| Column | Type | Notes |
|---|---|---|
| association_id | UUID | Primary Key |
| merchant_id | UUID | FK merchant |
| customer_id | UUID | FK customer |
| created_date | TIMESTAMP | Association created time |

Constraint:

```text
UNIQUE(merchant_id, customer_id)
```

Purpose:

Prevents duplicate Merchant-Customer associations.

#### feedback_thread

| Column | Type | Notes |
|---|---|---|
| thread_id | UUID | Primary Key |
| merchant_id | UUID | FK merchant |
| customer_id | UUID | FK customer |
| subject | VARCHAR(255) | Feedback subject |
| status | VARCHAR(20) | OPEN / CLOSED |
| created_date | TIMESTAMP | Created time |
| last_updated_date | TIMESTAMP | Last updated time |

#### comment

| Column | Type | Notes |
|---|---|---|
| comment_id | UUID | Primary Key |
| thread_id | UUID | FK feedback_thread |
| created_by | VARCHAR(20) | CUSTOMER / MERCHANT |
| message | TEXT | Comment text |
| created_date | TIMESTAMP | Created time |

---

## 7. API Design

### 7.1 Auth APIs

#### Send OTP

```http
POST /auth/otp/send
```

Request:

```json
{
  "phoneNumber": "+14085551234",
  "purpose": "LOGIN"
}
```

Response:

```json
{
  "message": "OTP sent"
}
```

#### Verify OTP

```http
POST /auth/otp/verify
```

Request:

```json
{
  "phoneNumber": "+14085551234",
  "otp": "123456",
  "role": "CUSTOMER"
}
```

Response:

```json
{
  "token": "<jwt>"
}
```

### 7.2 Merchant APIs

#### Register Merchant

```http
POST /merchants
```

Request:

```json
{
  "storeName": "ABC Pizza Fremont",
  "phoneNumber": "+14085550001",
  "email": "store@example.com",
  "address": "123 Main St, Fremont, CA",
  "category": "RESTAURANT"
}
```

Response:

```json
{
  "merchantId": "uuid",
  "qrCode": "MERCHANT-QR-CODE"
}
```

#### Get Merchant Profile

```http
GET /merchants/me
```

Requires Merchant JWT.

#### Get Merchant Customers

```http
GET /merchants/me/customers
```

Requires Merchant JWT.

### 7.3 Customer APIs

#### Register Customer

```http
POST /customers
```

Request:

```json
{
  "name": "John Doe",
  "phoneNumber": "+14085551234",
  "email": "john@example.com"
}
```

Response:

```json
{
  "customerId": "uuid",
  "qrCode": "CUSTOMER-QR-CODE"
}
```

#### Get Customer Profile

```http
GET /customers/me
```

Requires Customer JWT.

### 7.4 QR Association APIs

#### Customer Scans Merchant QR

```http
POST /associations/merchant-qr
```

Requires Customer JWT.

Request:

```json
{
  "merchantQrCode": "MERCHANT-QR-CODE"
}
```

Response when association is created:

```json
{
  "merchantId": "uuid",
  "associated": true,
  "message": "Association created"
}
```

Response when association already exists:

```json
{
  "merchantId": "uuid",
  "associated": true,
  "message": "Association already exists"
}
```

### 7.5 Feedback APIs

#### Create Feedback Thread

```http
POST /feedback/threads
```

Requires Customer JWT.

Request:

```json
{
  "merchantId": "uuid",
  "subject": "Issue with food order",
  "message": "The food was cold when served."
}
```

Response:

```json
{
  "threadId": "uuid",
  "status": "OPEN"
}
```

System behavior:

- Creates feedback thread
- Creates first comment using request message
- Sets thread status to OPEN

#### Get Customer Feedback Threads

```http
GET /feedback/threads/customer
```

Requires Customer JWT.

#### Get Merchant Feedback Threads

```http
GET /feedback/threads/merchant
```

Requires Merchant JWT.

#### Get Thread Details

```http
GET /feedback/threads/{threadId}
```

Authorization:

- Customer can view only own thread
- Merchant can view only own merchant thread

#### Add Comment

```http
POST /feedback/threads/{threadId}/comments
```

Requires Customer or Merchant JWT.

Request:

```json
{
  "message": "Thank you for sharing. We will review this."
}
```

Response:

```json
{
  "commentId": "uuid"
}
```

#### Close Thread

```http
PATCH /feedback/threads/{threadId}/close
```

Requires Merchant JWT.

Response:

```json
{
  "threadId": "uuid",
  "status": "CLOSED"
}
```

---

## 8. Core Flows

### 8.1 Merchant Registration Flow

```text
Merchant -> Register Merchant API
Engagement Service -> Validate request
Engagement Service -> Create merchant
Engagement Service -> Generate QR code identifier
Engagement Service -> Save merchant
Engagement Service -> Return merchantId and qrCode
```

### 8.2 Customer Registration Flow

```text
Customer -> Register Customer API
Engagement Service -> Validate request
Engagement Service -> Create customer
Engagement Service -> Generate QR code identifier
Engagement Service -> Save customer
Engagement Service -> Return customerId and qrCode
```

### 8.3 Authentication Flow

```text
User -> Send OTP
Auth Service -> Generate OTP
Auth Service -> Save OTP
Auth Service -> Send SMS or log OTP in local mode

User -> Verify OTP
Auth Service -> Validate OTP
Auth Service -> Generate JWT
Auth Service -> Return JWT
```

### 8.4 Customer Scans Merchant QR Flow

```text
Customer -> Submit Merchant QR
Engagement Service -> Find merchant by QR
Engagement Service -> Check existing association
Engagement Service -> Create association if missing
Engagement Service -> Return association status
```

### 8.5 Feedback Flow

```text
Customer -> Create feedback thread
Engagement Service -> Validate customer association with merchant
Engagement Service -> Create thread
Engagement Service -> Create initial comment
Engagement Service -> Return threadId

Merchant -> View feedback
Merchant -> Reply
Merchant -> Close thread
```

---

## 9. Authorization Rules

### 9.1 Customer

Customer can:

- View own profile
- View own associated merchants
- Create feedback only for associated merchants
- View own feedback threads
- Comment on own feedback threads

Customer cannot:

- View other customer data
- View merchant-only data
- Close feedback thread

### 9.2 Merchant

Merchant can:

- View own profile
- View own associated customers
- View feedback for own store
- Reply to feedback for own store
- Close own feedback threads

Merchant cannot:

- View other merchant data
- View unrelated customer feedback
- Create feedback as customer

---

## 10. Error Handling

Standard error response:

```json
{
  "errorCode": "RESOURCE_NOT_FOUND",
  "message": "Merchant not found",
  "timestamp": "2026-06-23T10:00:00Z"
}
```

Common error codes:

```text
INVALID_OTP
OTP_EXPIRED
UNAUTHORIZED
FORBIDDEN
MERCHANT_NOT_FOUND
CUSTOMER_NOT_FOUND
ASSOCIATION_NOT_FOUND
THREAD_NOT_FOUND
THREAD_CLOSED
VALIDATION_ERROR
```

---

## 11. Logging and Observability

Each request should log:

- correlationId
- userId if authenticated
- role
- endpoint
- response status
- execution time

Health check APIs:

```http
GET /actuator/health
```

Recommended logs:

```text
INFO  - successful business events
WARN  - validation or duplicate requests
ERROR - unexpected failures
```

---

## 12. Security Considerations

MVP security requirements:

- HTTPS
- JWT validation
- Role-based access control
- OTP expiration
- No sensitive data in logs
- Phone number treated as sensitive data
- Validate ownership on every secured API

---

## 13. Deployment View

For local MVP:

```text
Docker Compose

- auth-service
- engagement-service
- auth-db
- engagement-db
```

Optional local tools:

```text
- pgAdmin
- local SMS mock
```

---

## 14. Technology Stack Suggestion

Backend:

```text
Java 17
Spring Boot
Spring Security
Spring Data JPA
PostgreSQL
JWT
Docker
```

Testing:

```text
JUnit
Mockito
Postman
```

---

## 15. MVP Design Decisions

| Decision | Reason |
|---|---|
| Two services only | Keeps MVP manageable |
| SMS OTP + JWT | Realistic authentication model |
| Engagement service combines business domains | Avoids over-splitting too early |
| QR code stored as identifier | Avoids storing images |
| Thread status only OPEN/CLOSED | Keeps workflow simple |
| Text-only comments | Reduces storage and security complexity |
| Association permanent | Avoids unsubscribe/remove flows in MVP |

---

## 16. Out of Scope

Not included in MVP:

- Merchant hierarchy
- Merchant scans Customer QR
- Promotions
- Loyalty points
- Rewards
- Coupons
- Notifications
- LLM integration
- Admin portal
- Analytics dashboard
- Comment edit/delete
- Images
- Attachments
- Rate limiting
- DDoS protection
- Multi-region deployment
