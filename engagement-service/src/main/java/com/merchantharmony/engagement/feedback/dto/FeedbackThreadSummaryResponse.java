package com.merchantharmony.engagement.feedback.dto;

import java.time.Instant;
import java.util.UUID;

public record FeedbackThreadSummaryResponse(
        UUID threadId,
        UUID merchantTopicId,
        String topicName,
        String status,
        Instant createdAt
) {}
