CREATE TABLE merchant_customer (
    association_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    merchant_id    UUID NOT NULL,
    customer_id    UUID NOT NULL,
    associated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX idx_merchant_customer_unique   ON merchant_customer (merchant_id, customer_id);
CREATE INDEX        idx_merchant_customer_merchant ON merchant_customer (merchant_id);
CREATE INDEX        idx_merchant_customer_customer ON merchant_customer (customer_id);
