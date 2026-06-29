package com.merchantharmony.auth.otp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "otp_request")
@Getter
@Setter
@NoArgsConstructor
public class OtpRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "otp_request_id")
    private UUID otpRequestId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private OtpUserType userType;

    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = Instant.now();
    }
}
