package com.merchantharmony.auth.internal;

import com.merchantharmony.auth.internal.dto.MerchantProfileResponse;
import com.merchantharmony.auth.merchant.Merchant;
import com.merchantharmony.auth.merchant.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internal/merchants")
@RequiredArgsConstructor
public class InternalMerchantController {

    private final MerchantService merchantService;

    @GetMapping("/{merchantId}")
    public MerchantProfileResponse getMerchantProfile(@PathVariable UUID merchantId) {
        return toResponse(merchantService.getById(merchantId));
    }

    @GetMapping("/by-qr/{qrCode}")
    public MerchantProfileResponse getMerchantByQrCode(@PathVariable String qrCode) {
        return toResponse(merchantService.getByQrCode(qrCode));
    }

    private MerchantProfileResponse toResponse(Merchant merchant) {
        return new MerchantProfileResponse(
                merchant.getMerchantId(),
                merchant.getBusinessName(),
                merchant.getDisplayName(),
                merchant.getPhoneNumber(),
                merchant.getCategory().name(),
                merchant.getStatus().name(),
                merchant.getQrCode()
        );
    }
}
