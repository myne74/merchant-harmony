CREATE TABLE feedback_thread (
    thread_id         UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    merchant_id       UUID        NOT NULL,
    customer_id       UUID        NOT NULL,
    merchant_topic_id UUID        NOT NULL,
    status            VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    closed_at         TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_feedback_thread_topic FOREIGN KEY (merchant_topic_id) REFERENCES merchant_topic (merchant_topic_id)
);

CREATE INDEX idx_feedback_thread_merchant        ON feedback_thread (merchant_id);
CREATE INDEX idx_feedback_thread_customer        ON feedback_thread (customer_id);
CREATE INDEX idx_feedback_thread_merchant_status ON feedback_thread (merchant_id, status);
CREATE INDEX idx_feedback_thread_customer_status ON feedback_thread (customer_id, status);
