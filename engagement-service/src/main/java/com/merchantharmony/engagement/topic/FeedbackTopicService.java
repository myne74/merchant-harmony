package com.merchantharmony.engagement.topic;

import com.merchantharmony.common.domain.MerchantCategory;
import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import com.merchantharmony.engagement.merchant.MerchantTopic;
import com.merchantharmony.engagement.merchant.MerchantTopicRepository;
import com.merchantharmony.engagement.topic.dto.FeedbackTopicMasterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackTopicService {

    private final FeedbackTopicMasterRepository feedbackTopicMasterRepository;
    private final MerchantTopicRepository merchantTopicRepository;

    @Transactional(readOnly = true)
    public List<FeedbackTopicMasterResponse> getMasterTopics(String categoryStr) {
        MerchantCategory category;
        try {
            category = MerchantCategory.valueOf(categoryStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MerchantHarmonyException(ErrorCode.VALIDATION_ERROR,
                    "Invalid category: " + categoryStr);
        }
        return feedbackTopicMasterRepository
                .findByMerchantCategoryAndActiveOrderByDisplayOrderAsc(category, true)
                .stream()
                .map(t -> new FeedbackTopicMasterResponse(
                        t.getTopicId(),
                        t.getMerchantCategory().name(),
                        t.getName(),
                        t.getType().name(),
                        t.getDisplayOrder(),
                        t.isActive()))
                .toList();
    }

    @Transactional
    public void initializeMerchantTopics(UUID merchantId, String categoryStr) {
        if (merchantTopicRepository.existsByMerchantId(merchantId)) {
            log.info("Topics already initialized for merchantId={}, skipping", merchantId);
            return;
        }

        MerchantCategory category;
        try {
            category = MerchantCategory.valueOf(categoryStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MerchantHarmonyException(ErrorCode.VALIDATION_ERROR,
                    "Invalid category: " + categoryStr);
        }

        List<FeedbackTopicMaster> masterTopics =
                feedbackTopicMasterRepository.findByMerchantCategoryAndActiveOrderByDisplayOrderAsc(category, true);

        List<MerchantTopic> merchantTopics = masterTopics.stream()
                .map(master -> {
                    MerchantTopic mt = new MerchantTopic();
                    mt.setMerchantId(merchantId);
                    mt.setTopic(master);
                    mt.setActive(true);
                    return mt;
                })
                .toList();

        merchantTopicRepository.saveAll(merchantTopics);
        log.info("Initialized {} topics for merchantId={}, category={}", merchantTopics.size(), merchantId, category);
    }
}
