CREATE TABLE otp_request (
    otp_request_id UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id        UUID        NOT NULL,
    user_type      VARCHAR(20) NOT NULL,
    otp_code       VARCHAR(6)  NOT NULL,
    expires_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    verified       BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_otp_request_user       ON otp_request (user_id, user_type);
CREATE INDEX idx_otp_request_expires_at ON otp_request (expires_at);
