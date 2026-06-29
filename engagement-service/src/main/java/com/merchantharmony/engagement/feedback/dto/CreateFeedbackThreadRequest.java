package com.merchantharmony.engagement.feedback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateFeedbackThreadRequest(
        @NotNull UUID merchantTopicId,
        @NotBlank String message
) {}
