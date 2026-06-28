# ADR-006: QR Code Strategy

| Property | Value |
|----------|-------|
| ADR | 006 |
| Title | Merchant QR Code Strategy |
| Status | Accepted |
| Date | 2026-06-28 |
| Owner | Project Lead (Raveendra Myneni) |

---

## Context

Merchant Harmony begins every customer interaction with a QR code scan.

The QR strategy must provide a frictionless onboarding experience while remaining stable as the platform evolves.

---

## Decision

Every merchant is assigned one permanent QR Code during registration.

The QR Code represents the merchant identity only. It is not tied to a specific transaction, device, session, or customer.

```text
Merchant Registration
        │
        ▼
Generate Merchant QR Code
        │
        ▼
Store QR Identifier
        │
        ▼
Customer Scans QR
        │
        ▼
Merchant Landing
```

---

## QR Payload

The QR contains only a unique merchant identifier.

Example:

```text
merchant://{merchantId}
```

or an equivalent encoded identifier.

Business information is **not** embedded in the QR code.

---

## Rationale

Benefits:

- Small QR payload
- Permanent identifier
- Easy regeneration of QR images
- Stable customer entry point
- No sensitive information exposed

---

## Alternatives Considered

### Embed Merchant Details

Pros

- Fewer lookups

Cons

- QR becomes stale when business details change
- Larger payload
- Information duplication

Decision: Rejected.

---

### Dynamic Transaction QR

Pros

- Supports payments and transaction-specific workflows

Cons

- Higher operational complexity
- Not required for the MVP

Decision: Deferred.

---

## Security Considerations

- Merchant identity is validated after QR scan.
- Authorization is enforced using Customer JWT.
- QR codes do not grant access by themselves.
- Invalid or inactive merchants are rejected.
- QR payload contains no confidential information.

---

## Consequences

Positive:

- Simple onboarding
- Stable identifiers
- Future compatible with loyalty and promotions
- Easy to print and distribute

Trade-offs:

- Merchant details require a backend lookup.
- Merchant Landing becomes the primary onboarding endpoint.

These trade-offs are acceptable because they improve maintainability and user experience.

---

## Future Evolution

The QR framework can later support:

- Table-specific restaurant QR codes
- Branch-specific QR codes
- Campaign QR codes
- Promotional QR codes
- Event QR codes
- Loyalty check-in
- Payments

The permanent Merchant QR Code remains the canonical entry point.

---

## Related Documents

- ADR-004-MerchantLanding.md
- Architecture.md
- ApiSpecification.md
- BusinessRules.md
- DecisionLog.md
