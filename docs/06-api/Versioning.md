# API Versioning

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the API versioning approach for Merchant Harmony.

---

## Versioning Strategy

Merchant Harmony uses URI-based API versioning.

Base path:

```text
/api/v1
```

Example:

```http
GET /api/v1/customers/me
```

---

## Rationale

URI versioning is chosen because it is:

- Simple
- Explicit
- Easy to test
- Easy to document
- Easy for clients to understand

---

## Compatibility Rules

Within the same version:

- Do not remove fields.
- Do not rename fields.
- Do not change field meaning.
- Additive changes are allowed.
- New optional fields are allowed.
- New endpoints are allowed.

Breaking changes require a new API version.

---

## Future Versions

Future versions may introduce:

```text
/api/v2
```

Only when backward compatibility cannot be maintained.

---

## Related Documents

- ApiSpecification.md
- OpenApi.md
- ErrorHandling.md
