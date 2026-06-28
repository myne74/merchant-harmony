# Security Checklist

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This checklist defines the minimum security expectations for Merchant Harmony MVP implementation.

---

## Authentication

- SMS OTP required before JWT issuance.
- OTP must expire.
- OTP attempts must be limited.
- OTP values must not be logged.
- JWT must be signed.
- JWT expiration must be enforced.

---

## Authorization

- All secured APIs require JWT.
- Role checks must be enforced.
- Ownership checks must be enforced.
- Deny by default.
- Never trust user IDs from request body when JWT identity is available.

---

## API Security

- Validate request payloads.
- Return safe error messages.
- Do not expose stack traces.
- Use consistent error responses.
- Reject invalid enum values.
- Protect secured endpoints with Spring Security.

---

## Data Protection

- Treat phone numbers as sensitive data.
- Do not log JWT tokens.
- Do not log OTP codes.
- Store only required customer and merchant information.

---

## Future Security Enhancements

- Rate limiting
- DDoS protection
- Refresh token revocation
- Audit logging
- Admin access controls
- Secrets management

---

## Related Documents

- Authentication.md
- Authorization.md
- JWTDesign.md
- ThreatModel.md
