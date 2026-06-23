# ApiDesign.md

# Merchant Customer Loyalty Platform - MVP API Design

Version: 1.0  
Status: Draft

---

## 1. Purpose

This document defines the REST API design for the MVP version of the Merchant Customer Loyalty Platform.

The APIs support:

- SMS OTP authentication
- JWT-based authorization
- Merchant registration
- Customer registration
- QR-based Merchant-Customer association
- Feedback thread creation
- Threaded comments
- Merchant thread closure

---

## 2. Base URLs

For local development:

```text
Auth Service:
http://localhost:8081

Engagement Service:
http://localhost:8082
```

---

## 3. Common Headers

### Public APIs

Public APIs do not require JWT.

Examples:

```http
POST /auth/otp/send
POST /auth/otp/verify
POST /merchants
POST /customers
```

---

### Secured APIs

Secured APIs require JWT.

```http
Authorization: Bearer <jwt>
Content-Type: application/json
```

---

## 4. Standard Response Format

### Success Response

```json
{
  "success": true,
  "data": {},
  "message": "Request processed successfully"
}
```

---

### Error Response

```json
{
  "success": false,
  "errorCode": "VALIDATION_ERROR",
  "message": "Invalid request",
  "timestamp": "2026-06-23T10:00:00Z"
}
```

---

## 5. Auth APIs

## 5.1 Send OTP

```http
POST /auth/otp/send
```

### Purpose

Generate and send OTP to the provided phone number.

### Authentication

Not required.

### Request

```json
{
  "phoneNumber": "+14085551234",
  "purpose": "LOGIN"
}
```

### Request Fields

| Field | Required | Description |
|---|---|---|
| phoneNumber | Yes | Customer or Merchant phone number |
| purpose | Yes | LOGIN or REGISTER |

### Response

```json
{
  "success": true,
  "data": {
    "otpSent": true
  },
  "message": "OTP sent successfully"
}
```

---

## 5.2 Verify OTP

```http
POST /auth/otp/verify
```

### Purpose

Validate OTP and return JWT token.

### Authentication

Not required.

### Request

```json
{
  "phoneNumber": "+14085551234",
  "otp": "123456",
  "role": "CUSTOMER"
}
```

### Request Fields

| Field | Required | Description |
|---|---|---|
| phoneNumber | Yes | User phone number |
| otp | Yes | OTP entered by user |
| role | Yes | CUSTOMER or MERCHANT |

### Response

```json
{
  "success": true,
  "data": {
    "token": "<jwt>",
    "tokenType": "Bearer",
    "expiresIn": 3600
  },
  "message": "OTP verified successfully"
}
```

### Error Cases

| Error Code | Description |
|---|---|
| INVALID_OTP | OTP is incorrect |
| OTP_EXPIRED | OTP has expired |
| VALIDATION_ERROR | Missing or invalid fields |

---

## 6. Merchant APIs

## 6.1 Register Merchant

```http
POST /merchants
```

### Purpose

Register a new merchant/store.

### Authentication

Not required for MVP.

### Request

```json
{
  "storeName": "ABC Pizza Fremont",
  "phoneNumber": "+14085550001",
  "email": "store@example.com",
  "address": "123 Main St, Fremont, CA",
  "category": "RESTAURANT"
}
```

### Request Fields

| Field | Required | Description |
|---|---|---|
| storeName | Yes | Merchant store name |
| phoneNumber | Yes | Merchant phone number |
| email | No | Merchant email |
| address | Yes | Store address |
| category | Yes | RESTAURANT, GROCERY, SALON, RETAIL, OTHER |

### Response

```json
{
  "success": true,
  "data": {
    "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
    "storeName": "ABC Pizza Fremont",
    "qrCode": "MERCHANT-8a0f7f6e"
  },
  "message": "Merchant registered successfully"
}
```

### Error Cases

