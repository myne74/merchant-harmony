# requirements_future.md

# Merchant Customer Loyalty Platform - Future Requirements

Version: 1.0
Status: Draft

---

## 1. Authentication Enhancements

Future authentication methods:

- Email and Password
- Google Login
- Apple Login
- Social Login
- Multi Factor Authentication (MFA)
- Remember Device
- Password Reset

---

## 2. Merchant Hierarchy

Current MVP treats every store as an independent Merchant.

Future:

A Merchant may own multiple stores.

Example:

```text
ABC Pizza
 |
 +-- Fremont Store
 +-- San Jose Store
 +-- Santa Clara Store
```

Capabilities:

- Parent Merchant
- Child Merchants
- Merchant Groups
- Shared customers
- Shared promotions
- Group level reporting

---

## 3. QR Enhancements

Future QR capabilities:

- Merchant QR regeneration
- Customer QR regeneration
- QR expiration
- QR analytics
- Temporary QR codes
- Event specific QR codes
- Campaign QR codes

---

## 4. Merchant Customer Association

Future capabilities:

### Merchant scans Customer QR

Merchant scans customer QR.

If association does not exist:

- Create association

If association exists:

- Display customer profile

---

### Association Management

- Customer can leave Merchant
- Merchant can remove Customer
- Association status
- Association expiration
- Block Customer
- Blacklist Customer

---

## 5. Feedback Enhancements

### Thread Categories

Supported categories:

- SERVICE
- PRODUCT
- PAYMENT
- DELIVERY
- OTHER

Merchant configurable categories may be supported.

---

### Thread Priority

Supported priorities:

- LOW
- MEDIUM
- HIGH
- CRITICAL

---

### Thread Status

Additional statuses:

- OPEN
- IN_PROGRESS
- WAITING_CUSTOMER
- WAITING_MERCHANT
- CLOSED

---

### Assignment

Merchant may assign thread to:

- Manager
- Staff
- Department

---

## 6. Comments

Future comment capabilities:

- Edit Comment
- Delete Comment
- Rich Text
- Images
- Attachments
- Emoji Reactions
- Mention Users
- Comment History

---

## 7. Merchant Profile

Merchant profile enhancements:

- Business Hours
- Website URL
- Social Media Links
- Business Description
- Logo
- Store Images
- Location Map
- Service Menu

---

## 8. Notifications

Notifications are out of scope for MVP.

Future:

### Merchant Notifications

Merchant notified when:

- Customer creates feedback
- Customer replies
- Customer associates

Notification channels:

- SMS
- Email
- Push Notifications

---

### Customer Notifications

Customer notified when:

- Merchant replies
- Merchant closes thread
- Merchant sends promotion

Notification channels:

- SMS
- Email
- Push Notifications

---

## 9. Promotions

Merchants can create:

- Promotions
- Campaigns
- Coupons
- Discount Codes
- Limited Time Offers

Capabilities:

- Schedule promotions
- Send to selected customers
- Send to all customers
- Promotion history
- Promotion analytics

---

## 10. Loyalty Program

Future loyalty features:

### Points

Customer earns points for:

- Purchases
- Visits
- Reviews
- Referrals

---

### Rewards

Customers redeem:

- Free items
- Discounts
- Coupons
- Exclusive offers

---

### Membership Levels

Examples:

- Bronze
- Silver
- Gold
- Platinum

Each level may provide different benefits.

---

## 11. LLM Integration

Future AI capabilities:

### Feedback Thread Summary

Summarize:

- Entire thread
- Key issues
- Suggested actions

---

### Merchant Summary

Summarize:

- Weekly feedback
- Monthly feedback
- Top complaints
- Positive sentiment
- Negative sentiment
- Trending issues

---

### Customer Insights

Provide:

- Customer sentiment
- Customer loyalty score
- High value customers
- At risk customers

---

### Merchant Insights

Provide:

- Areas of improvement
- Business recommendations
- Service quality trends
- Operational suggestions

---

## 12. Admin Portal

Admin capabilities:

- Merchant Approval
- Merchant Suspension
- Customer Suspension
- User Search
- Audit Logs
- System Configuration
- Feature Flags
- Category Management

---

## 13. Analytics Dashboard

Merchant dashboards:

- Total Customers
- Active Customers
- Feedback Trends
- Customer Growth
- Thread Status Distribution
- Average Response Time
- Customer Satisfaction

---

## 14. Security Enhancements

Future security features:

- Rate Limiting
- DDoS Protection
- CAPTCHA
- Device Fingerprinting
- Session Revocation
- JWT Refresh Tokens
- IP Restrictions
- Audit Trail

---

## 15. Platform Enhancements

Future platform capabilities:

- Distributed Tracing
- Metrics Dashboard
- Centralized Logging
- Multi Region Deployment
- Database Replication
- Read Replicas
- Event Driven Architecture
- Kafka Integration
- Cache Layer
- Search Service

---

## 16. Future Microservices

Possible future services:

```text
Auth Service
Engagement Service
Notification Service
Promotion Service
Loyalty Service
LLM Service
Analytics Service
Admin Service
Search Service
```
