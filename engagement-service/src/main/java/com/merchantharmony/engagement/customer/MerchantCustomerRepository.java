package com.merchantharmony.engagement.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantCustomerRepository extends JpaRepository<MerchantCustomer, UUID> {

    List<MerchantCustomer> findByMerchantId(UUID merchantId);

    List<MerchantCustomer> findByCustomerId(UUID customerId);

    Optional<MerchantCustomer> findByMerchantIdAndCustomerId(UUID merchantId, UUID customerId);
}
