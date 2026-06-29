# Key Decisions

A running log of significant decisions made during implementation — the "why we did it this way" reference. For formal architectural decisions see `docs/12-architecture-decisions/`.

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
