package com.merchantharmony.engagement.internal.dto;

import java.util.UUID;

public record AuthMerchantProfileResponse(
        UUID merchantId,
        String businessName,
        String displayName,
        String phoneNumber,
        String category,
        String status,
        String qrCode
) {}
