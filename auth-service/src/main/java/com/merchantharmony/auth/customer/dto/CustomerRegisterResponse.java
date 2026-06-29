package com.merchantharmony.auth.customer.dto;

import java.util.UUID;

public record CustomerRegisterResponse(
        UUID customerId,
        String name
) {}
