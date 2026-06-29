package com.merchantharmony.engagement.internal.dto;

import java.util.UUID;

public record AuthCustomerProfileResponse(
        UUID customerId,
        String name,
        String phoneNumber,
        String email
) {}
