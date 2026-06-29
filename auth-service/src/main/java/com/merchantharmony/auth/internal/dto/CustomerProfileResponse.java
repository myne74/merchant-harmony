package com.merchantharmony.auth.internal.dto;

import java.util.UUID;

public record CustomerProfileResponse(
        UUID customerId,
        String name,
        String phoneNumber,
        String email
) {}
