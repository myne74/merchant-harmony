package com.merchantharmony.engagement.customer.dto;

import java.util.UUID;

public record LandingTopicResponse(
        UUID merchantTopicId,
        String name,
        String type,
        int displayOrder
) {}
