package com.merchantharmony.engagement.feedback;

import com.merchantharmony.common.exception.ErrorCode;
import com.merchantharmony.common.exception.MerchantHarmonyException;
import com.merchantharmony.common.security.UserRole;
import com.merchantharmony.engagement.customer.MerchantCustomerRepository;
import com.merchantharmony.engagement.feedback.dto.*;
import com.merchantharmony.engagement.merchant.MerchantTopic;
import com.merchantharmony.engagement.merchant.MerchantTopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private final FeedbackThreadRepository feedbackThreadRepository;
    private final CommentRepository commentRepository;
    private final MerchantTopicRepository merchantTopicRepository;
    private final MerchantCustomerRepository merchantCustomerRepository;

    @Transactional
    public CreateFeedbackThreadResponse createThread(UUID customerId, CreateFeedbackThreadRequest request) {
        MerchantTopic merchantTopic = merchantTopicRepository.findById(request.merchantTopicId())
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND, "Merchant topic not found"));

        if (!merchantTopic.isActive()) {
            throw new MerchantHarmonyException(ErrorCode.TOPIC_NOT_ACTIVE, "This feedback topic is not active");
        }

        UUID merchantId = merchantTopic.getMerchantId();

        merchantCustomerRepository.findByMerchantIdAndCustomerId(merchantId, customerId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.BUSINESS_RULE_VIOLATION,
                        "You are not associated with this merchant"));

        FeedbackThread thread = new FeedbackThread();
        thread.setMerchantId(merchantId);
        thread.setCustomerId(customerId);
        thread.setMerchantTopic(merchantTopic);
        FeedbackThread savedThread = feedbackThreadRepository.save(thread);

        Comment comment = new Comment();
        comment.setThread(savedThread);
        comment.setAuthorType(CommentAuthorType.CUSTOMER);
        comment.setMessage(request.message());
        commentRepository.save(comment);

        return new CreateFeedbackThreadResponse(savedThread.getThreadId(), savedThread.getStatus().name());
    }

    @Transactional(readOnly = true)
    public List<FeedbackThreadSummaryResponse> getCustomerThreads(UUID customerId) {
        return feedbackThreadRepository.findByCustomerIdWithTopic(customerId)
                .stream()
                .map(t -> new FeedbackThreadSummaryResponse(
                        t.getThreadId(),
                        t.getMerchantTopic().getMerchantTopicId(),
                        t.getMerchantTopic().getTopic().getName(),
                        t.getStatus().name(),
                        t.getCreatedAt()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FeedbackThreadSummaryResponse> getMerchantThreads(UUID merchantId) {
        return feedbackThreadRepository.findByMerchantIdWithTopic(merchantId)
                .stream()
                .map(t -> new FeedbackThreadSummaryResponse(
                        t.getThreadId(),
                        t.getMerchantTopic().getMerchantTopicId(),
                        t.getMerchantTopic().getTopic().getName(),
                        t.getStatus().name(),
                        t.getCreatedAt()))
                .toList();
    }

    @Transactional(readOnly = true)
    public FeedbackThreadDetailResponse getThread(UUID threadId, UUID userId, UserRole role) {
        FeedbackThread thread = feedbackThreadRepository.findById(threadId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND, "Thread not found"));

        assertOwnership(thread, userId, role);

        List<CommentResponse> comments = commentRepository.findByThread_ThreadIdOrderByCreatedAtAsc(threadId)
                .stream()
                .map(c -> new CommentResponse(c.getCommentId(), c.getAuthorType().name(),
                        c.getMessage(), c.getCreatedAt()))
                .toList();

        return new FeedbackThreadDetailResponse(
                thread.getThreadId(),
                thread.getMerchantId(),
                thread.getCustomerId(),
                thread.getMerchantTopic().getMerchantTopicId(),
                thread.getMerchantTopic().getTopic().getName(),
                thread.getStatus().name(),
                thread.getCreatedAt(),
                thread.getClosedAt(),
                comments);
    }

    @Transactional
    public AddCommentResponse addComment(UUID threadId, UUID userId, UserRole role, AddCommentRequest request) {
        FeedbackThread thread = feedbackThreadRepository.findById(threadId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND, "Thread not found"));

        if (thread.getStatus() == ThreadStatus.CLOSED) {
            throw new MerchantHarmonyException(ErrorCode.THREAD_CLOSED, "This thread is closed");
        }

        assertOwnership(thread, userId, role);

        Comment comment = new Comment();
        comment.setThread(thread);
        comment.setAuthorType(role == UserRole.MERCHANT ? CommentAuthorType.MERCHANT : CommentAuthorType.CUSTOMER);
        comment.setMessage(request.message());
        Comment saved = commentRepository.save(comment);

        return new AddCommentResponse(saved.getCommentId());
    }

    @Transactional
    public CloseThreadResponse closeThread(UUID threadId, UUID merchantId) {
        FeedbackThread thread = feedbackThreadRepository.findById(threadId)
                .orElseThrow(() -> new MerchantHarmonyException(ErrorCode.NOT_FOUND, "Thread not found"));

        if (!thread.getMerchantId().equals(merchantId)) {
            throw new MerchantHarmonyException(ErrorCode.FORBIDDEN, "You do not own this thread");
        }

        thread.setStatus(ThreadStatus.CLOSED);
        thread.setClosedAt(Instant.now());
        feedbackThreadRepository.save(thread);

        return new CloseThreadResponse(thread.getThreadId(), thread.getStatus().name());
    }

    private void assertOwnership(FeedbackThread thread, UUID userId, UserRole role) {
        boolean owns = role == UserRole.MERCHANT
                ? thread.getMerchantId().equals(userId)
                : thread.getCustomerId().equals(userId);
        if (!owns) {
            throw new MerchantHarmonyException(ErrorCode.FORBIDDEN, "You do not have access to this thread");
        }
    }
}
