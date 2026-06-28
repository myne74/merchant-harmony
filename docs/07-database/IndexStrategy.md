# Index Strategy

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Draft |
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

## Recommended Indexes

### merchant

| Column | Reason |
|--------|--------|
| phone_number | Unique lookup for merchant authentication |
| qr_code | Lookup from Merchant Landing |
| category | Topic initialization and filtering |

### customer

| Column | Reason |
|--------|--------|
| phone_number | Unique lookup for customer authentication |

### merchant_customer

| Column | Reason |
|--------|--------|
| merchant_id | Find customers for merchant |
| customer_id | Find merchants for customer |
| merchant_id, customer_id | Prevent duplicate association |

### feedback_topic_master

| Column | Reason |
|--------|--------|
| merchant_category | Load default topics |
| merchant_category, display_order | Ordered topic display |

### merchant_topic

| Column | Reason |
|--------|--------|
| merchant_id | Load merchant topics |
| merchant_id, active | Merchant Landing |
| topic_id | Master topic reference |

### feedback_thread

| Column | Reason |
|--------|--------|
| merchant_id | Merchant feedback view |
| customer_id | Customer feedback view |
| merchant_topic_id | Topic reporting |
| status | Open/closed filtering |

### comment

| Column | Reason |
|--------|--------|
| thread_id | Load thread conversation |

---

## Related Documents

- DataModel.md
- ERDiagram.md
- FlywayMigrations.md
