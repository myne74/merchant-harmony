package com.merchantharmony.engagement.feedback.dto;

import java.time.Instant;
import java.util.UUID;

public record CommentResponse(
        UUID commentId,
        String authorType,
        String message,
        Instant createdAt
) {}
