# AI_CONTEXT.md

# Merchant Harmony - AI Context

| Property | Value |
|----------|-------|
| Version | 1.0 |
| Status | Active |
| Owner | Project Lead (RM) |
| Last Updated | 2026-06-27 |

---

## Purpose

This document provides the baseline context for anyone working on the Merchant Harmony project.

It is intended to quickly onboard developers, architects, reviewers, or AI assistants without requiring previous conversation history.

This document serves as the entry point to the engineering documentation and intentionally avoids duplicating detailed requirements or architecture.

---

## Project Overview

Merchant Harmony is a Merchant–Customer engagement platform designed to help merchants build stronger customer relationships through feedback and ongoing engagement.

The project is intentionally being developed as a Minimum Viable Product (MVP) while following professional software engineering practices.

---

## Project Objectives

### Product Objective

Build a production-quality backend platform that demonstrates a practical Merchant–Customer engagement solution.

### Engineering Objective

Demonstrate the complete software development lifecycle, including:

- Product Thinking
- Requirements Engineering
- Architecture
- API Design
- Database Design
- Secure Development
- Testing
- DevOps
- Operations
- Documentation

---

## Current Product Scope

The current implementation supports:

- Merchant self-registration
- Customer self-registration
- SMS OTP authentication
- JWT authorization
- Merchant QR Code generation
- Customer scans Merchant QR Code
- Merchant–Customer association
- Feedback thread creation
- Threaded text conversations
- Merchant replies to customer feedback
- Merchant closes feedback threads

Future capabilities are maintained in **FutureRequirements.md**.

---

## Technology Stack

Backend
- Java 21
- Spring Boot 4.1
- Spring Security
- Spring Data JPA

Database
- PostgreSQL

Security
- SMS OTP
- JWT

Build
- Maven

Development
- IntelliJ IDEA
- Docker
- Git

---

## Repository Structure

```
merchant-harmony/
│
├── pom.xml
├── README.md
├── AI_CONTEXT.md
├── PROJECT_STATE.md
│
├── auth-service/
├── engagement-service/
├── common/
│
├── docs/
├── docker/
└── scripts/
```

---

## Collaboration Model

### Project Lead (RM)

Responsible for:

- Product vision
- Product roadmap
- Requirements
- Architecture decisions
- Code ownership
- Repository ownership
- Final technical decisions

### Documentation & Architecture Assistant

Responsible for:

- Engineering documentation
- Architecture reviews
- Requirements refinement
- API reviews
- Documentation consistency
- Design guidance

### Implementation Assistant

Responsible for:

- Spring Boot implementation
- Refactoring
- Unit tests
- Framework guidance
- Debugging support

All AI-generated work is reviewed and approved by the Project Lead.

---

## Engineering Principles

- Keep the MVP simple.
- Build incrementally.
- Prefer readability over cleverness.
- Avoid premature optimization.
- Move non-essential features to FutureRequirements.md.
- Document significant engineering decisions.
- Keep implementation and documentation synchronized.

---

## Documentation Principles

The repository documentation is the engineering source of truth.

When implementation changes:

- Update ProductRequirements.md
- Update Architecture.md
- Update ApiSpecification.md
- Update DataModel.md (if applicable)
- Record significant architectural decisions in DecisionLog.md

Documentation should evolve alongside the implementation.

---

## Current Status

Current Phase

```
Phase 2 – Auth Service Implementation
```

Completed

- Phase 0: Product Requirements, Future Requirements, Initial Architecture, Initial API Specification
- Phase 1: Maven multi-module, auth-service + engagement-service scaffolds, Docker Compose, common module, global exception handling, JWT infrastructure, Flyway migrations + initial schema for both databases

Next Milestone

```
Phase 3 – Engagement Service Implementation
```

---

## Primary Documents

- ProductRequirements.md
- FutureRequirements.md
- Architecture.md
- ApiSpecification.md
- DataModel.md
- DecisionLog.md
- PROJECT_STATE.md

---

## Working Agreement

Merchant Harmony is intended to resemble the internal engineering wiki of a small software company.

Documentation should remain concise, accurate, and implementation-focused.

Build the product first. Expand the documentation as the product evolves.
