package com.merchantharmony.engagement.customer.dto;

import java.util.List;

public record MerchantLandingResponse(
        MerchantSummaryResponse merchant,
        String associationStatus,
        List<LandingTopicResponse> feedbackTopics
) {}
