package com.merchantharmony.engagement.merchant.dto;

import java.util.UUID;

public record MerchantTopicResponse(
        UUID merchantTopicId,
        UUID topicId,
        String name,
        String type,
        int displayOrder,
        boolean active
) {}
