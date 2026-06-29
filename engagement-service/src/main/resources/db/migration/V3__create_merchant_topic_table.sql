CREATE TABLE merchant_topic (
    merchant_topic_id UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    merchant_id       UUID    NOT NULL,
    topic_id          UUID    NOT NULL,
    active            BOOLEAN NOT NULL DEFAULT TRUE,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_merchant_topic_topic FOREIGN KEY (topic_id) REFERENCES feedback_topic_master (topic_id)
);

CREATE INDEX        idx_merchant_topic_merchant        ON merchant_topic (merchant_id);
CREATE UNIQUE INDEX idx_merchant_topic_unique          ON merchant_topic (merchant_id, topic_id);
CREATE INDEX        idx_merchant_topic_merchant_active ON merchant_topic (merchant_id, active);
