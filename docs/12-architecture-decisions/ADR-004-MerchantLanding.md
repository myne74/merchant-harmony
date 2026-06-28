# ADR-004: Merchant Landing

| Property | Value |
|----------|-------|
| ADR | 004 |
| Title | Merchant Landing |
| Status | Accepted |
| Date | 2026-06-28 |
| Owner | Project Lead (Raveendra Myneni) |

---

## Context

The primary customer journey begins when a customer scans a Merchant QR Code.

Several onboarding approaches were considered:

- Display only merchant information
- Immediately open a feedback screen
- Require customers to navigate multiple APIs
- Introduce a dedicated Merchant Landing experience

The objective is to minimize customer effort while keeping the platform intuitive for first-time users.

---

## Decision

Merchant Harmony introduces a dedicated **Merchant Landing** endpoint.

The Merchant Landing endpoint is the customer's primary entry point after scanning a Merchant QR Code.

It performs three responsibilities in a single request:

1. Validates the Merchant QR Code.
2. Creates the Merchant-Customer association if it does not already exist.
3. Returns all information required to begin a feedback conversation.

---

## API

POST /api/v1/customers/me/merchant-landing

Response includes:
- Merchant summary
- Association status
- Active Merchant Topics
- Feedback Topic display order

---

## Customer Journey

Customer scans Merchant QR
→ Merchant Landing
→ Validate Merchant
→ Create Association (if required)
→ Load Active Topics
→ Customer selects Topic
→ Submit Feedback

---

## Rationale

Benefits:
- Single API call
- Automatic association creation
- Reduced client complexity
- Faster onboarding
- Consistent user experience

---

## Alternatives Considered

1. Separate APIs (Rejected)
2. QR returning only Merchant ID (Rejected)

---

## Consequences

Positive:
- Excellent onboarding
- Reduced API chatter
- Cleaner client implementation

Trade-offs:
- Merchant Landing performs multiple coordinated operations.

---

## Future Evolution

May later include:
- Promotions
- Loyalty
- Recommendations
- AI Insights

---

## Related Documents

- Architecture.md
- ApiSpecification.md
- BusinessRules.md
- ProductRequirements.md
- DecisionLog.md
