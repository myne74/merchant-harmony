package com.merchantharmony.engagement.topic.dto;

import java.util.UUID;

public record FeedbackTopicMasterResponse(
        UUID topicId,
        String category,
        String name,
        String type,
        int displayOrder,
        boolean active
) {}
