package com.merchantharmony.engagement.feedback.dto;

import jakarta.validation.constraints.NotBlank;

public record AddCommentRequest(@NotBlank String message) {}
