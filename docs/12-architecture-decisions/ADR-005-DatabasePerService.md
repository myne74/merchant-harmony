# ADR-005: Database Per Service

| Property | Value |
|----------|-------|
| ADR | 005 |
| Title | Database Per Service |
| Status | Accepted |
| Date | 2026-06-28 |
| Owner | Project Lead (Raveendra Myneni) |

---

## Context

Merchant Harmony is built as a microservice architecture. A key architectural decision is whether services should share a database or own their data independently.

The MVP contains two services:

- auth-service
- engagement-service

---

## Decision

Each microservice owns its own database.

Services never access another service's database directly. All cross-service interactions occur through published APIs.

```text
auth-service
    │
    └── auth_db

engagement-service
    │
    └── engagement_db
```

---

## Rationale

Benefits:

- Clear ownership of data
- Independent schema evolution
- Independent deployments
- Reduced coupling
- Better security boundaries
- Easier future scaling

---

## Alternatives Considered

### Shared Database

Pros

- Simpler joins
- Easier reporting

Cons

- Tight coupling
- Cross-team schema dependencies
- Difficult service extraction

Decision: Rejected.

---

### One Database, Multiple Schemas

Pros

- Better isolation than shared tables

Cons

- Still shared infrastructure
- Ownership ambiguity

Decision: Deferred.

---

## Data Ownership

### auth-service

Owns:

- Merchant authentication
- Customer authentication
- OTP requests
- JWT metadata

### engagement-service

Owns:

- Merchants
- Customers
- Merchant QR Codes
- MerchantCustomer associations
- FeedbackTopicMaster
- MerchantTopic
- FeedbackThread
- Comment

---

## Communication

Services communicate through REST APIs.

Direct SQL access across service boundaries is prohibited.

Future versions may adopt asynchronous messaging for selected workflows.

---

## Consequences

Positive:

- Strong service autonomy
- Independent deployments
- Cleaner architecture
- Easier migration to event-driven systems

Trade-offs:

- No cross-database joins
- Data duplication where appropriate
- API calls required for shared business operations

These trade-offs are acceptable and align with modern microservice practices.

---

## Future Evolution

Potential enhancements include:

- Kafka event publishing
- CQRS read models
- Event sourcing for selected domains
- Polyglot persistence where justified

---

## Related Documents

- Architecture.md
- DataModel.md
- DecisionLog.md
- ADR-001-ServiceBoundaries.md
