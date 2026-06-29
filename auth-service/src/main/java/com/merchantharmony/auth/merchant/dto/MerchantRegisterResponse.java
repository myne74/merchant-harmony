package com.merchantharmony.auth.merchant.dto;

import java.util.UUID;

public record MerchantRegisterResponse(
        UUID merchantId,
        String businessName,
        String status,
        String qrCode
) {}
