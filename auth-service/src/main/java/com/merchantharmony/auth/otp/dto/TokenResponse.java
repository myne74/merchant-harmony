package com.merchantharmony.auth.otp.dto;

public record TokenResponse(
        String accessToken,
        String tokenType
) {}
