# Flyway Migrations

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the Flyway migration strategy for Merchant Harmony. Flyway is the sole authority for all schema changes across both service databases. Hibernate does not create or alter tables.

---

## Strategy

### Database per Service

Each microservice owns its own Flyway migration set. There is no shared migration path.

| Service | Database | Migration Path |
|---------|----------|----------------|
| auth-service | auth_db (port 5433) | `src/main/resources/db/migration/` |
| engagement-service | engagement_db (port 5434) | `src/main/resources/db/migration/` |

### Hibernate Integration

```properties
spring.jpa.hibernate.ddl-auto=none
```

Flyway runs first at application startup, then Hibernate maps entities to the already-existing schema. `validate` will be enabled once JPA entities are introduced in Phase 2.

### Spring Boot Auto-Configuration

When `flyway-core` is on the classpath and a DataSource is configured, Spring Boot automatically:

- Runs pending migrations at startup
- Uses `classpath:db/migration` as the default location
- Tracks migration history in the `flyway_schema_history` table

No additional `spring.flyway.*` properties are required beyond the DataSource already configured.

### Cross-Database References

`merchant_id` and `customer_id` appearing in engagement_db tables are plain UUIDs. There are no cross-database foreign key constraints because each service owns its database exclusively. Referential integrity across service boundaries is enforced by JWT validation and application-layer guards.

---

## auth-service Migrations (auth_db)

### V1 — merchant

Creates the `merchant` table. All merchants registered via the auth-service register endpoint are stored here. The QR code is generated at registration and stored permanently.

```
merchant
├── merchant_id (PK, UUID)
├── business_name, display_name, category, status
├── phone_number (unique — used for OTP login)
├── email, website
├── address_line1, address_line2, city, state_province, postal_code, country
├── qr_code (unique — permanent identifier for Merchant Landing)
├── qr_code_generated_at
└── created_at, updated_at
```

### V2 — customer

Creates the `customer` table. Customers register independently via their own phone number.

```
customer
├── customer_id (PK, UUID)
├── name
├── phone_number (unique — used for OTP login)
├── email
└── created_at, updated_at
```

### V3 — otp_request

Creates the `otp_request` table. Tracks in-flight OTP challenges for both merchants and customers.

```
otp_request
├── otp_request_id (PK, UUID)
├── user_id (UUID — merchantId or customerId)
├── user_type (MERCHANT | CUSTOMER)
├── otp_code
├── expires_at
├── verified
└── created_at
```

The `expires_at` index enables efficient cleanup of expired OTPs. `user_id + user_type` is indexed for fast lookup during OTP verification.

---

## engagement-service Migrations (engagement_db)

### V1 — feedback_topic_master

Creates the `feedback_topic_master` table. This is centrally managed master data — merchants do not create topics directly. The platform defines topics per category, and merchant topics are copied from this table at registration.

```
feedback_topic_master
├── topic_id (PK, UUID)
├── merchant_category (RESTAURANT | GROCERY | SALON | RETAIL | OTHER)
├── name (display label)
├── type (PRODUCT_EXPERIENCE | SERVICE_EXPERIENCE | PAYMENT | ENVIRONMENT | GENERAL)
├── display_order
├── active
└── created_at, updated_at
```

### V2 — merchant_customer

Creates the `merchant_customer` association table. The unique constraint on `(merchant_id, customer_id)` prevents duplicate associations. Created when a customer scans a Merchant QR Code through the Merchant Landing flow.

```
merchant_customer
├── association_id (PK, UUID)
├── merchant_id (UUID — no FK, lives in auth_db)
├── customer_id (UUID — no FK, lives in auth_db)
└── associated_at
```

### V3 — merchant_topic

Creates the `merchant_topic` table. Each merchant gets their own copy of the relevant `feedback_topic_master` topics at registration. Merchants can toggle topics active/inactive, but must always keep at least one active.

```
merchant_topic
├── merchant_topic_id (PK, UUID)
├── merchant_id (UUID — no FK, lives in auth_db)
├── topic_id (FK → feedback_topic_master)
├── active
└── created_at, updated_at
```

Unique constraint on `(merchant_id, topic_id)` prevents duplicate topic assignments.

### V4 — feedback_thread

Creates the `feedback_thread` table. One thread represents one customer–merchant conversation about one topic.

```
feedback_thread
├── thread_id (PK, UUID)
├── merchant_id (UUID — no FK, lives in auth_db)
├── customer_id (UUID — no FK, lives in auth_db)
├── merchant_topic_id (FK → merchant_topic)
├── status (OPEN | CLOSED)
├── created_at
└── closed_at (nullable, set when merchant closes the thread)
```

### V5 — comment

Creates the `comment` table. Each comment is an immutable message within a thread. No edit, no delete.

```
comment
├── comment_id (PK, UUID)
├── thread_id (FK → feedback_thread)
├── author_type (CUSTOMER | MERCHANT)
├── message (TEXT)
└── created_at
```

The `(thread_id, created_at)` compound index supports chronological message retrieval without a full table scan.

### V6 — seed feedback_topic_master

Inserts initial master topics for all five merchant categories.

| Category | Topics |
|----------|--------|
| RESTAURANT | Food, Service, Billing, Cleanliness, General |
| SALON | Haircut, Staff, Appointment, Billing, General |
| GROCERY | Product Quality, Staff, Billing, Cleanliness, General |
| RETAIL | Product Quality, Staff, Billing, Returns, General |
| OTHER | Service, Staff, Billing, General |

Seed data runs after the table is created (V6 > V1). Re-running is safe because `gen_random_uuid()` generates new UUIDs on each run — migrations never re-run after the first successful execution.

---

## Naming Convention

```
V{version}__{snake_case_description}.sql
```

- Version is an integer: `V1`, `V2`, `V3` …
- Two underscores separate version from description
- Description is lowercase snake_case
- Each migration does one logical thing (one table, or one seed operation)

---

## Adding a New Migration

1. Create `V{N+1}__{description}.sql` in the service's `db/migration/` directory
2. Never modify an already-applied migration file — Flyway checksums each file and will refuse to start if a previously applied migration is modified
3. For data fixes, add a new versioned migration

---

## Related Documents

- DataModel.md
- Architecture.md
- Docker.md
