CREATE TABLE merchant (
    merchant_id            UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    business_name          VARCHAR(255) NOT NULL,
    display_name           VARCHAR(255),
    phone_number           VARCHAR(20)  NOT NULL,
    email                  VARCHAR(255),
    website                VARCHAR(500),
    category               VARCHAR(50)  NOT NULL,
    status                 VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    address_line1          VARCHAR(255) NOT NULL,
    address_line2          VARCHAR(255),
    city                   VARCHAR(100) NOT NULL,
    state_province         VARCHAR(100) NOT NULL,
    postal_code            VARCHAR(20)  NOT NULL,
    country                VARCHAR(10)  NOT NULL DEFAULT 'US',
    qr_code                VARCHAR(255) NOT NULL,
    qr_code_generated_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at             TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX idx_merchant_phone_number ON merchant (phone_number);
CREATE UNIQUE INDEX idx_merchant_qr_code      ON merchant (qr_code);
CREATE INDEX        idx_merchant_category      ON merchant (category);
CREATE INDEX        idx_merchant_status        ON merchant (status);
