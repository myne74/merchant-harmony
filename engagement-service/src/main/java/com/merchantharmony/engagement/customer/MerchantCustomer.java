package com.merchantharmony.engagement.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "merchant_customer")
@Getter
@Setter
@NoArgsConstructor
public class MerchantCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "association_id")
    private UUID associationId;

    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "associated_at", nullable = false, updatable = false)
    private Instant associatedAt;

    @PrePersist
    private void prePersist() {
        associatedAt = Instant.now();
    }
}
