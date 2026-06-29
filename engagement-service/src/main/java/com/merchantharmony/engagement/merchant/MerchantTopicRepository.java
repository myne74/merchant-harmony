package com.merchantharmony.engagement.merchant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantTopicRepository extends JpaRepository<MerchantTopic, UUID> {

    @Query("SELECT mt FROM MerchantTopic mt JOIN FETCH mt.topic t WHERE mt.merchantId = :merchantId ORDER BY t.displayOrder ASC")
    List<MerchantTopic> findByMerchantIdWithTopic(@Param("merchantId") UUID merchantId);

    @Query("SELECT mt FROM MerchantTopic mt JOIN FETCH mt.topic t WHERE mt.merchantId = :merchantId AND mt.active = true ORDER BY t.displayOrder ASC")
    List<MerchantTopic> findActiveByMerchantIdWithTopic(@Param("merchantId") UUID merchantId);

    Optional<MerchantTopic> findByMerchantTopicIdAndMerchantId(UUID merchantTopicId, UUID merchantId);

    long countByMerchantIdAndActive(UUID merchantId, boolean active);

    boolean existsByMerchantId(UUID merchantId);
}
