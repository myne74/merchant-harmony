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

## Progress Tracker

| Phase | Status | Session |
|-------|--------|---------|
| Phase 0 — Engineering Foundation | Complete | Pre-Day-1 |
| Phase 1 — Maven multi-module setup | Complete | 001 |
| Phase 1 — Docker Compose + PostgreSQL | Complete | 001 |
| Phase 1 — Global exception handling | Complete | 001 |
| Phase 1 — JWT infrastructure | Complete | 001 |
| Phase 1 — Flyway + DB schema | Complete | 002 |
| Phase 2 — Auth service implementation | Pending | — |
| Phase 3 — Engagement service implementation | Pending | — |
