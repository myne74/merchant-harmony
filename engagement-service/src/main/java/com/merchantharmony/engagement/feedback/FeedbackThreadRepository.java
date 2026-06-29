package com.merchantharmony.engagement.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FeedbackThreadRepository extends JpaRepository<FeedbackThread, UUID> {

    @Query("SELECT t FROM FeedbackThread t JOIN FETCH t.merchantTopic mt JOIN FETCH mt.topic WHERE t.customerId = :customerId ORDER BY t.createdAt DESC")
    List<FeedbackThread> findByCustomerIdWithTopic(@Param("customerId") UUID customerId);

    @Query("SELECT t FROM FeedbackThread t JOIN FETCH t.merchantTopic mt JOIN FETCH mt.topic WHERE t.merchantId = :merchantId ORDER BY t.createdAt DESC")
    List<FeedbackThread> findByMerchantIdWithTopic(@Param("merchantId") UUID merchantId);
}
