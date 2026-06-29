package com.merchantharmony.engagement.customer;

import com.merchantharmony.common.security.UserPrincipal;
import com.merchantharmony.engagement.customer.dto.MerchantLandingRequest;
import com.merchantharmony.engagement.customer.dto.MerchantLandingResponse;
import com.merchantharmony.engagement.customer.dto.MerchantSummaryResponse;
import com.merchantharmony.engagement.internal.dto.AuthCustomerProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/me")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerEngagementService customerEngagementService;

    @GetMapping
    public AuthCustomerProfileResponse getProfile(@AuthenticationPrincipal UserPrincipal principal,
                                                   HttpServletRequest request) {
        return customerEngagementService.getCustomerProfile(
                UUID.fromString(principal.userId()), request.getHeader("Authorization"));
    }

    @PostMapping("/merchant-landing")
    public MerchantLandingResponse merchantLanding(@Valid @RequestBody MerchantLandingRequest landingRequest,
                                                    @AuthenticationPrincipal UserPrincipal principal,
                                                    HttpServletRequest request) {
        return customerEngagementService.merchantLanding(
                UUID.fromString(principal.userId()),
                landingRequest.merchantQrCode(),
                request.getHeader("Authorization"));
    }

    @GetMapping("/merchants")
    public List<MerchantSummaryResponse> getAssociatedMerchants(@AuthenticationPrincipal UserPrincipal principal,
                                                                  HttpServletRequest request) {
        return customerEngagementService.getAssociatedMerchants(
                UUID.fromString(principal.userId()), request.getHeader("Authorization"));
    }
}
