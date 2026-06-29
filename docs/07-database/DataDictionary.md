# Data Dictionary

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

Column-level reference for all Merchant Harmony database tables. Reflects the physical schema as implemented in Flyway migrations.

---

## auth_db

### merchant

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| merchant_id | UUID | No | gen_random_uuid() | Primary key |
| business_name | VARCHAR(255) | No | — | Legal or trade name |
| display_name | VARCHAR(255) | Yes | — | Public-facing name shown on Merchant Landing |
| phone_number | VARCHAR(20) | No | — | Used for OTP authentication. Unique. |
| email | VARCHAR(255) | Yes | — | Optional contact email |
| website | VARCHAR(500) | Yes | — | Business website URL |
| category | VARCHAR(50) | No | — | RESTAURANT, GROCERY, SALON, RETAIL, OTHER |
| status | VARCHAR(20) | No | 'ACTIVE' | ACTIVE, INACTIVE |
| address_line1 | VARCHAR(255) | No | — | Street address |
| address_line2 | VARCHAR(255) | Yes | — | Apartment / Suite / Floor |
| city | VARCHAR(100) | No | — | |
| state_province | VARCHAR(100) | No | — | |
| postal_code | VARCHAR(20) | No | — | |
| country | VARCHAR(10) | No | 'US' | ISO 3166-1 alpha-2 |
| qr_code | VARCHAR(255) | No | — | Permanent QR identifier generated at registration. Unique. |
| qr_code_generated_at | TIMESTAMPTZ | No | — | Audit timestamp for QR generation |
| created_at | TIMESTAMPTZ | No | NOW() | |
| updated_at | TIMESTAMPTZ | No | NOW() | Updated by application on every write |

**Indexes:**

| Name | Columns | Type |
|------|---------|------|
| idx_merchant_phone_number | phone_number | UNIQUE |
| idx_merchant_qr_code | qr_code | UNIQUE |
| idx_merchant_category | category | Standard |
| idx_merchant_status | status | Standard |

---

### customer

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| customer_id | UUID | No | gen_random_uuid() | Primary key |
| name | VARCHAR(255) | No | — | Display name |
| phone_number | VARCHAR(20) | No | — | Used for OTP authentication. Unique. |
| email | VARCHAR(255) | Yes | — | Optional contact email |
| created_at | TIMESTAMPTZ | No | NOW() | |
| updated_at | TIMESTAMPTZ | No | NOW() | Updated by application on every write |

**Indexes:**

| Name | Columns | Type |
|------|---------|------|
| idx_customer_phone_number | phone_number | UNIQUE |

---

### otp_request

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| otp_request_id | UUID | No | gen_random_uuid() | Primary key. Returned to client at login as otpRequestId. |
| user_id | UUID | No | — | merchantId or customerId |
| user_type | VARCHAR(20) | No | — | MERCHANT, CUSTOMER |
| otp_code | VARCHAR(6) | No | — | 6-digit numeric OTP (stored plain for MVP; hash in production) |
| expires_at | TIMESTAMPTZ | No | — | OTP is invalid after this time |
| verified | BOOLEAN | No | FALSE | Set to TRUE on successful OTP verification |
| created_at | TIMESTAMPTZ | No | NOW() | |

**Indexes:**

| Name | Columns | Type | Notes |
|------|---------|------|-------|
| idx_otp_request_user | user_id, user_type | Standard | Fast lookup during verification |
| idx_otp_request_expires_at | expires_at | Standard | Efficient cleanup of expired OTPs |

---

## engagement_db

### feedback_topic_master

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| topic_id | UUID | No | gen_random_uuid() | Primary key |
| merchant_category | VARCHAR(50) | No | — | RESTAURANT, GROCERY, SALON, RETAIL, OTHER |
| name | VARCHAR(100) | No | — | Display label (e.g. "Food", "Haircut") |
| type | VARCHAR(50) | No | — | PRODUCT_EXPERIENCE, SERVICE_EXPERIENCE, PAYMENT, ENVIRONMENT, GENERAL |
| display_order | INTEGER | No | — | Controls display sequence within a category |
| active | BOOLEAN | No | TRUE | Inactive topics are not used during merchant registration |
| created_at | TIMESTAMPTZ | No | NOW() | |
| updated_at | TIMESTAMPTZ | No | NOW() | |

**Indexes:**

| Name | Columns | Type |
|------|---------|------|
| idx_ftm_category_active | merchant_category, active | Standard |
| idx_ftm_display_order | merchant_category, display_order | Standard |

