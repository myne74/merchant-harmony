package com.merchantharmony.engagement.merchant;

import com.merchantharmony.common.security.UserPrincipal;
import com.merchantharmony.engagement.internal.dto.AuthMerchantProfileResponse;
import com.merchantharmony.engagement.merchant.dto.MerchantCustomerResponse;
import com.merchantharmony.engagement.merchant.dto.MerchantTopicResponse;
import com.merchantharmony.engagement.merchant.dto.UpdateMerchantTopicRequest;
import com.merchantharmony.engagement.merchant.dto.UpdateMerchantTopicResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/merchants/me")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantEngagementService merchantEngagementService;

    @GetMapping
    public AuthMerchantProfileResponse getProfile(@AuthenticationPrincipal UserPrincipal principal,
                                                   HttpServletRequest request) {
        return merchantEngagementService.getMerchantProfile(
                UUID.fromString(principal.userId()), request.getHeader("Authorization"));
    }

    @GetMapping("/customers")
    public List<MerchantCustomerResponse> getCustomers(@AuthenticationPrincipal UserPrincipal principal,
                                                        HttpServletRequest request) {
        return merchantEngagementService.getMerchantCustomers(
                UUID.fromString(principal.userId()), request.getHeader("Authorization"));
    }

    @GetMapping("/feedback-topics")
    public List<MerchantTopicResponse> getFeedbackTopics(@AuthenticationPrincipal UserPrincipal principal) {
        return merchantEngagementService.getMerchantTopics(UUID.fromString(principal.userId()));
    }

    @PatchMapping("/feedback-topics/{merchantTopicId}")
    public UpdateMerchantTopicResponse updateTopicStatus(@PathVariable UUID merchantTopicId,
                                                          @Valid @RequestBody UpdateMerchantTopicRequest request,
                                                          @AuthenticationPrincipal UserPrincipal principal) {
        return merchantEngagementService.updateMerchantTopicStatus(
                merchantTopicId, UUID.fromString(principal.userId()), request);
    }
}
