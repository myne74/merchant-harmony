# Test Strategy

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the testing approach for Merchant Harmony MVP.

---

## Testing Goals

- Validate core business rules.
- Validate authentication and authorization.
- Validate API contracts.
- Validate data persistence.
- Prevent regression during feature development.

---

## Test Levels

### Unit Tests

Focus:

- Service logic
- Validation rules
- Utility classes
- Business rule enforcement

### Integration Tests

Focus:

- REST APIs
- Database persistence
- Repository behavior
- Security filters

### Acceptance Tests

Focus:

- Merchant registration
- Customer registration
- OTP login
- Merchant Landing
- Feedback thread creation
- Comment flow
- Thread closure

---

## High-Priority Test Scenarios

- Merchant phone unique within merchants.
- Customer phone unique within customers.
- Same phone allowed across merchant and customer.
- Merchant gets default topics during registration.
- Merchant cannot disable last active topic.
- Customer scan creates association.
- Existing association is reused.
- Feedback requires active merchant topic.
- Closed thread rejects new comments.
- Customer cannot close thread.
- Merchant can close own thread only.

---

## Tools

Planned tools:

- JUnit 5
- Mockito
- Spring Boot Test
- Testcontainers
- PostgreSQL test container

---

## Related Documents

- BusinessRules.md
- ApiSpecification.md
- DataModel.md
- SecurityChecklist.md
