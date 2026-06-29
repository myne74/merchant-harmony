package com.merchantharmony.engagement.merchant.dto;

import java.time.Instant;
import java.util.UUID;

public record MerchantCustomerResponse(
        UUID customerId,
        String name,
        String phoneNumber,
        Instant associatedAt
) {}
