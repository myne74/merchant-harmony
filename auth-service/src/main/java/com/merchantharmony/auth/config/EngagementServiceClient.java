package com.merchantharmony.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@Slf4j
public class EngagementServiceClient {

    private final RestClient restClient;

    public EngagementServiceClient(@Value("${engagement-service.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public void initializeMerchantTopics(UUID merchantId, String category) {
        try {
            restClient.post()
                    .uri("/api/v1/internal/merchants/{merchantId}/topics/initialize", merchantId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new TopicInitRequest(category))
                    .retrieve()
                    .toBodilessEntity();
            log.info("Topics initialized for merchantId={}, category={}", merchantId, category);
        } catch (Exception e) {
            log.error("Failed to initialize topics for merchantId={}: {}", merchantId, e.getMessage());
        }
    }

    private record TopicInitRequest(String category) {}
}
