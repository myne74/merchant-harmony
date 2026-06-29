package com.merchantharmony.auth.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRegisterRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 255)
        String name,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "\\+[1-9]\\d{7,14}", message = "Phone number must be in E.164 format, e.g. +14085551234")
        String phoneNumber,

        @Email(message = "Invalid email format")
        String email
) {}