| Error Code | Description |
|---|---|
| PHONE_ALREADY_EXISTS | Merchant phone number already registered |
| VALIDATION_ERROR | Required fields are missing or invalid |

---

## 6.2 Get Merchant Profile

```http
GET /merchants/me
```

### Purpose

Return logged-in merchant profile.

### Authentication

Merchant JWT required.

### Response

```json
{
  "success": true,
  "data": {
    "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
    "storeName": "ABC Pizza Fremont",
    "phoneNumber": "+14085550001",
    "email": "store@example.com",
    "address": "123 Main St, Fremont, CA",
    "category": "RESTAURANT",
    "qrCode": "MERCHANT-8a0f7f6e"
  },
  "message": "Merchant profile retrieved successfully"
}
```

---

## 6.3 Get Merchant Customers

```http
GET /merchants/me/customers
```

### Purpose

Return customers associated with logged-in merchant.

### Authentication

Merchant JWT required.

### Response

```json
{
  "success": true,
  "data": [
    {
      "customerId": "c1111111-2222-3333-4444-555555555555",
      "name": "John Doe",
      "phoneNumber": "+14085551234",
      "email": "john@example.com",
      "associatedDate": "2026-06-23T10:00:00Z"
    }
  ],
  "message": "Customers retrieved successfully"
}
```

---

## 7. Customer APIs

## 7.1 Register Customer

```http
POST /customers
```

### Purpose

Register a new customer.

### Authentication

Not required for MVP.

### Request

```json
{
  "name": "John Doe",
  "phoneNumber": "+14085551234",
  "email": "john@example.com"
}
```

### Request Fields

| Field | Required | Description |
|---|---|---|
| name | Yes | Customer name |
| phoneNumber | Yes | Customer phone number |
| email | No | Customer email |

### Response

```json
{
  "success": true,
  "data": {
    "customerId": "c1111111-2222-3333-4444-555555555555",
    "name": "John Doe",
    "qrCode": "CUSTOMER-c1111111"
  },
  "message": "Customer registered successfully"
}
```

### Error Cases

| Error Code | Description |
|---|---|
| PHONE_ALREADY_EXISTS | Customer phone number already registered |
| VALIDATION_ERROR | Required fields are missing or invalid |

---

## 7.2 Get Customer Profile

```http
GET /customers/me
```

### Purpose

Return logged-in customer profile.

### Authentication

Customer JWT required.

### Response

```json
{
  "success": true,
  "data": {
    "customerId": "c1111111-2222-3333-4444-555555555555",
    "name": "John Doe",
    "phoneNumber": "+14085551234",
    "email": "john@example.com",
    "qrCode": "CUSTOMER-c1111111"
  },
  "message": "Customer profile retrieved successfully"
}
```

---

## 7.3 Get Customer Associated Merchants

```http
GET /customers/me/merchants
```

### Purpose

Return merchants associated with logged-in customer.

### Authentication

Customer JWT required.

### Response

```json
{
  "success": true,
  "data": [
    {
      "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
      "storeName": "ABC Pizza Fremont",
      "category": "RESTAURANT",
      "address": "123 Main St, Fremont, CA",
      "associatedDate": "2026-06-23T10:00:00Z"
    }
  ],
  "message": "Associated merchants retrieved successfully"
}
```

---

## 8. Association APIs

## 8.1 Customer Scans Merchant QR

```http
POST /associations/merchant-qr
```

### Purpose

Associate logged-in customer with merchant using Merchant QR code.

### Authentication

Customer JWT required.

### Request

```json
{
  "merchantQrCode": "MERCHANT-8a0f7f6e"
}
```

### Response - New Association

```json
{
  "success": true,
  "data": {
    "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
    "associated": true,
    "associationCreated": true
  },
  "message": "Association created successfully"
}
```

### Response - Existing Association

```json
{
  "success": true,
  "data": {
    "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
    "associated": true,
    "associationCreated": false
  },
  "message": "Association already exists"
}
```

### Error Cases

