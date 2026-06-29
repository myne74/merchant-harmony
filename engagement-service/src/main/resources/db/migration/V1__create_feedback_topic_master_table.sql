CREATE TABLE feedback_topic_master (
    topic_id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    merchant_category VARCHAR(50)  NOT NULL,
    name              VARCHAR(100) NOT NULL,
    type              VARCHAR(50)  NOT NULL,
    display_order     INTEGER      NOT NULL,
    active            BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ftm_category_active ON feedback_topic_master (merchant_category, active);
CREATE INDEX idx_ftm_display_order   ON feedback_topic_master (merchant_category, display_order);
