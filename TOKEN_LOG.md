# AI Session Log — Merchant Harmony

## Purpose

Track AI collaboration sessions for retrospective analysis.
Helps identify which types of work are expensive, which patterns to avoid,
and how collaboration efficiency improves over time.

No manual data entry needed — updated by the AI assistant at the end of each session.

---

## Session Log

### Session 001 — 2026-06-28

**Phase:** Phase 0 → Phase 1 transition
**Type:** Orientation + Scaffold

**What we did:**
- Loaded and read all root markdown files and 6 key design docs
- Saved project context to persistent memory (4 memory files)
- Rewrote root `pom.xml` as a Maven multi-module parent (Spring Boot 4.1.0, Java 21)
- Created `auth-service` and `engagement-service` as child modules
- Each service: `pom.xml`, main application class, `application.properties`, test class
- Full multi-module compile verified — zero errors

**Decisions made:**
- Spring Boot 4.1.0 (user preference over 3.x)
- Java 21 (required by Spring Boot 4)
- JJWT 0.12.6 for JWT
- `auth-service` on port 8081, `engagement-service` on port 8082
- `ddl-auto=none` — schema managed by Flyway (not Hibernate)
- Package root: `com.merchantharmony`

**Cost signals:**
- High: read 6 large docs upfront (ApiSpecification.md is ~700 lines)
- Medium: wrote 9 files from scratch
- Low: no debugging loops, no back-and-forth on decisions

**What to do differently next time:**
- Don't re-read docs already in memory — use targeted reads for the specific section needed
- ApiSpecification.md: read only the endpoint being implemented, not the full file

**Pending for docs agent:**
- Update Architecture.md and AI_CONTEXT.md: Java version 17 → 21

---

## Efficiency Patterns

| Pattern | Cost Impact | Better Approach |
|---------|------------|-----------------|
| Reading all docs at session start | High | Memory + targeted section reads |
| Reading entire ApiSpecification.md | High | Read only the relevant endpoint |
| Long discussion before coding | Medium | 1-2 turns to agree, then code |
| Boilerplate scaffold files | Medium (one-time) | Acceptable — doesn't repeat |
| Re-explaining project context | High | Memory system eliminates this |

---

---

### Session 002 — 2026-06-28

**Phase:** Phase 1 (continued)
**Type:** Infrastructure + Schema

**What we did:**
- Picked up from context compaction mid-session (Flyway was next)
- Added `flyway-core` + `flyway-database-postgresql` to auth-service and engagement-service poms
- Created `auth_db` migrations: V1 merchant, V2 customer, V3 otp_request
- Created `engagement_db` migrations: V1 feedback_topic_master, V2 merchant_customer, V3 merchant_topic, V4 feedback_thread, V5 comment, V6 seed data (24 rows across 5 categories)
- All tests passed: context load tests confirmed Flyway ran all migrations against live PostgreSQL containers
- Filled in FlywayMigrations.md, DataDictionary.md, updated ERDiagram.md and IndexStrategy.md to Active status
- Updated PROJECT_STATE.md and AI_CONTEXT.md to reflect Phase 1 complete

**Decisions made:**
- Merchant Landing merchant profile data fetched from auth-service via REST (GET /api/v1/internal/merchants/{merchantId}) — chosen over denormalized engagement_db copy for MVP simplicity
- ddl-auto=none stays until JPA entities are introduced in Phase 2 (then switch to validate)
- Cross-database FK constraints omitted by design (database-per-service); referential integrity via JWT + application layer
- Two indexes deferred: merchant_topic.topic_id and feedback_thread.merchant_topic_id (not primary query paths in MVP)

**Cost signals:**
- Low: context compaction forced a re-read of poms and data model, but targeted reads kept overhead minimal
- Low: no debugging loops — Flyway wired up cleanly on first compile + test run

**What to do differently next time:**
- Read poms at session start to confirm current state before editing (saves one "file not read" error)

---

---

### Session 003 — 2026-06-28

**Phase:** Phase 2 — Auth Service Implementation
**Type:** Business logic + entity layer

**What we did:**
- Added Lombok to auth-service pom with `annotationProcessorPaths` fix in root pom (Spring Boot 4.1 / Maven compiler plugin requires explicit processor path)
- Added `spring-boot-flyway` to both service poms (Spring Boot 4.1 split Flyway auto-configuration into its own module)
- Added `MerchantCategory` and `MerchantStatus` enums to common domain package
- Built full auth-service implementation: Merchant entity/repo/service/controller, Customer entity/repo/service/controller, OTP entity/repo/service, internal merchant profile endpoint
- Package-by-feature structure: merchant/, customer/, otp/, internal/
- DTOs as Java records (immutable, concise, Jackson 3.x compatible)
- `ddl-auto` upgraded from `none` to `validate` — Hibernate now validates schema on startup
- P2-004 (topic initialization call) deferred to Phase 3 — logged as TODO
- All tests green: 3 migrations on auth_db, 6 migrations on engagement_db

**Decisions made:**
- Package-by-feature over package-by-layer
- Lombok for entity boilerplate; `@Data` avoided on JPA entities
- `SecureRandom` for OTP generation (security-sensitive)
- OTP logged at WARN for MVP dev testing — not returned in response
- Internal endpoint protected by any valid JWT (pass-through auth for MVP)

