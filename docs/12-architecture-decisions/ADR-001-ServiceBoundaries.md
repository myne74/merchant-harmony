# ADR-001: Service Boundaries

| Property | Value |
|----------|-------|
| ADR | 001 |
| Title | Service Boundaries |
| Status | Accepted |
| Date | 2026-06-27 |
| Owner | Project Lead (Raveendra Myneni) |

---

# Context

Merchant Harmony is designed as a modern cloud-native application. The MVP intentionally limits the number of services while maintaining clear ownership boundaries to support future growth.

The platform currently supports:

- Merchant registration
- Customer registration
- OTP authentication
- Merchant QR onboarding
- Merchant-Customer association
- Feedback conversations

---

# Decision

The MVP will consist of **two microservices**:

## auth-service

Owns:

- Merchant authentication
- Customer authentication
- OTP generation and verification
- JWT generation
- Authentication APIs

Owns its authentication database.

---

## engagement-service

Owns:

- Merchant profiles
- Customer profiles
- Merchant QR Codes
- Merchant-Customer associations
- Feedback Topic Master
- Merchant Topics
- Merchant Landing
- Feedback Threads
- Comments

Owns its business database.

---

# Rationale

This design provides:

- Clear ownership of business data
- Independent deployment
- Independent scaling
- Reduced coupling
- Simple operational model for the MVP

Only two services are required today, avoiding unnecessary complexity.

---

# Alternatives Considered

### Single Monolith

Pros

- Faster initial development
- Simpler deployment

Cons

- Tight coupling
- Harder future decomposition
- Larger deployment units

Decision: Rejected.

---

### Many Small Microservices

Pros

- Strong separation of concerns
- Independent scaling

Cons

- Operational complexity
- More infrastructure
- Higher development overhead

Decision: Deferred until platform growth justifies additional services.

---

# Consequences

Positive:

- Easy to understand architecture
- Independent ownership
- Future-ready decomposition

Trade-offs:

- REST communication between services
- Duplicate audit metadata across services
- Future event-driven integration may be introduced

---

# Future Evolution

Potential future services include:

- Notification Service
- Loyalty Service
- Promotions Service
- Analytics Service
- AI Insights Service

These can be extracted without changing existing service responsibilities.

---

# Related Documents

- Architecture.md
- DecisionLog.md
- ProductRequirements.md
- ApiSpecification.md