**Seed data:** 24 pre-loaded rows across 5 categories. See `V6__seed_feedback_topic_master.sql`.

---

### merchant_customer

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| association_id | UUID | No | gen_random_uuid() | Primary key |
| merchant_id | UUID | No | — | References merchant in auth_db (no FK constraint — cross-database) |
| customer_id | UUID | No | — | References customer in auth_db (no FK constraint — cross-database) |
| associated_at | TIMESTAMPTZ | No | NOW() | When the customer first scanned the merchant QR code |

**Indexes:**

| Name | Columns | Type | Notes |
|------|---------|------|-------|
| idx_merchant_customer_unique | merchant_id, customer_id | UNIQUE | Prevents duplicate associations |
| idx_merchant_customer_merchant | merchant_id | Standard | |
| idx_merchant_customer_customer | customer_id | Standard | |

---

### merchant_topic

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| merchant_topic_id | UUID | No | gen_random_uuid() | Primary key |
| merchant_id | UUID | No | — | References merchant in auth_db (no FK constraint — cross-database) |
| topic_id | UUID | No | — | FK → feedback_topic_master.topic_id |
| active | BOOLEAN | No | TRUE | Merchant can deactivate but must keep at least one active |
| created_at | TIMESTAMPTZ | No | NOW() | |
| updated_at | TIMESTAMPTZ | No | NOW() | |

**Constraints:**

| Name | Type | Definition |
|------|------|------------|
| fk_merchant_topic_topic | FK | topic_id → feedback_topic_master(topic_id) |

**Indexes:**

| Name | Columns | Type |
|------|---------|------|
| idx_merchant_topic_merchant | merchant_id | Standard |
| idx_merchant_topic_unique | merchant_id, topic_id | UNIQUE |
| idx_merchant_topic_merchant_active | merchant_id, active | Standard |

---

### feedback_thread

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| thread_id | UUID | No | gen_random_uuid() | Primary key |
| merchant_id | UUID | No | — | References merchant in auth_db (no FK constraint — cross-database) |
| customer_id | UUID | No | — | References customer in auth_db (no FK constraint — cross-database) |
| merchant_topic_id | UUID | No | — | FK → merchant_topic.merchant_topic_id |
| status | VARCHAR(20) | No | 'OPEN' | OPEN, CLOSED |
| created_at | TIMESTAMPTZ | No | NOW() | |
| closed_at | TIMESTAMPTZ | Yes | — | Set when merchant closes the thread |

**Constraints:**

| Name | Type | Definition |
|------|------|------------|
| fk_feedback_thread_topic | FK | merchant_topic_id → merchant_topic(merchant_topic_id) |

**Indexes:**

| Name | Columns | Type |
|------|---------|------|
| idx_feedback_thread_merchant | merchant_id | Standard |
| idx_feedback_thread_customer | customer_id | Standard |
| idx_feedback_thread_merchant_status | merchant_id, status | Standard |
| idx_feedback_thread_customer_status | customer_id, status | Standard |

---

### comment

| Column | Type | Nullable | Default | Notes |
|--------|------|----------|---------|-------|
| comment_id | UUID | No | gen_random_uuid() | Primary key |
| thread_id | UUID | No | — | FK → feedback_thread.thread_id |
| author_type | VARCHAR(20) | No | — | CUSTOMER, MERCHANT |
| message | TEXT | No | — | Comment body. No edit, no delete. |
| created_at | TIMESTAMPTZ | No | NOW() | Immutable |

**Constraints:**

| Name | Type | Definition |
|------|------|------------|
| fk_comment_thread | FK | thread_id → feedback_thread(thread_id) |

**Indexes:**

| Name | Columns | Type | Notes |
|------|---------|------|-------|
| idx_comment_thread | thread_id | Standard | |
| idx_comment_thread_time | thread_id, created_at | Standard | Chronological message retrieval |

---

## Notes

- All primary keys use `gen_random_uuid()` — available natively in PostgreSQL 13+
- `TIMESTAMPTZ` (TIMESTAMP WITH TIME ZONE) is used for all timestamps — stores in UTC, displays in session timezone
- Cross-database references (merchant_id, customer_id in engagement_db) carry no FK constraints — referential integrity is enforced by JWT validation and application-layer guards
- `updated_at` is maintained by the application layer via JPA `@PreUpdate`

---

## Related Documents

- DataModel.md
- FlywayMigrations.md
- ERDiagram.md
