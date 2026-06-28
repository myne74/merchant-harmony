# Business Rules

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document defines the core business rules for Merchant Harmony.

These rules guide validation, service logic, API behavior, and test cases.

---

## Merchant Rules

- A merchant represents one physical store or business location.
- A merchant must register before using the platform.
- Merchant phone number must be unique.
- Merchant category is required.
- A merchant is assigned one permanent Merchant QR Code.
- A merchant can have many associated customers.
- A merchant can view only its own customers and feedback threads.
- A merchant can reply only to feedback threads belonging to its own store.
- A merchant can close only its own feedback threads.

Supported merchant categories:

- RESTAURANT
- GROCERY
- SALON
- RETAIL
- OTHER

---

## Customer Rules

- A customer must register before submitting feedback.
- Customer phone number must be unique.
- Customer name is required.
- Customer email is optional.
- A customer can associate with many merchants.
- A customer can create feedback only for associated merchants.
- A customer can view only their own feedback threads.
- A customer can reply only to their own feedback threads.
- A customer cannot close a feedback thread.

---

## Authentication Rules

- Authentication is performed using SMS OTP.
- OTP verification is required before JWT is issued.
- JWT is required for secured APIs.
- JWT must identify the authenticated user and role.
- Supported roles are CUSTOMER and MERCHANT.
- Public APIs include registration and OTP APIs.
- All profile, association, feedback, and comment APIs require JWT.

---

## Merchant-Customer Association Rules

- Association is initiated when a customer scans a Merchant QR Code.
- If the association does not exist, the system creates it.
- If the association already exists, the system returns the existing relationship.
- Duplicate associations are not allowed.
- Association is permanent in the current product.
- Association removal is not supported in the current product.

---

## QR Code Rules

- Merchant QR Code uniquely identifies a merchant.
- Merchant QR Code is generated during merchant registration.
- Merchant QR Code is permanent in the current product.
- QR Code image storage is not required.
- The system stores the QR identifier and can generate/display a QR image from it.

Customer QR Code is not part of the current product and is maintained in FutureRequirements.md.

---

## Feedback Thread Rules

- A customer can create multiple feedback threads for the same merchant.
- A feedback thread belongs to exactly one merchant.
- A feedback thread belongs to exactly one customer.
- A feedback thread starts with status OPEN.
- Only the merchant can close a feedback thread.
- A CLOSED thread is read-only.
- Closed threads cannot be reopened in the current product.
- Customers may create a new feedback thread for future interactions.

Supported thread statuses:

- OPEN
- CLOSED

---

## Comment Rules

- Comments belong to a feedback thread.
- Comments are text only.
- Comments may be created by a CUSTOMER or MERCHANT.
- Comments cannot be added to CLOSED threads.
- Comments cannot be edited in the current product.
- Comments cannot be deleted in the current product.
- Images and file attachments are not supported in the current product.

---

## Authorization Rules

- Customers can access only their own profile, associations, feedback threads, and comments.
- Merchants can access only their own profile, associated customers, feedback threads, and comments.
- Access must be validated on every secured API.
- Role-based authorization must be enforced consistently across services.

---

## Related Documents

- ProductRequirements.md
- FutureRequirements.md
- ApiSpecification.md
- DataModel.md
- Authentication.md
