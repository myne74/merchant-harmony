# Key Decisions

A running log of significant decisions made during implementation — the "why we did it this way" reference. For formal architectural decisions see `docs/12-architecture-decisions/`.

---

## Phase 3 — Engagement Service Implementation (2026-06-29)

| Decision | Choice Made | Why |
|----------|-------------|-----|
| REST client | `RestClient` (Spring 6.1+) | `RestTemplate` is deprecated in Spring 7; `RestClient` is the synchronous replacement with cleaner fluent API |
| Internal endpoint auth | Permit `/api/v1/internal/**` without JWT in engagement-service | Topic init call from auth-service happens during merchant registration before any JWT is issued; trusted internal network for MVP |
| Topic init error handling | Best-effort (catch + log, never fail registration) | Keeping registration atomic is better UX than cascading engagement-service failures |
| open-in-view | `false` on both services | Explicit opt-out of Open Session In View; lazy loading handled by `@Transactional(readOnly=true)` on service methods |
| N+1 for list-with-auth-data calls | Accepted for MVP | N calls to auth-service per item in merchant-customer list and associated-merchant list; small scale, noted as tech debt |
| FETCH JOIN on thread/topic queries | JPQL `JOIN FETCH` in repository | Prevents N+1 when mapping thread→merchantTopic→topic in list endpoints |
| Business rule: last active topic | `countByMerchantIdAndActive` before disabling | Spec rule: "at least one active feedback topic at all times" — checked in service before persisting the update |
| Role-based path security | `hasRole()` rules in SecurityConfig | Enforces MERCHANT/CUSTOMER separation at the framework level, not per-endpoint logic |

---

## Phase 2 — Auth Service Implementation (2026-06-28)

| Decision | Choice Made | Why |
|----------|-------------|-----|
| Package structure | Package-by-feature (merchant/, customer/, otp/) | Cohesion — each feature owns its entity, repo, service, controller together |
| Entity boilerplate | Lombok `@Getter @Setter @NoArgsConstructor` | 150+ lines of getters/setters eliminated; `@Data` avoided on JPA entities (equals/hashCode issues with lazy loading) |
| DTOs | Java records | Immutable, concise, Jackson 3.x deserializes natively via canonical constructor |
| OTP generation | `SecureRandom`, not `Random` | `Random` is predictable; OTPs are security-sensitive — always use `SecureRandom` |
| OTP dev visibility | Log at WARN level | No SMS integration for MVP; OTP visible in logs for testing without exposing it in API response |
| Hibernate validation | `ddl-auto=validate` (upgraded from `none`) | Now that entities exist, Hibernate validates schema on startup — catches column/type mismatches fast |
| OtpUserType enum | Separate from `UserRole` | Auth flow concern vs security concern — decoupled so they can evolve independently |
| Internal endpoint auth | Any valid JWT (pass-through) | Engagement-service passes customer JWT to auth-service internal API; simple for MVP, can add service-account JWT later |

---

## Phase 1 — Foundation Implementation (2026-06-28)

| Decision | Choice Made | Why |
|----------|-------------|-----|
| Spring Boot version | 4.1 over 3.x | Learning project — encounter real frontier problems, not solved ones |
| Java version | 21 | Required by Spring Boot 4.1 |
| JWT signing | HMAC-SHA256 (symmetric) | All services share the secret; no external verifier; RSA adds complexity without benefit here |
| JWT principal | Custom `UserPrincipal` record, not `UserDetails` | Only need userId + role; avoid tying domain to framework interface |
| Common module beans | `@Bean` in each service's `SecurityConfig`, not `@Component` in common | Library design — consumers decide wiring; don't impose scanning behavior |
| Schema management | Flyway only, `ddl-auto=none` | Hibernate DDL is not auditable or reversible; Flyway migrations are both |
| Cross-DB references | Plain UUIDs, no FK constraints | Database-per-service means no cross-DB enforcement; application layer (JWT) owns integrity |
| Feedback topic master | Pre-seeded by category, not merchant-created | Consistent quality, instant onboarding, no empty-state problem at registration |
| Merchant Landing data | Fetch merchant profile from auth-service via REST | Simpler than denormalized copy for MVP; revisit if latency becomes a real problem |
| Documentation ownership | Implementation assistant owns docs alongside code | Docs written by the person who knows the why; prevents drift |

---

## Discoveries (not decisions, but worth recording)

| Discovery | Impact |
|-----------|--------|
| Spring Boot 4.1: Jackson 3.x renamed to `tools.jackson.*` | All ObjectMapper imports had to change |
| Spring Boot 4.1: `UserDetailsServiceAutoConfiguration` moved to `org.springframework.boot.security.autoconfigure` | Exclusion in `@SpringBootApplication` had to be updated |
| Spring Boot 4.1: Security auto-config split into `spring-boot-security` jar | Affected import resolution for security beans |
| Flyway 10+: needs `flyway-database-postgresql` as a separate dependency for PostgreSQL | Added explicitly to both service poms |
| Spring Boot 4.1: `FlywayAutoConfiguration` extracted to `spring-boot-flyway` jar | Must add `spring-boot-flyway` dependency explicitly; without it Flyway is on classpath but never runs |
| Spring Boot 4.1: Lombok needs explicit `<annotationProcessorPaths>` in maven-compiler-plugin | Without it, `@Getter`/`@Setter`/`@Slf4j` compile but generate no code — methods not found at compile time |
