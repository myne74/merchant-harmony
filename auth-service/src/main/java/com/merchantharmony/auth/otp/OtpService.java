package com.merchantharmony.auth.otp;

import com.merchantharmony.auth.config.NotificationServiceClient;
import com.merchantharmony.auth.otp.dto.LoginResponse;
import com.merchantharmony.auth.otp.dto.TokenResponse;
import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import com.merchantharmony.common.security.JwtService;
import com.merchantharmony.common.security.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private static final long OTP_EXPIRY_SECONDS = 300L;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final OtpRequestRepository otpRequestRepository;
    private final JwtService jwtService;
    private final NotificationServiceClient notificationServiceClient;

    @Transactional
    public LoginResponse initiateLogin(UUID userId, OtpUserType userType, String phoneNumber) {
        String otpCode = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));

        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setUserId(userId);
        otpRequest.setUserType(userType);
        otpRequest.setOtpCode(otpCode);
        otpRequest.setExpiresAt(Instant.now().plusSeconds(OTP_EXPIRY_SECONDS));
        otpRequest.setVerified(false);

        OtpRequest saved = otpRequestRepository.save(otpRequest);

        notificationServiceClient.sendSms(phoneNumber,
                "Your Merchant Harmony OTP is " + otpCode + ". Valid for 5 minutes.");

        return new LoginResponse(saved.getOtpRequestId(), "OTP sent successfully");
    }

    @Transactional
    public TokenResponse verifyOtp(UUID otpRequestId, String otp, OtpUserType expectedUserType) {
        OtpRequest otpRequest = otpRequestRepository.findById(otpRequestId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.INVALID_OTP,
                        "Invalid OTP request"));

        if (otpRequest.getUserType() != expectedUserType) {
            throw new MerchantHarmonyException(ErrorCode.INVALID_OTP, "Invalid OTP request");
        }

        if (otpRequest.isVerified()) {
            throw new MerchantHarmonyException(ErrorCode.INVALID_OTP, "OTP has already been used");
        }

        if (Instant.now().isAfter(otpRequest.getExpiresAt())) {
            throw new MerchantHarmonyException(ErrorCode.OTP_EXPIRED, "OTP has expired");
        }

        if (!otpRequest.getOtpCode().equals(otp)) {
            throw new MerchantHarmonyException(ErrorCode.INVALID_OTP, "Invalid OTP");
        }

        otpRequest.setVerified(true);
        otpRequestRepository.save(otpRequest);

        UserRole role = expectedUserType == OtpUserType.MERCHANT ? UserRole.MERCHANT : UserRole.CUSTOMER;
        String token = jwtService.generateToken(otpRequest.getUserId().toString(), role);

        log.info("OTP verified, JWT issued: userId={}, userType={}", otpRequest.getUserId(), expectedUserType);

        return new TokenResponse(token, "Bearer");
    }
}
