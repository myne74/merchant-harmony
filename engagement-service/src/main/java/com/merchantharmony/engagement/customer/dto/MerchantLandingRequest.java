package com.merchantharmony.engagement.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record MerchantLandingRequest(@NotBlank String merchantQrCode) {}
