package com.merchantharmony.engagement.topic;

import com.merchantharmony.engagement.topic.dto.FeedbackTopicMasterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback-topic-master")
@RequiredArgsConstructor
public class FeedbackTopicController {

    private final FeedbackTopicService feedbackTopicService;

    @GetMapping
    public List<FeedbackTopicMasterResponse> getMasterTopics(@RequestParam String category) {
        return feedbackTopicService.getMasterTopics(category);
    }
}
