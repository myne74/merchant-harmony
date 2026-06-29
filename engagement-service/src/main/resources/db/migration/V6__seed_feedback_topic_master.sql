INSERT INTO feedback_topic_master (topic_id, merchant_category, name, type, display_order, active) VALUES
    (gen_random_uuid(), 'RESTAURANT', 'Food',             'PRODUCT_EXPERIENCE', 1, TRUE),
    (gen_random_uuid(), 'RESTAURANT', 'Service',          'SERVICE_EXPERIENCE', 2, TRUE),
    (gen_random_uuid(), 'RESTAURANT', 'Billing',          'PAYMENT',            3, TRUE),
    (gen_random_uuid(), 'RESTAURANT', 'Cleanliness',      'ENVIRONMENT',        4, TRUE),
    (gen_random_uuid(), 'RESTAURANT', 'General',          'GENERAL',            5, TRUE),

    (gen_random_uuid(), 'SALON',      'Haircut',          'PRODUCT_EXPERIENCE', 1, TRUE),
    (gen_random_uuid(), 'SALON',      'Staff',            'SERVICE_EXPERIENCE', 2, TRUE),
    (gen_random_uuid(), 'SALON',      'Appointment',      'SERVICE_EXPERIENCE', 3, TRUE),
    (gen_random_uuid(), 'SALON',      'Billing',          'PAYMENT',            4, TRUE),
    (gen_random_uuid(), 'SALON',      'General',          'GENERAL',            5, TRUE),

    (gen_random_uuid(), 'GROCERY',    'Product Quality',  'PRODUCT_EXPERIENCE', 1, TRUE),
    (gen_random_uuid(), 'GROCERY',    'Staff',            'SERVICE_EXPERIENCE', 2, TRUE),
    (gen_random_uuid(), 'GROCERY',    'Billing',          'PAYMENT',            3, TRUE),
    (gen_random_uuid(), 'GROCERY',    'Cleanliness',      'ENVIRONMENT',        4, TRUE),
    (gen_random_uuid(), 'GROCERY',    'General',          'GENERAL',            5, TRUE),

    (gen_random_uuid(), 'RETAIL',     'Product Quality',  'PRODUCT_EXPERIENCE', 1, TRUE),
    (gen_random_uuid(), 'RETAIL',     'Staff',            'SERVICE_EXPERIENCE', 2, TRUE),
    (gen_random_uuid(), 'RETAIL',     'Billing',          'PAYMENT',            3, TRUE),
    (gen_random_uuid(), 'RETAIL',     'Returns',          'SERVICE_EXPERIENCE', 4, TRUE),
    (gen_random_uuid(), 'RETAIL',     'General',          'GENERAL',            5, TRUE),

    (gen_random_uuid(), 'OTHER',      'Service',          'SERVICE_EXPERIENCE', 1, TRUE),
    (gen_random_uuid(), 'OTHER',      'Staff',            'SERVICE_EXPERIENCE', 2, TRUE),
    (gen_random_uuid(), 'OTHER',      'Billing',          'PAYMENT',            3, TRUE),
    (gen_random_uuid(), 'OTHER',      'General',          'GENERAL',            4, TRUE);
