package com.merchantharmony.engagement.feedback;

import com.merchantharmony.common.security.UserPrincipal;
import com.merchantharmony.engagement.feedback.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feedback/threads")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateFeedbackThreadResponse createThread(@Valid @RequestBody CreateFeedbackThreadRequest request,
                                                      @AuthenticationPrincipal UserPrincipal principal) {
        return feedbackService.createThread(UUID.fromString(principal.userId()), request);
    }

    @GetMapping("/customer")
    public List<FeedbackThreadSummaryResponse> getCustomerThreads(@AuthenticationPrincipal UserPrincipal principal) {
        return feedbackService.getCustomerThreads(UUID.fromString(principal.userId()));
    }

    @GetMapping("/merchant")
    public List<FeedbackThreadSummaryResponse> getMerchantThreads(@AuthenticationPrincipal UserPrincipal principal) {
        return feedbackService.getMerchantThreads(UUID.fromString(principal.userId()));
    }

    @GetMapping("/{threadId}")
    public FeedbackThreadDetailResponse getThread(@PathVariable UUID threadId,
                                                   @AuthenticationPrincipal UserPrincipal principal) {
        return feedbackService.getThread(threadId, UUID.fromString(principal.userId()), principal.role());
    }

    @PostMapping("/{threadId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public AddCommentResponse addComment(@PathVariable UUID threadId,
                                          @Valid @RequestBody AddCommentRequest request,
                                          @AuthenticationPrincipal UserPrincipal principal) {
        return feedbackService.addComment(threadId, UUID.fromString(principal.userId()), principal.role(), request);
    }

    @PatchMapping("/{threadId}/close")
    public CloseThreadResponse closeThread(@PathVariable UUID threadId,
                                            @AuthenticationPrincipal UserPrincipal principal) {
        return feedbackService.closeThread(threadId, UUID.fromString(principal.userId()));
    }
}
