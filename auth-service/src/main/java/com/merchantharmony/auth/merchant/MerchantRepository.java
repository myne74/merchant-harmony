package com.merchantharmony.auth.merchant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    Optional<Merchant> findByPhoneNumber(String phoneNumber);

    Optional<Merchant> findByQrCode(String qrCode);

    boolean existsByPhoneNumber(String phoneNumber);
}
