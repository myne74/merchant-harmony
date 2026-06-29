package com.merchantharmony.auth.otp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OtpVerifyRequest(
        @NotNull(message = "OTP request ID is required")
        java.util.UUID otpRequestId,

        @NotBlank(message = "OTP is required")
        @Pattern(regexp = "\\d{6}", message = "OTP must be 6 digits")
        String otp
) {}
