# Interview Talking Points

| Property | Value |
|----------|-------|
| Version | 1.1 |
| Status | Active |
| Owner | Project Lead (Raveendra Myneni) |
| Last Updated | 2026-06-28 |

---

## Elevator Pitch

Merchant Harmony is a cloud-native customer engagement platform that enables merchants and customers to communicate through structured feedback conversations initiated by scanning a merchant QR code.

The platform emphasizes a frictionless onboarding experience, clean domain boundaries, and independently deployable microservices.

---

## Architecture Highlights

- Java 21 and Spring Boot
- Maven multi-module repository
- Two independently deployable microservices:
  - auth-service
  - engagement-service
- Database per service
- RESTful APIs
- Flyway-managed schema migrations
- Docker-based local development

---

## Key Design Decisions

- Separate Merchant and Customer authentication flows
- SMS OTP with JWT authorization
- Merchant QR Code as the primary customer entry point
- Merchant Landing performs onboarding in one API call
- Feedback Topics managed as category master data
- Merchant Topics provisioned automatically during registration
- Every Feedback Thread belongs to exactly one active Merchant Topic
- Merchants must always have at least one active Feedback Topic

---

## Design Principles

- Simplicity over unnecessary flexibility
- Clear ownership of data
- Minimize API round trips
- Optimize first-time user experience
- Independent deployment
- Backward-compatible API evolution

---

## Why This Architecture?

- Simple MVP that scales naturally
- Business ownership reflected in service boundaries
- Database-per-service for autonomy
- Immediate merchant operability after registration
- Optimized customer onboarding through Merchant Landing

---

## Future Evolution

- Loyalty Platform
- Promotions Engine
- Notification Service
- Kafka event streaming
- AI-assisted feedback analysis
- Multi-region deployment
- Search and analytics

---

## Interview Summary

This project demonstrates:

- Domain-driven design
- Microservice architecture
- REST API design
- Security architecture
- Database modeling
- ADR-driven architecture
- Production-ready engineering documentation
- Clean repository organization
