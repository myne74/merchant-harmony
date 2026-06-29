package com.merchantharmony.engagement.internal;

import com.merchantharmony.engagement.topic.FeedbackTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class InternalTopicController {

    private final FeedbackTopicService feedbackTopicService;

    @PostMapping("/merchants/{merchantId}/topics/initialize")
    @ResponseStatus(HttpStatus.CREATED)
    public void initializeMerchantTopics(@PathVariable UUID merchantId,
                                          @RequestBody TopicInitRequest request) {
        feedbackTopicService.initializeMerchantTopics(merchantId, request.category());
    }

    private record TopicInitRequest(String category) {}
}
