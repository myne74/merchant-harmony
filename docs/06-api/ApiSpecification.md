# API Specification

| Property | Value |
|----------|-------|
| Version | 1.6 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document defines the human-readable REST API contract for the Merchant Harmony MVP.

It guides implementation, testing, and future OpenAPI/Swagger documentation.

---

## API Principles

- RESTful API design
- JSON request and response bodies
- Versioned API paths
- JWT required for secured APIs
- Role-specific authentication endpoints
- Ownership validation on secured resources
- Feedback topics are derived from active merchant category master data
- Every feedback thread belongs to exactly one active merchant feedback topic
- Merchant Landing API should make the first customer interaction simple

Base path:

```text
/api/v1
```

---

## Standard Error Response

```json
{
  "errorCode": "VALIDATION_ERROR",
  "message": "Invalid request",
  "timestamp": "2026-06-27T10:00:00Z"
}
```

Common error codes:

- VALIDATION_ERROR
- UNAUTHORIZED
- FORBIDDEN
- NOT_FOUND
- DUPLICATE_RESOURCE
- INVALID_OTP
- OTP_EXPIRED
- THREAD_CLOSED
- TOPIC_NOT_ACTIVE
- BUSINESS_RULE_VIOLATION

---

# Authentication APIs

Merchant and Customer authentication use separate endpoints.

Phone number is unique within each user type, but the same phone number may exist once as a Merchant and once as a Customer.

---

## Register Merchant

```http
POST /api/v1/auth/merchants/register
```

Authentication: Public

Request:

```json
{
  "businessName": "ABC Pizza",
  "displayName": "ABC Pizza Fremont",
  "phoneNumber": "+14085550001",
  "email": "owner@example.com",
  "website": "https://abcpizza.example.com",
  "category": "RESTAURANT",
  "addressLine1": "123 Main Street",
  "addressLine2": "Suite 100",
  "city": "Fremont",
  "stateProvince": "CA",
  "postalCode": "94538",
  "country": "US"
}
```

Response:

```json
{
  "merchantId": "uuid",
  "businessName": "ABC Pizza",
  "status": "ACTIVE",
  "qrCode": "merchant-qr-code"
}
```

Rules:

- Merchant phone number must be unique within merchants.
- Same phone number may also exist as a customer.
- Merchant QR Code is generated during registration.
- Merchant starts with ACTIVE status.
- Active feedback topics for the merchant category are enabled during registration.
- Merchant should be operational immediately after registration.
- Merchant must have at least one active feedback topic.

---

## Merchant Login

```http
POST /api/v1/auth/merchants/login
```

Authentication: Public

Request:

```json
{
  "phoneNumber": "+14085550001"
}
```

Response:

```json
{
  "otpRequestId": "uuid",
  "message": "OTP sent successfully"
}
```

Rules:

- Phone number must belong to a registered merchant.
- OTP request is created with userType = MERCHANT.
- OTP value should not be returned in production responses.

---

## Verify Merchant OTP

```http
POST /api/v1/auth/merchants/verify-otp
```

Authentication: Public

Request:

```json
{
  "otpRequestId": "uuid",
  "otp": "123456"
}
```

Response:

```json
{
  "accessToken": "jwt-token",
  "tokenType": "Bearer"
}
```

Rules:

- OTP request must belong to userType = MERCHANT.
- OTP must be valid and not expired.
- JWT contains merchant identity and role = MERCHANT.

---

## Register Customer

```http
POST /api/v1/auth/customers/register
```

