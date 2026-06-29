package com.merchantharmony.auth.config;

import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class NotificationServiceClient {

    private final RestClient restClient;

    public NotificationServiceClient(@Value("${notification-service.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public void sendSms(String to, String message) {
        try {
            restClient.post()
                    .uri("/api/v1/notifications/sms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new SmsRequest(to, message))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            log.error("SMS notification failed for to={}: {}", to, e.getMessage());
            throw new MerchantHarmonyException(ErrorCode.INTERNAL_ERROR,
                    "Failed to send OTP. Please try again.");
        }
    }

    private record SmsRequest(String to, String message) {}
}
