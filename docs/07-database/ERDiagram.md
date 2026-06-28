# ER Diagram

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Draft |
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

## Notes

This is a logical ER view. Physical schema details will be finalized during Flyway migration implementation.

---

## Related Documents

- DataModel.md
- IndexStrategy.md
- FlywayMigrations.md
