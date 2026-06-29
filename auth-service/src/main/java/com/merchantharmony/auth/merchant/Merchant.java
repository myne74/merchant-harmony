package com.merchantharmony.auth.merchant;

import com.merchantharmony.common.domain.MerchantCategory;
import com.merchantharmony.common.domain.MerchantStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "merchant")
@Getter
@Setter
@NoArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "merchant_id")
    private UUID merchantId;

    @Column(nullable = false)
    private String businessName;

    private String displayName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private String email;

    private String website;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status;

    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String stateProvince;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, unique = true)
    private String qrCode;

    @Column(nullable = false)
    private Instant qrCodeGeneratedAt;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
