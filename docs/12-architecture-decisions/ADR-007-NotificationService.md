# ADR-007: Notification Service Architecture

| Property | Value |
|----------|-------|
| ADR | 007 |
| Title | Notification Service Architecture |
| Status | Accepted |
| Date | 2026-06-29 |
| Owner | Project Lead (Raveendra Myneni) |

---

## Context

Merchant Harmony uses SMS OTP for authentication. Delivering that OTP requires a mechanism to send SMS messages.

The system has no SMS provider available for MVP. However, the architecture must support:

- Multiple SMS providers (e.g., Twilio, AWS SNS, MessageBird) that can be swapped without code changes
- Future notification channels beyond SMS (email, Slack, push notifications)
- A migration from synchronous HTTP calls to event-driven delivery via Kafka when the system matures

The notification concern must be isolated from auth-service so that provider changes, channel additions, and transport upgrades never touch authentication logic.

---

## Decision

Introduce `notification-service` as a dedicated microservice.

### Scope (MVP)

- **Consumer:** auth-service only — OTP delivery on login initiation
- **Channel:** SMS only
- **Persistence:** None — fire-and-forget; no notification history stored in MVP
- **Transport:** Synchronous REST call from auth-service

### Provider Interface Pattern

A `SmsProvider` interface is the extension point for all SMS implementations:

```java
public interface SmsProvider {
    void send(String to, String message);
}
```

The active provider is wired via Spring `@Bean` configuration, controlled by an application property (`sms.provider`). Swapping providers requires a new implementation and a config change — no caller code changes.

### MVP Implementation: LoggingSmsProvider

`LoggingSmsProvider` is the MVP implementation of `SmsProvider`:

- Logs the recipient phone number and message at INFO level
- Simulates a **10% random failure rate** using `SecureRandom` to exercise error handling in auth-service
- Throws a runtime exception on simulated failure, causing notification-service to return HTTP 500
- No external dependency; no real SMS is sent

This validates the full error-handling path before a real provider is integrated.

### Internal API

```http
POST /api/v1/notifications/sms
```

Request:
```json
{
  "to": "+14085550001",
  "message": "Your Merchant Harmony OTP is 123456. Valid for 5 minutes."
}
```

Response: `200 OK` (no body) on success, `500` on failure.

The endpoint requires **no JWT**. It is trusted at the internal Docker network level (same pattern as topic initialization in engagement-service).

### Failure Behavior

auth-service makes a **synchronous** call to notification-service before returning the OTP login response.

- If notification-service returns success → auth-service returns `LoginResponse` to the caller
- If notification-service returns an error (including the 10% simulated failure) → auth-service returns an error to the caller

**Rationale:** An OTP that was never delivered is worse than a failed login attempt. The caller should know the OTP was not sent so they can retry.

This is different from the topic initialization call (which is best-effort) because OTP delivery is a required precondition for authentication — without it, the login flow cannot proceed.

---

## Future Evolution

### Real Provider Integration

Add a `TwilioSmsProvider` (or equivalent) implementing `SmsProvider`. Configure `sms.provider=twilio` in production. No other code changes.

### Multiple Channels

Add `EmailProvider`, `SlackProvider` interfaces following the same pattern. notification-service becomes the single notification hub.

### Kafka Migration Path

When Kafka is introduced:

```
auth-service          notification-service
      │                        │
      │  publish OtpRequested  │
      │ ─────────────────────► │  (Kafka topic)
      │                        │
      │                        │ → SmsProvider.send()
```

- auth-service publishes an event instead of making a REST call
- notification-service becomes a Kafka consumer
- `SmsProvider` interface and implementations are unchanged
- The REST endpoint (`POST /api/v1/notifications/sms`) can be removed or kept as a fallback

The provider interface design does not change between the synchronous REST MVP and the Kafka future state — only the transport layer changes.

---

## Alternatives Considered

### OTP Logging in auth-service (Current State)

auth-service already logs the OTP at WARN level for MVP dev testing (`[MVP DEV] OTP for ...`).

**Rejected as permanent solution** because:
- Notification concern bleeds into auth-service
- No clean extension point for a real provider
- Logging is not a delivery mechanism

### Fire-and-Forget (Best-Effort) Call

auth-service calls notification-service but ignores errors — registration/login always succeeds.

**Rejected** because an undelivered OTP leaves the user with a login request that can never be completed. Failing fast is better UX.

### Embed Provider Directly in auth-service

Add Twilio SDK directly to auth-service.

**Rejected** because:
- Tight coupling between authentication and delivery
- Every provider change requires touching auth-service
- Cannot share notification logic with other future services

---

## Consequences

**Positive:**
- Notification concern is fully isolated from authentication logic
- Provider swap is a config change, not a code change
- Kafka migration path is well-defined and does not break the provider interface
- 10% simulated failure validates auth-service error handling before a real provider is integrated
- Consistent with the microservice model already established

**Trade-offs:**
- Adds a third service to the local development environment
- Synchronous call adds latency to the OTP login endpoint (acceptable for MVP)
- auth-service is now dependent on notification-service availability (accepted — OTP delivery is required)

---

## Related Documents

- ADR-001-ServiceBoundaries.md
- ADR-002-Authentication.md
- ApiSpecification.md
- ProductBacklog.md
