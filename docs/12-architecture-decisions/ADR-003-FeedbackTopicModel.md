# ADR-003: Feedback Topic Model

| Property | Value |
|----------|-------|
| ADR | 003 |
| Title | Feedback Topic Model |
| Status | Accepted |
| Date | 2026-06-27 |
| Owner | Project Lead (Raveendra Myneni) |

---

## Context

Merchant Harmony needs a simple way for customers to classify feedback when interacting with a merchant.

Early options included:

- Free-text feedback category
- Merchant-created offerings
- Product/service catalog
- Optional general feedback
- Category-based feedback topics

The system needs to work across many merchant types such as restaurants, salons, grocery stores, retail stores, and others.

---

## Decision

Merchant Harmony will use a **category-based Feedback Topic model**.

The model includes:

```text
FeedbackTopicMaster
        ↓
MerchantTopic
        ↓
FeedbackThread
```

### FeedbackTopicMaster

Centrally managed master topics by merchant category.

Example:

```text
RESTAURANT
- Food
- Service
- Billing
- Cleanliness
- General
```

### MerchantTopic

Merchant-specific enabled topics created from active master topics during merchant registration.

### FeedbackThread

Every feedback thread must reference exactly one active MerchantTopic.

---

## Rationale

This approach keeps the customer and merchant experience simple.

### Benefits

- Avoids requiring merchants to create topics before becoming operational.
- Avoids duplicate topic data across similar merchants.
- Keeps feedback structured.
- Works across different merchant categories.
- Simplifies reporting and future analytics.
- Avoids large product/service catalogs in the MVP.
- Eliminates special-case "general feedback" handling.

---

## Rules

- Feedback topics are maintained as master data by merchant category.
- All active master topics for the merchant category are enabled during merchant registration.
- `defaultEnabled` is not required.
- `displayOrder` controls topic display order.
- Merchants may enable or disable their own topics.
- A merchant must always have at least one active topic.
- Every feedback thread must reference exactly one active MerchantTopic.

---

## Alternatives Considered

### Merchant-created Offerings

Rejected because it requires merchants to perform setup before becoming operational and may create redundant data.

### Product/Service Catalog

Rejected for MVP because it introduces inventory-like complexity.

### Optional General Feedback

Rejected because it creates special API and data model behavior. General should be represented as a normal topic.

### Free-text Category

Rejected because it reduces reporting quality and makes analytics difficult.

---

## Consequences

Positive:

- Simple customer experience
- Fast merchant onboarding
- Clean feedback classification
- Strong foundation for future analytics

Trade-offs:

- Merchants cannot create custom topics in MVP.
- Topic customization requires future admin/configuration features.
- Existing merchants may need migration when master topics change.

---

## Related Documents

- ProductRequirements.md
- BusinessRules.md
- DataModel.md
- ApiSpecification.md
- DecisionLog.md
