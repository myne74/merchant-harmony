package com.merchantharmony.auth.merchant;

import com.merchantharmony.auth.config.EngagementServiceClient;
import com.merchantharmony.auth.merchant.dto.MerchantRegisterRequest;
import com.merchantharmony.auth.merchant.dto.MerchantRegisterResponse;
import com.merchantharmony.common.domain.MerchantCategory;
import com.merchantharmony.common.domain.MerchantStatus;
import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final EngagementServiceClient engagementServiceClient;

    @Transactional
    public MerchantRegisterResponse register(MerchantRegisterRequest request) {
        if (merchantRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new MerchantHarmonyException(ErrorCode.DUPLICATE_RESOURCE,
                    "A merchant with this phone number already exists");
        }

        MerchantCategory category;
        try {
            category = MerchantCategory.valueOf(request.category().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MerchantHarmonyException(ErrorCode.VALIDATION_ERROR,
                    "Invalid category: " + request.category() + ". Must be one of: RESTAURANT, GROCERY, SALON, RETAIL, OTHER");
        }

        Instant now = Instant.now();
        String qrCode = UUID.randomUUID().toString();

        Merchant merchant = new Merchant();
        merchant.setBusinessName(request.businessName());
        merchant.setDisplayName(request.displayName());
        merchant.setPhoneNumber(request.phoneNumber());
        merchant.setEmail(request.email());
        merchant.setWebsite(request.website());
        merchant.setCategory(category);
        merchant.setStatus(MerchantStatus.ACTIVE);
        merchant.setAddressLine1(request.addressLine1());
        merchant.setAddressLine2(request.addressLine2());
        merchant.setCity(request.city());
        merchant.setStateProvince(request.stateProvince());
        merchant.setPostalCode(request.postalCode());
        merchant.setCountry(request.country() != null ? request.country() : "US");
        merchant.setQrCode(qrCode);
        merchant.setQrCodeGeneratedAt(now);

        Merchant saved = merchantRepository.save(merchant);

        log.info("Merchant registered: merchantId={}, category={}", saved.getMerchantId(), category);

        engagementServiceClient.initializeMerchantTopics(saved.getMerchantId(), category.name());

        return new MerchantRegisterResponse(
                saved.getMerchantId(),
                saved.getBusinessName(),
                saved.getStatus().name(),
                saved.getQrCode()
        );
    }

    @Transactional(readOnly = true)
    public Merchant getByQrCode(String qrCode) {
        return merchantRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND,
                        "No merchant found for this QR code"));
    }

    @Transactional(readOnly = true)
    public Merchant getByPhoneNumber(String phoneNumber) {
        return merchantRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND,
                        "No merchant found with this phone number"));
    }

    @Transactional(readOnly = true)
    public Merchant getById(UUID merchantId) {
        return merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND,
                        "Merchant not found"));
    }
}
