package com.merchantharmony.engagement.merchant.dto;

import java.util.UUID;

public record UpdateMerchantTopicResponse(UUID merchantTopicId, boolean active) {}
