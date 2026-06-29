package com.merchantharmony.engagement.merchant;

import com.merchantharmony.engagement.topic.FeedbackTopicMaster;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "merchant_topic")
@Getter
@Setter
@NoArgsConstructor
public class MerchantTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "merchant_topic_id")
    private UUID merchantTopicId;

    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private FeedbackTopicMaster topic;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = Instant.now();
    }
}
