package com.merchantharmony.auth.merchant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MerchantRegisterRequest(

        @NotBlank(message = "Business name is required")
        @Size(max = 255)
        String businessName,

        @Size(max = 255)
        String displayName,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\+[1-9]\\d{7,14}", message = "Phone number must be in E.164 format, e.g. +14085550001")
        String phoneNumber,

        @Email(message = "Invalid email format")
        String email,

        @Size(max = 500)
        String website,

        @NotBlank(message = "Category is required")
        String category,

        @NotBlank(message = "Address line 1 is required")
        @Size(max = 255)
        String addressLine1,

        @Size(max = 255)
        String addressLine2,

        @NotBlank(message = "City is required")
        @Size(max = 100)
        String city,

        @NotBlank(message = "State/Province is required")
        @Size(max = 100)
        String stateProvince,

        @NotBlank(message = "Postal code is required")
        @Size(max = 20)
        String postalCode,

        @Size(max = 10)
        String country
) {}
