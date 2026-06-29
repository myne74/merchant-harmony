package com.merchantharmony.engagement.merchant;

import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import com.merchantharmony.engagement.customer.MerchantCustomer;
import com.merchantharmony.engagement.customer.MerchantCustomerRepository;
import com.merchantharmony.engagement.internal.AuthServiceClient;
import com.merchantharmony.engagement.internal.dto.AuthCustomerProfileResponse;
import com.merchantharmony.engagement.internal.dto.AuthMerchantProfileResponse;
import com.merchantharmony.engagement.merchant.dto.MerchantCustomerResponse;
import com.merchantharmony.engagement.merchant.dto.MerchantTopicResponse;
import com.merchantharmony.engagement.merchant.dto.UpdateMerchantTopicRequest;
import com.merchantharmony.engagement.merchant.dto.UpdateMerchantTopicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantEngagementService {

    private final MerchantTopicRepository merchantTopicRepository;
    private final MerchantCustomerRepository merchantCustomerRepository;
    private final AuthServiceClient authServiceClient;

    public AuthMerchantProfileResponse getMerchantProfile(UUID merchantId, String authHeader) {
        return authServiceClient.getMerchantProfile(merchantId, authHeader);
    }

    @Transactional(readOnly = true)
    public List<MerchantTopicResponse> getMerchantTopics(UUID merchantId) {
        return merchantTopicRepository.findByMerchantIdWithTopic(merchantId)
                .stream()
                .map(mt -> new MerchantTopicResponse(
                        mt.getMerchantTopicId(),
                        mt.getTopic().getTopicId(),
                        mt.getTopic().getName(),
                        mt.getTopic().getType().name(),
                        mt.getTopic().getDisplayOrder(),
                        mt.isActive()))
                .toList();
    }

    @Transactional
    public UpdateMerchantTopicResponse updateMerchantTopicStatus(UUID merchantTopicId, UUID merchantId,
                                                                   UpdateMerchantTopicRequest request) {
        MerchantTopic merchantTopic = merchantTopicRepository
                .findByMerchantTopicIdAndMerchantId(merchantTopicId, merchantId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND,
                        "Merchant topic not found"));

        if (!request.active() && merchantTopicRepository.countByMerchantIdAndActive(merchantId, true) <= 1) {
            throw new MerchantHarmonyException(ErrorCode.BUSINESS_RULE_VIOLATION,
                    "Cannot disable the last active feedback topic");
        }

        merchantTopic.setActive(request.active());
        merchantTopicRepository.save(merchantTopic);

        return new UpdateMerchantTopicResponse(merchantTopic.getMerchantTopicId(), merchantTopic.isActive());
    }

    @Transactional(readOnly = true)
    public List<MerchantCustomerResponse> getMerchantCustomers(UUID merchantId, String authHeader) {
        List<MerchantCustomer> associations = merchantCustomerRepository.findByMerchantId(merchantId);
        return associations.stream()
                .map(assoc -> {
                    AuthCustomerProfileResponse customer =
                            authServiceClient.getCustomerProfile(assoc.getCustomerId(), authHeader);
                    return new MerchantCustomerResponse(
                            customer.customerId(),
                            customer.name(),
                            customer.phoneNumber(),
                            assoc.getAssociatedAt());
                })
                .toList();
    }
}
