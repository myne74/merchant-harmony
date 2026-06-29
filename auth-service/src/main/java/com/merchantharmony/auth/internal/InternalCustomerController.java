package com.merchantharmony.auth.internal;

import com.merchantharmony.auth.customer.Customer;
import com.merchantharmony.auth.customer.CustomerService;
import com.merchantharmony.auth.internal.dto.CustomerProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internal/customers")
@RequiredArgsConstructor
public class InternalCustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public CustomerProfileResponse getCustomerProfile(@PathVariable UUID customerId) {
        Customer customer = customerService.getById(customerId);
        return new CustomerProfileResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getPhoneNumber(),
                customer.getEmail()
        );
    }
}
