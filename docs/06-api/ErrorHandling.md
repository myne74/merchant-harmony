# Error Handling

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Purpose

This document defines the standard error handling approach for Merchant Harmony APIs.

---

## Standard Error Response

```json
{
  "errorCode": "VALIDATION_ERROR",
  "message": "Invalid request",
  "timestamp": "2026-06-28T10:00:00Z"
}
```

---

## Common Error Codes

| Code | HTTP Status | Meaning |
|------|-------------|---------|
| VALIDATION_ERROR | 400 | Request validation failed |
| UNAUTHORIZED | 401 | Missing or invalid authentication |
| FORBIDDEN | 403 | Authenticated but not allowed |
| NOT_FOUND | 404 | Resource not found |
| DUPLICATE_RESOURCE | 409 | Resource already exists |
| BUSINESS_RULE_VIOLATION | 409 | Business rule failed |
| INVALID_OTP | 400 | OTP verification failed |
| OTP_EXPIRED | 400 | OTP expired |
| THREAD_CLOSED | 409 | Thread is closed |
| TOPIC_NOT_ACTIVE | 409 | Topic is inactive |

---

## Principles

- Use consistent error response format.
- Keep messages clear and safe.
- Do not expose stack traces.
- Log server-side details internally.
- Return proper HTTP status codes.
- Validation errors should identify invalid fields when practical.

---

## Implementation Notes

Use a global exception handler in Spring Boot:

```text
@RestControllerAdvice
```

Map domain exceptions to API errors consistently.

---

## Related Documents

- ApiSpecification.md
- BusinessRules.md
- SecurityChecklist.md
