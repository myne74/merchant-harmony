package com.merchantharmony.engagement.feedback.dto;

import java.util.UUID;

public record CloseThreadResponse(UUID threadId, String status) {}