**Discoveries:**
- Spring Boot 4.1: Lombok requires `<annotationProcessorPaths>` in maven-compiler-plugin — without it, annotated code compiles but Lombok generates nothing
- Spring Boot 4.1: `FlywayAutoConfiguration` moved to `spring-boot-flyway` jar — Flyway on classpath is not enough

**Cost signals:**
- Medium: 3 rounds of build debugging (Lombok processor, FlywayAutoConfiguration split, Docker cleanup)
- Low: once patterns were clear, all 24 files written without back-and-forth

---

---

### Session 004 — 2026-06-29

**Phase:** Phase 3 — Engagement Service Implementation
**Type:** Business logic + entity layer (engagement-service)

**What we did:**
- Added Lombok + spring-boot-starter-validation to engagement-service pom
- Upgraded engagement-service ddl-auto to validate; added open-in-view=false to both services
- Updated engagement-service SecurityConfig: role-based path rules (MERCHANT/CUSTOMER), internal endpoints permitted without JWT
- Built full engagement-service implementation: 5 entities, 5 repositories, 4 services, 4 controllers, 3 enums, 15+ DTOs
- AuthServiceClient (RestClient) in engagement-service calls auth-service internal endpoints
- EngagementServiceClient (RestClient) in auth-service wires P2-004: topic init at merchant registration
- Added auth-service internal endpoints: GET /by-qr/{qrCode}, GET /internal/customers/{customerId}
- Updated MerchantProfileResponse to include phoneNumber + status
- All 21 Phase 3 backlog items implemented; BUILD SUCCESS across all 4 modules

**Decisions made:**
- RestClient (Spring 6.1+) over RestTemplate for cross-service HTTP calls (RestTemplate deprecated in Spring 7)
- Internal topic init endpoint permitted without JWT (auth→engagement call happens before JWT exists at merchant registration)
- Topic initialization is best-effort from auth-service: exceptions caught and logged, merchant registration never fails for topic init errors
- `spring.jpa.open-in-view=false` on both services (explicit opt-out of OSIV; lazy loading handled by @Transactional on service methods)
- FETCH JOIN JPQL queries on thread/topic lists to avoid N+1 (MerchantTopicRepository, FeedbackThreadRepository)
- Merchant customer list and associated-merchant list use N calls to auth-service per item (MVP acceptable at small scale)
- Business rule enforcement: cannot disable last active merchant topic (countByMerchantIdAndActive check)

**Cost signals:**
- Low: all files written in parallel batches without debugging loops — clean compile first try
- Session was fast because entity schema was already locked in Flyway migrations; no design ambiguity

**What to do differently next time:**
- Consider batching auth-service calls (N calls per list item is fine at MVP scale but document the N+1 pattern as tech debt)

---

### Session 004 (continued) — 2026-06-29

**Phase:** Phase 4 — Notification Service
**Type:** New microservice + cross-service wiring

**What we did:**
- Added notification-service as the 4th Maven module (port 8083, no database)
- SmsProvider interface (extension point for all SMS implementations)
- LoggingSmsProvider: logs OTP at INFO, 10% simulated failure via SecureRandom, @ConditionalOnProperty(sms.provider=logging)
- POST /api/v1/notifications/sms endpoint — no JWT, internal trusted network
- SecurityConfig in notification-service: permitAll (Spring Security is transitive via common)
- NotificationServiceClient in auth-service (RestClient) — propagates errors as MerchantHarmonyException(INTERNAL_ERROR)
- OtpService.initiateLogin() signature updated to include phoneNumber; WARN log replaced with notification call
- auth-service propagates notification failure to OTP login caller (@Transactional rolls back OTP save on error)
- Bug fix: added scanBasePackages="com.merchantharmony" to all three service main classes so GlobalExceptionHandler from common is correctly discovered by component scanning
- All 4 modules: BUILD SUCCESS

**Decisions made:**
- @ConditionalOnProperty on LoggingSmsProvider: future providers wired in by setting sms.provider=twilio (or similar) in config
- NotificationServiceClient does NOT swallow errors (unlike EngagementServiceClient) — undelivered OTP must surface to caller
- @Transactional on initiateLogin means OTP save is rolled back if notification fails — no orphaned OTP records

**Discoveries:**
- GlobalExceptionHandler in common was NOT being picked up by auth-service/engagement-service (package outside their scan path) — fixed with scanBasePackages

---

## Progress Tracker

| Phase | Status | Session |
|-------|--------|---------|
| Phase 0 — Engineering Foundation | Complete | Pre-Day-1 |
| Phase 1 — Maven multi-module setup | Complete | 001 |
| Phase 1 — Docker Compose + PostgreSQL | Complete | 001 |
| Phase 1 — Global exception handling | Complete | 001 |
| Phase 1 — JWT infrastructure | Complete | 001 |
| Phase 1 — Flyway + DB schema | Complete | 002 |
| Phase 2 — Auth service implementation | Complete | 003 |
| Phase 3 — Engagement service implementation | Complete | 004 |
| Phase 4 — Notification service | Complete | 004 |
