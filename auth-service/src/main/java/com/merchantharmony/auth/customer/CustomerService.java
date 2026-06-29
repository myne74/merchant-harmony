package com.merchantharmony.auth.customer;

import com.merchantharmony.auth.customer.dto.CustomerRegisterRequest;
import com.merchantharmony.auth.customer.dto.CustomerRegisterResponse;
import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerRegisterResponse register(CustomerRegisterRequest request) {
        if (customerRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new MerchantHarmonyException(ErrorCode.DUPLICATE_RESOURCE,
                    "A customer with this phone number already exists");
        }

        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setEmail(request.email());

        Customer saved = customerRepository.save(customer);

        log.info("Customer registered: customerId={}", saved.getCustomerId());

        return new CustomerRegisterResponse(saved.getCustomerId(), saved.getName());
    }

    @Transactional(readOnly = true)
    public Customer getByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND,
                        "No customer found with this phone number"));
    }

    @Transactional(readOnly = true)
    public Customer getById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND,
                        "Customer not found"));
    }
}
