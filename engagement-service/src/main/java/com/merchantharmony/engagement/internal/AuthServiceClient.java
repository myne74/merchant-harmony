package com.merchantharmony.engagement.internal;

import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import com.merchantharmony.engagement.internal.dto.AuthCustomerProfileResponse;
import com.merchantharmony.engagement.internal.dto.AuthMerchantProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@Slf4j
public class AuthServiceClient {

    private final RestClient restClient;

    public AuthServiceClient(@Value("${auth-service.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public AuthMerchantProfileResponse getMerchantProfile(UUID merchantId, String authorizationHeader) {
        return restClient.get()
                .uri("/api/v1/internal/merchants/{id}", merchantId)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    if (res.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new MerchantHarmonyException(ErrorCode.NOT_FOUND, "Merchant not found");
                    }
                    throw new MerchantHarmonyException(ErrorCode.INTERNAL_ERROR, "Auth service error");
                })
                .body(AuthMerchantProfileResponse.class);
    }

    public AuthMerchantProfileResponse getMerchantByQrCode(String qrCode, String authorizationHeader) {
        return restClient.get()
                .uri("/api/v1/internal/merchants/by-qr/{code}", qrCode)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    if (res.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new MerchantHarmonyException(ErrorCode.NOT_FOUND, "Invalid QR code");
                    }
                    throw new MerchantHarmonyException(ErrorCode.INTERNAL_ERROR, "Auth service error");
                })
                .body(AuthMerchantProfileResponse.class);
    }

    public AuthCustomerProfileResponse getCustomerProfile(UUID customerId, String authorizationHeader) {
        return restClient.get()
                .uri("/api/v1/internal/customers/{id}", customerId)
                .header("Authorization", authorizationHeader)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    if (res.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new MerchantHarmonyException(ErrorCode.NOT_FOUND, "Customer not found");
                    }
                    throw new MerchantHarmonyException(ErrorCode.INTERNAL_ERROR, "Auth service error");
                })
                .body(AuthCustomerProfileResponse.class);
    }
}
