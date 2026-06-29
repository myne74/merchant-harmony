# Index Strategy

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the initial indexing strategy for Merchant Harmony MVP.

---

## Indexing Principles

- Index foreign keys used in joins.
- Index unique business identifiers.
- Index columns used frequently in filters.
- Avoid premature indexing.
- Revisit indexes after implementation and testing.

---

## Implemented Indexes

### auth_db — merchant

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_merchant_phone_number | phone_number | UNIQUE | OTP login lookup |
| idx_merchant_qr_code | qr_code | UNIQUE | Merchant Landing QR resolution |
| idx_merchant_category | category | Standard | Topic initialization at registration |
| idx_merchant_status | status | Standard | Active merchant filtering |

### auth_db — customer

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_customer_phone_number | phone_number | UNIQUE | OTP login lookup |

### auth_db — otp_request

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_otp_request_user | user_id, user_type | Standard | Fast OTP verification lookup |
| idx_otp_request_expires_at | expires_at | Standard | Expired OTP cleanup |

### engagement_db — feedback_topic_master

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_ftm_category_active | merchant_category, active | Standard | Load active topics at registration |
| idx_ftm_display_order | merchant_category, display_order | Standard | Ordered topic display |

### engagement_db — merchant_customer

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_merchant_customer_unique | merchant_id, customer_id | UNIQUE | Prevent duplicate associations |
| idx_merchant_customer_merchant | merchant_id | Standard | List customers for a merchant |
| idx_merchant_customer_customer | customer_id | Standard | List merchants for a customer |

### engagement_db — merchant_topic

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_merchant_topic_merchant | merchant_id | Standard | Load all topics for a merchant |
| idx_merchant_topic_unique | merchant_id, topic_id | UNIQUE | Prevent duplicate topic assignment |
| idx_merchant_topic_merchant_active | merchant_id, active | Standard | Merchant Landing active topics |

### engagement_db — feedback_thread

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_feedback_thread_merchant | merchant_id | Standard | Merchant feedback inbox |
| idx_feedback_thread_customer | customer_id | Standard | Customer thread history |
| idx_feedback_thread_merchant_status | merchant_id, status | Standard | Open/closed filtering for merchants |
| idx_feedback_thread_customer_status | customer_id, status | Standard | Open/closed filtering for customers |

### engagement_db — comment

| Index Name | Columns | Type | Reason |
|------------|---------|------|--------|
| idx_comment_thread | thread_id | Standard | Load all comments for a thread |
| idx_comment_thread_time | thread_id, created_at | Standard | Chronological message retrieval |

---

## Deferred Indexes

| Table | Column | Reason Deferred |
|-------|--------|-----------------|
| merchant_topic | topic_id | Not a primary query direction in MVP — merchants query by merchant_id, not by master topic |
| feedback_thread | merchant_topic_id | Topic-level reporting deferred to post-MVP analytics phase |

---

## Related Documents

- DataModel.md
- ERDiagram.md
- FlywayMigrations.md
