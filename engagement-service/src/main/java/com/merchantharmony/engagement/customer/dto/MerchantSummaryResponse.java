package com.merchantharmony.engagement.customer.dto;

import java.util.UUID;

public record MerchantSummaryResponse(
        UUID merchantId,
        String businessName,
        String displayName,
        String category
) {}
