CREATE TABLE comment (
    comment_id  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    thread_id   UUID        NOT NULL,
    author_type VARCHAR(20) NOT NULL,
    message     TEXT        NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_comment_thread FOREIGN KEY (thread_id) REFERENCES feedback_thread (thread_id)
);

CREATE INDEX idx_comment_thread      ON comment (thread_id);
CREATE INDEX idx_comment_thread_time ON comment (thread_id, created_at);
