package com.merchantharmony.engagement.customer;

import com.merchantharmony.engagement.customer.dto.*;
import com.merchantharmony.engagement.internal.AuthServiceClient;
import com.merchantharmony.engagement.internal.dto.AuthCustomerProfileResponse;
import com.merchantharmony.engagement.internal.dto.AuthMerchantProfileResponse;
import com.merchantharmony.engagement.merchant.MerchantTopic;
import com.merchantharmony.engagement.merchant.MerchantTopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerEngagementService {

    private final MerchantCustomerRepository merchantCustomerRepository;
    private final MerchantTopicRepository merchantTopicRepository;
    private final AuthServiceClient authServiceClient;

    public AuthCustomerProfileResponse getCustomerProfile(UUID customerId, String authHeader) {
        return authServiceClient.getCustomerProfile(customerId, authHeader);
    }

    @Transactional
    public MerchantLandingResponse merchantLanding(UUID customerId, String qrCode, String authHeader) {
        AuthMerchantProfileResponse merchantProfile =
                authServiceClient.getMerchantByQrCode(qrCode, authHeader);

        UUID merchantId = merchantProfile.merchantId();

        merchantCustomerRepository.findByMerchantIdAndCustomerId(merchantId, customerId)
                .orElseGet(() -> {
                    MerchantCustomer newAssoc = new MerchantCustomer();
                    newAssoc.setMerchantId(merchantId);
                    newAssoc.setCustomerId(customerId);
                    return merchantCustomerRepository.save(newAssoc);
                });

        List<LandingTopicResponse> topics = merchantTopicRepository
                .findActiveByMerchantIdWithTopic(merchantId)
                .stream()
                .map(mt -> new LandingTopicResponse(
                        mt.getMerchantTopicId(),
                        mt.getTopic().getName(),
                        mt.getTopic().getType().name(),
                        mt.getTopic().getDisplayOrder()))
                .toList();

        MerchantSummaryResponse merchantSummary = new MerchantSummaryResponse(
                merchantProfile.merchantId(),
                merchantProfile.businessName(),
                merchantProfile.displayName(),
                merchantProfile.category());

        return new MerchantLandingResponse(merchantSummary, "ACTIVE", topics);
    }

    @Transactional(readOnly = true)
    public List<MerchantSummaryResponse> getAssociatedMerchants(UUID customerId, String authHeader) {
        return merchantCustomerRepository.findByCustomerId(customerId)
                .stream()
                .map(assoc -> {
                    AuthMerchantProfileResponse profile =
                            authServiceClient.getMerchantProfile(assoc.getMerchantId(), authHeader);
                    return new MerchantSummaryResponse(
                            profile.merchantId(),
                            profile.businessName(),
                            profile.displayName(),
                            profile.category());
                })
                .toList();
    }
}
