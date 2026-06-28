# ADR-002: Authentication Strategy

| Property | Value |
|----------|-------|
| ADR | 002 |
| Title | Authentication Strategy |
| Status | Accepted |
| Date | 2026-06-27 |
| Owner | Project Lead (Raveendra Myneni) |

---

# Context

Merchant Harmony supports two distinct user types:

- Merchant
- Customer

Although both authenticate using a phone number and SMS OTP, they represent different business roles with different permissions and APIs.

The authentication mechanism must be simple for users while remaining secure and extensible.

---

# Decision

The platform adopts:

- SMS OTP authentication
- JWT-based authorization
- Separate authentication flows for Merchants and Customers
- Role-based access control
- Stateless authentication

Merchant and Customer identities are maintained independently.

---

# Authentication Flow

```text
User enters phone number
        ↓
OTP generated
        ↓
OTP verified
        ↓
JWT issued
        ↓
JWT presented on secured APIs
        ↓
Spring Security validates JWT
```

---

# API Design

Separate endpoints are used for each user type.

Merchant

```text
POST /auth/merchants/register
POST /auth/merchants/login
POST /auth/merchants/verify-otp
```

Customer

```text
POST /auth/customers/register
POST /auth/customers/login
POST /auth/customers/verify-otp
```

---

# Why Separate Endpoints?

Using separate authentication endpoints provides:

- Clear business ownership
- Independent validation rules
- Simpler authorization
- Better API readability
- Future extensibility

The same phone number may exist:

- once as a Merchant
- once as a Customer

because these represent different business identities.

---

# Why SMS OTP?

Pros

- No password management
- Lower onboarding friction
- Better mobile experience
- Reduced credential theft risk
- Simple recovery process

Trade-offs

- SMS provider dependency
- OTP expiration management
- Requires rate limiting in future releases

---

# JWT Design

JWT contains:

- User ID
- User Type
- Role
- Issued Time
- Expiration Time

The token does not contain business data.

Business information is always retrieved from the owning service.

---

# Authorization

Role-based authorization is enforced on every secured endpoint.

Merchant JWT

Can access:

- Merchant profile
- Merchant customers
- Merchant topics
- Merchant feedback
- Merchant replies

Customer JWT

Can access:

- Customer profile
- Merchant Landing
- Merchant associations
- Customer feedback
- Customer replies

Ownership validation is always performed after authentication.

---

# Alternatives Considered

### Username / Password

Pros

- Familiar

Cons

- Password resets
- Credential storage
- Increased security burden

Decision: Rejected.

---

### Shared Login Endpoint

Pros

- Fewer APIs

Cons

- Ambiguous identity
- Additional lookup logic
- Poor readability

Decision: Rejected.

---

# Security Considerations

- OTPs are short-lived.
- JWTs have limited lifetime.
- HTTPS is required.
- JWT signature must be validated on every secured request.
- Authorization is enforced in addition to authentication.
- Future versions may support token revocation and refresh tokens.

---

# Consequences

Positive

- Excellent onboarding experience
- Clear API boundaries
- Simple client implementation
- Easy future expansion

Trade-offs

- Duplicate login endpoints
- Additional OTP infrastructure

These trade-offs are acceptable given the improved user experience and cleaner domain model.

---

# Related Documents

- Authentication.md
- ApiSpecification.md
- BusinessRules.md
- Architecture.md
- DecisionLog.md
