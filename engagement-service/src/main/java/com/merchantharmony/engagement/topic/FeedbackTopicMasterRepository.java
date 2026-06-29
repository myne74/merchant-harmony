package com.merchantharmony.engagement.topic;

import com.merchantharmony.common.domain.MerchantCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedbackTopicMasterRepository extends JpaRepository<FeedbackTopicMaster, UUID> {

    List<FeedbackTopicMaster> findByMerchantCategoryAndActiveOrderByDisplayOrderAsc(
            MerchantCategory category, boolean active);
}
