# ER Diagram

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |


---

## Purpose

This document provides a high-level entity relationship view for the Merchant Harmony MVP.

---

## Entity Relationship View

```text
feedback_topic_master
        │ 1:N
        ▼
merchant_topic
        │ N:1
        ▼
merchant
        │
        ├── 1:N merchant_customer N:1 ── customer
        │
        └── 1:N feedback_thread N:1 ─── customer
                    │
                    └── 1:N comment
```

---

## Core Relationships

| Relationship | Cardinality |
|-------------|-------------|
| Merchant → MerchantCustomer | 1:N |
| Customer → MerchantCustomer | 1:N |
| Merchant → MerchantTopic | 1:N |
| FeedbackTopicMaster → MerchantTopic | 1:N |
| Merchant → FeedbackThread | 1:N |
| Customer → FeedbackThread | 1:N |
| MerchantTopic → FeedbackThread | 1:N |
| FeedbackThread → Comment | 1:N |

---

## Database Assignment

The entities above are split across two databases:

| Database | Entities |
|----------|----------|
| auth_db | merchant, customer, otp_request |
| engagement_db | feedback_topic_master, merchant_customer, merchant_topic, feedback_thread, comment |

Cross-database references (merchant_id, customer_id appearing in engagement_db) carry no FK constraints. Referential integrity is enforced by JWT validation and application-layer guards.

## Notes

This is a logical ER view. Physical column definitions are in DataDictionary.md. Flyway migrations are in each service's `src/main/resources/db/migration/`.

---

## Related Documents

- DataModel.md
- IndexStrategy.md
- FlywayMigrations.md