Authentication: Public

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
  "name": "John Doe"
}
```

Rules:

- Customer phone number must be unique within customers.
- Same phone number may also exist as a merchant.
- Customer QR Code is not part of the current product.

---

## Customer Login

```http
POST /api/v1/auth/customers/login
```

Authentication: Public

Request:

```json
{
  "phoneNumber": "+14085551234"
}
```

Response:

```json
{
  "otpRequestId": "uuid",
  "message": "OTP sent successfully"
}
```

Rules:

- Phone number must belong to a registered customer.
- OTP request is created with userType = CUSTOMER.
- OTP value should not be returned in production responses.

---

## Verify Customer OTP

```http
POST /api/v1/auth/customers/verify-otp
```

Authentication: Public

Request:

```json
{
  "otpRequestId": "uuid",
  "otp": "123456"
}
```

Response:

```json
{
  "accessToken": "jwt-token",
  "tokenType": "Bearer"
}
```

Rules:

- OTP request must belong to userType = CUSTOMER.
- OTP must be valid and not expired.
- JWT contains customer identity and role = CUSTOMER.

---

# Merchant APIs

## Get Merchant Profile

```http
GET /api/v1/merchants/me
```

Authentication: Merchant JWT

Response:

```json
{
  "merchantId": "uuid",
  "businessName": "ABC Pizza",
  "displayName": "ABC Pizza Fremont",
  "phoneNumber": "+14085550001",
  "category": "RESTAURANT",
  "status": "ACTIVE"
}
```

Rules:

- Merchant can access only its own profile.

---

## Get Merchant Customers

```http
GET /api/v1/merchants/me/customers
```

Authentication: Merchant JWT

Response:

```json
[
  {
    "customerId": "uuid",
    "name": "John Doe",
    "phoneNumber": "+14085551234",
    "associatedAt": "2026-06-27T10:00:00Z"
  }
]
```

Rules:

- Merchant can view only customers associated with its own merchant account.

---

# Feedback Topic APIs

Feedback topics are master data by merchant category.

When a merchant registers, the system enables all active master topics for that merchant based on the selected category. This keeps merchant onboarding simple and avoids redundant topic creation across similar merchants.

Each merchant must always have at least one active feedback topic.

---

## Get Master Feedback Topics by Category

```http
GET /api/v1/feedback-topic-master?category=RESTAURANT
```

Authentication: Public or Merchant JWT

Response:

```json
[
  {
    "topicId": "uuid",
    "category": "RESTAURANT",
    "name": "Food",
    "type": "PRODUCT_EXPERIENCE",
    "displayOrder": 1,
    "active": true
  },
  {
    "topicId": "uuid",
    "category": "RESTAURANT",
    "name": "Billing",
    "type": "PAYMENT",
    "displayOrder": 3,
    "active": true
  }
]
```

Rules:

- Returns active master topics for the selected merchant category.
- Topics are returned by displayOrder.
- Used by the system during merchant registration.
- `defaultEnabled` is not required because all active master topics are enabled during registration.

---

## Get Merchant Feedback Topics

```http
GET /api/v1/merchants/me/feedback-topics
```

Authentication: Merchant JWT

Response:

```json
[
  {
    "merchantTopicId": "uuid",
    "topicId": "uuid",
    "name": "Food",
    "type": "PRODUCT_EXPERIENCE",
    "displayOrder": 1,
    "active": true
  },
  {
    "merchantTopicId": "uuid",
    "topicId": "uuid",
    "name": "Billing",
    "type": "PAYMENT",
    "displayOrder": 3,
    "active": true
  }
]
```

Rules:

- Merchant can view only its own feedback topics.
- Topics are initially created from active category master data.
- Topics are returned by displayOrder.

---

## Update Merchant Feedback Topic Status

```http
PATCH /api/v1/merchants/me/feedback-topics/{merchantTopicId}
```

Authentication: Merchant JWT

Request:

```json
{
  "active": false
}
```

Response:

```json
{
  "merchantTopicId": "uuid",
  "active": false
}
```

Rules:

- Merchant can enable or disable only its own feedback topics.
- Disabled topics are not shown to customers.
- Existing feedback threads remain unchanged.
- Update is rejected if it would leave the merchant with zero active feedback topics.

---

# Customer APIs

## Get Customer Profile

```http
GET /api/v1/customers/me
```

Authentication: Customer JWT

Response:

```json
{
  "customerId": "uuid",
  "name": "John Doe",
  "phoneNumber": "+14085551234",
  "email": "john@example.com"
}
```

Rules:

- Customer can access only their own profile.

---

## Merchant Landing

```http
POST /api/v1/customers/me/merchant-landing
```

Authentication: Customer JWT

Request:

```json
{
  "merchantQrCode": "merchant-qr-code"
}
```

Response:

```json
{
  "merchant": {
    "merchantId": "uuid",
    "businessName": "ABC Pizza",
    "displayName": "ABC Pizza Fremont",
    "category": "RESTAURANT"
  },
  "associationStatus": "ACTIVE",
  "feedbackTopics": [
    {
      "merchantTopicId": "uuid",
      "name": "Food",
      "type": "PRODUCT_EXPERIENCE",
      "displayOrder": 1
    },
    {
      "merchantTopicId": "uuid",
      "name": "Service",
      "type": "SERVICE_EXPERIENCE",
      "displayOrder": 2
    }
  ]
}
```

Rules:

- Merchant QR Code must be valid.
- Association is created if it does not exist.
- Existing association is returned if already present.
- Only active merchant topics are returned.
- Topics are returned by displayOrder.
- At least one active topic must be returned.
- This API supports the first customer interaction after scanning a Merchant QR Code.

---

## Get Associated Merchants

```http
GET /api/v1/customers/me/merchants
```

Authentication: Customer JWT

Response:

```json
[
  {
    "merchantId": "uuid",
    "businessName": "ABC Pizza",
    "displayName": "ABC Pizza Fremont",
    "category": "RESTAURANT"
  }
]
```

Rules:

- Customer can view only their associated merchants.

---

# Feedback APIs

## Create Feedback Thread

```http
POST /api/v1/feedback/threads
```

Authentication: Customer JWT

Request:

```json
{
  "merchantTopicId": "uuid",
  "message": "The food was too spicy today."
}
```

Response:

```json
{
  "threadId": "uuid",
  "status": "OPEN"
}
```

Rules:

- Customer identity is resolved from JWT.
- Merchant is resolved from merchantTopicId.
- Customer must be associated with the merchant.
- Merchant topic must belong to the resolved merchant.
- Merchant topic must be active.
- Every feedback thread must reference exactly one active merchant topic.
- Thread starts with OPEN status.
- Initial customer message creates the first comment.

---

## Get Customer Feedback Threads

```http
GET /api/v1/feedback/threads/customer
```

Authentication: Customer JWT

Rules:

- Customer can view only their own threads.

---

## Get Merchant Feedback Threads

```http
GET /api/v1/feedback/threads/merchant
```

Authentication: Merchant JWT

Rules:

- Merchant can view only threads belonging to its own merchant account.

---

## Get Feedback Thread

```http
GET /api/v1/feedback/threads/{threadId}
```

Authentication: Customer or Merchant JWT

Rules:

- Customer can access only their own thread.
- Merchant can access only threads belonging to its own merchant account.

---

## Add Comment

```http
POST /api/v1/feedback/threads/{threadId}/comments
```

Authentication: Customer or Merchant JWT

Request:

```json
{
  "message": "Thank you for the update."
}
```

Response:

```json
{
  "commentId": "uuid"
}
```

Rules:

- Thread must be OPEN.
- Customer can comment only on their own thread.
- Merchant can comment only on its own merchant thread.
- Comments are text only.

---

## Close Feedback Thread

```http
PATCH /api/v1/feedback/threads/{threadId}/close
```

Authentication: Merchant JWT

Response:

```json
{
  "threadId": "uuid",
  "status": "CLOSED"
}
```

Rules:

- Only merchant can close a thread.
- Merchant can close only its own thread.
- Closed threads are read-only.

---

## Future API Documentation

OpenAPI/Swagger documentation will be introduced during implementation using Springdoc OpenAPI.

---

## Related Documents

- ProductRequirements.md
- BusinessRules.md
- Architecture.md
- DataModel.md
- Authentication.md