| Error Code | Description |
|---|---|
| MERCHANT_NOT_FOUND | QR code does not match merchant |
| UNAUTHORIZED | JWT missing or invalid |
| FORBIDDEN | User is not CUSTOMER |

---

## 9. Feedback APIs

## 9.1 Create Feedback Thread

```http
POST /feedback/threads
```

### Purpose

Create a new feedback thread for an associated merchant.

### Authentication

Customer JWT required.

### Request

```json
{
  "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
  "subject": "Issue with food order",
  "message": "The food was cold when served."
}
```

### Request Fields

| Field | Required | Description |
|---|---|---|
| merchantId | Yes | Merchant receiving feedback |
| subject | Yes | Feedback subject |
| message | Yes | Initial customer comment |

### Response

```json
{
  "success": true,
  "data": {
    "threadId": "f2222222-3333-4444-5555-666666666666",
    "status": "OPEN"
  },
  "message": "Feedback thread created successfully"
}
```

### Business Rules

- Customer must be associated with Merchant.
- Thread status starts as OPEN.
- First comment is created from the message field.

### Error Cases

| Error Code | Description |
|---|---|
| ASSOCIATION_NOT_FOUND | Customer is not associated with Merchant |
| MERCHANT_NOT_FOUND | Merchant does not exist |
| VALIDATION_ERROR | Required fields are missing |

---

## 9.2 Get Customer Feedback Threads

```http
GET /feedback/threads/customer
```

### Purpose

Return feedback threads created by logged-in customer.

### Authentication

Customer JWT required.

### Response

```json
{
  "success": true,
  "data": [
    {
      "threadId": "f2222222-3333-4444-5555-666666666666",
      "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
      "merchantName": "ABC Pizza Fremont",
      "subject": "Issue with food order",
      "status": "OPEN",
      "createdDate": "2026-06-23T10:00:00Z",
      "lastUpdatedDate": "2026-06-23T10:05:00Z"
    }
  ],
  "message": "Feedback threads retrieved successfully"
}
```

---

## 9.3 Get Merchant Feedback Threads

```http
GET /feedback/threads/merchant
```

### Purpose

Return feedback threads for logged-in merchant.

### Authentication

Merchant JWT required.

### Response

```json
{
  "success": true,
  "data": [
    {
      "threadId": "f2222222-3333-4444-5555-666666666666",
      "customerId": "c1111111-2222-3333-4444-555555555555",
      "customerName": "John Doe",
      "subject": "Issue with food order",
      "status": "OPEN",
      "createdDate": "2026-06-23T10:00:00Z",
      "lastUpdatedDate": "2026-06-23T10:05:00Z"
    }
  ],
  "message": "Feedback threads retrieved successfully"
}
```

---

## 9.4 Get Feedback Thread Details

```http
GET /feedback/threads/{threadId}
```

### Purpose

Return thread details and comments.

### Authentication

Customer or Merchant JWT required.

### Response

```json
{
  "success": true,
  "data": {
    "threadId": "f2222222-3333-4444-5555-666666666666",
    "merchantId": "8a0f7f6e-1111-4444-9999-123456789000",
    "customerId": "c1111111-2222-3333-4444-555555555555",
    "subject": "Issue with food order",
    "status": "OPEN",
    "comments": [
      {
        "commentId": "cm111111-2222-3333-4444-555555555555",
        "createdBy": "CUSTOMER",
        "message": "The food was cold when served.",
        "createdDate": "2026-06-23T10:00:00Z"
      },
      {
        "commentId": "cm222222-3333-4444-5555-666666666666",
        "createdBy": "MERCHANT",
        "message": "Thank you. We will review this with our team.",
        "createdDate": "2026-06-23T10:05:00Z"
      }
    ]
  },
  "message": "Feedback thread retrieved successfully"
}
```

### Authorization Rules

- Customer can view only own thread.
- Merchant can view only threads belonging to own store.

---

