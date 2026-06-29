package com.merchantharmony.auth.otp.dto;

import java.util.UUID;

public record LoginResponse(
        UUID otpRequestId,
        String message
) {}
