package com.merchantharmony.engagement.feedback;

import com.merchantharmony.engagement.merchant.MerchantTopic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "feedback_thread")
@Getter
@Setter
@NoArgsConstructor
public class FeedbackThread {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "thread_id")
    private UUID threadId;

    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_topic_id", nullable = false)
    private MerchantTopic merchantTopic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThreadStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    @PrePersist
    private void prePersist() {
        createdAt = Instant.now();
        status = ThreadStatus.OPEN;
    }
}