## 9.5 Add Comment

```http
POST /feedback/threads/{threadId}/comments
```

### Purpose

Add comment to an existing OPEN feedback thread.

### Authentication

Customer or Merchant JWT required.

### Request

```json
{
  "message": "Thank you for sharing. We will review this."
}
```

### Response

```json
{
  "success": true,
  "data": {
    "commentId": "cm222222-3333-4444-5555-666666666666"
  },
  "message": "Comment added successfully"
}
```

### Business Rules

- Thread must be OPEN.
- Customer can comment only on own thread.
- Merchant can comment only on own merchant thread.
- Comment is text only.
- Comment cannot be edited or deleted.

### Error Cases

| Error Code | Description |
|---|---|
| THREAD_NOT_FOUND | Thread does not exist |
| THREAD_CLOSED | Comments cannot be added to closed thread |
| FORBIDDEN | User does not have access to thread |

---

## 9.6 Close Feedback Thread

```http
PATCH /feedback/threads/{threadId}/close
```

### Purpose

Close an OPEN feedback thread.

### Authentication

Merchant JWT required.

### Response

```json
{
  "success": true,
  "data": {
    "threadId": "f2222222-3333-4444-5555-666666666666",
    "status": "CLOSED"
  },
  "message": "Feedback thread closed successfully"
}
```

### Business Rules

- Only Merchant can close thread.
- Merchant can close only own merchant thread.
- Closed thread cannot be reopened in MVP.
- Customer can create a new thread if needed.

---

## 10. Common Error Codes

| Error Code | HTTP Status | Description |
|---|---:|---|
| VALIDATION_ERROR | 400 | Invalid request |
| INVALID_OTP | 400 | OTP is incorrect |
| OTP_EXPIRED | 400 | OTP has expired |
| UNAUTHORIZED | 401 | JWT missing or invalid |
| FORBIDDEN | 403 | User does not have access |
| MERCHANT_NOT_FOUND | 404 | Merchant not found |
| CUSTOMER_NOT_FOUND | 404 | Customer not found |
| ASSOCIATION_NOT_FOUND | 404 | Association not found |
| THREAD_NOT_FOUND | 404 | Feedback thread not found |
| THREAD_CLOSED | 409 | Thread is closed |
| PHONE_ALREADY_EXISTS | 409 | Phone number already exists |

---

## 11. API Summary

| Area | Method | Endpoint | Auth Required | Role |
|---|---|---|---|---|
| Auth | POST | /auth/otp/send | No | Public |
| Auth | POST | /auth/otp/verify | No | Public |
| Merchant | POST | /merchants | No | Public |
| Merchant | GET | /merchants/me | Yes | MERCHANT |
| Merchant | GET | /merchants/me/customers | Yes | MERCHANT |
| Customer | POST | /customers | No | Public |
| Customer | GET | /customers/me | Yes | CUSTOMER |
| Customer | GET | /customers/me/merchants | Yes | CUSTOMER |
| Association | POST | /associations/merchant-qr | Yes | CUSTOMER |
| Feedback | POST | /feedback/threads | Yes | CUSTOMER |
| Feedback | GET | /feedback/threads/customer | Yes | CUSTOMER |
| Feedback | GET | /feedback/threads/merchant | Yes | MERCHANT |
| Feedback | GET | /feedback/threads/{threadId} | Yes | CUSTOMER / MERCHANT |
| Feedback | POST | /feedback/threads/{threadId}/comments | Yes | CUSTOMER / MERCHANT |
| Feedback | PATCH | /feedback/threads/{threadId}/close | Yes | MERCHANT |

---

## 12. Out of Scope APIs

The following APIs are not part of MVP:

- Merchant scans Customer QR
- Delete association
- Customer opt-out
- Notifications
- Promotions
- Loyalty points
- Rewards
- Coupons
- LLM summaries
- Admin APIs
- Comment edit
- Comment delete
- Upload attachment
- Upload image
