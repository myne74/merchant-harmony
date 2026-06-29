package com.merchantharmony.auth.customer;

import com.merchantharmony.auth.customer.dto.CustomerRegisterRequest;
import com.merchantharmony.auth.customer.dto.CustomerRegisterResponse;
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
@RequestMapping("/api/v1/auth/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final OtpService otpService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRegisterResponse register(@Valid @RequestBody CustomerRegisterRequest request) {
        return customerService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Customer customer = customerService.getByPhoneNumber(request.phoneNumber());
        return otpService.initiateLogin(customer.getCustomerId(), OtpUserType.CUSTOMER, customer.getPhoneNumber());
    }

    @PostMapping("/verify-otp")
    public TokenResponse verifyOtp(@Valid @RequestBody OtpVerifyRequest request) {
        return otpService.verifyOtp(request.otpRequestId(), request.otp(), OtpUserType.CUSTOMER);
    }
}
