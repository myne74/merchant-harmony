package com.merchantharmony.engagement.feedback.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record FeedbackThreadDetailResponse(
        UUID threadId,
        UUID merchantId,
        UUID customerId,
        UUID merchantTopicId,
        String topicName,
        String status,
        Instant createdAt,
        Instant closedAt,
        List<CommentResponse> comments
) {}
