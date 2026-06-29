package com.merchantharmony.auth.otp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OtpRequestRepository extends JpaRepository<OtpRequest, UUID> {}
