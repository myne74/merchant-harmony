package com.merchantharmony.engagement.merchant.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateMerchantTopicRequest(@NotNull Boolean active) {}
