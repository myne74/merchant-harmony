package com.merchantharmony.notification.sms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SmsRequest(
        @NotBlank @Pattern(regexp = "^\\+[1-9]\\d{7,14}$", message = "must be a valid E.164 phone number") String to,
        @NotBlank String message
) {}
