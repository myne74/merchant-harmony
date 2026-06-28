# Project State

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document provides the current project snapshot for Merchant Harmony.

It enables developers, reviewers, architects, and future contributors to quickly understand the current state of the project without reviewing implementation details.

This document should be updated at the end of each major milestone or development sprint.

---

## Current Phase

```text
Phase 0 – Engineering Foundation
```

The current focus is establishing a solid engineering foundation before active feature development begins.

---

## Current Product

Merchant Harmony is a Merchant–Customer engagement platform focused on helping merchants collect customer feedback, maintain customer relationships, and evolve into a broader customer engagement platform.

The current implementation scope includes:

- Merchant self-registration
- Customer self-registration
- SMS OTP authentication
- JWT authorization
- Merchant QR Code generation
- Customer scans Merchant QR Code
- Merchant–Customer association
- Feedback thread creation
- Threaded conversations
- Merchant closes feedback threads

---

## Completed

The following engineering artifacts have been completed or drafted:

- Repository structure
- Engineering Wiki structure
- AI Context
- Product Requirements
- Future Requirements
- Initial Architecture
- Initial API Specification
- Engineering naming conventions
- Repository organization

---

## Current Focus

The current objectives are:

- Finalize core engineering documentation
- Prepare the repository for implementation
- Freeze documentation restructuring
- Begin Sprint 1 implementation

---

## Next Milestone

```text
Phase 1 – Foundation Implementation
```

Planned work:

- Parent Maven project
- auth-service foundation
- engagement-service foundation
- Spring Boot configuration
- PostgreSQL integration
- Docker Compose
- Global exception handling
- Validation framework
- Health endpoints
- JWT infrastructure

---

## Current Blockers

None.

---

## Documentation Baseline

Core engineering artifacts include:

- AI_CONTEXT.md
- ProductRequirements.md
- FutureRequirements.md
- Architecture.md
- ApiSpecification.md
- DataModel.md
- Authentication.md
- DecisionLog.md

---

## Update Guidelines

Update this document when:

- The project enters a new phase.
- A major milestone is completed.
- A blocker is introduced or resolved.
- A significant architectural or product decision is made.

Keep this document concise and current. It should always reflect the present state of the project.
