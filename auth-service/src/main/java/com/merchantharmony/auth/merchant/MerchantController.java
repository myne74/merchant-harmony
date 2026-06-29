package com.merchantharmony.auth.merchant;

import com.merchantharmony.auth.merchant.dto.MerchantRegisterRequest;
import com.merchantharmony.auth.merchant.dto.MerchantRegisterResponse;
import com.merchantharmony.auth.otp.OtpService;
import com.merchantharmony.auth.otp.OtpUserType;
import com.merchantharmony.auth.otp.dto.LoginRequest;
import com.merchantharmony.auth.otp.dto.LoginResponse;
import com.merchantharmony.auth.otp.dto.OtpVerifyRequest;
import com.merchantharmony.auth.otp.dto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    private final OtpService otpService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MerchantRegisterResponse register(@Valid @RequestBody MerchantRegisterRequest request) {
        return merchantService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Merchant merchant = merchantService.getByPhoneNumber(request.phoneNumber());
        return otpService.initiateLogin(merchant.getMerchantId(), OtpUserType.MERCHANT);
    }

    @PostMapping("/verify-otp")
    public TokenResponse verifyOtp(@Valid @RequestBody OtpVerifyRequest request) {
        return otpService.verifyOtp(request.otpRequestId(), request.otp(), OtpUserType.MERCHANT);
    }
}
