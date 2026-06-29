package com.merchantharmony.auth.internal.dto;

import java.util.UUID;

public record MerchantProfileResponse(
        UUID merchantId,
        String businessName,
        String displayName,
        String category,
        String qrCode
) {}
