BEGIN;

-- =======================
-- Alice: Estonia/Tallinn
-- =======================
INSERT INTO consumption (
    id,
    metering_point_id,
    amount,
    amount_unit,
    consumption_time,
    created_at,
    created_by,
    updated_at,
    updated_by,
    version
)
VALUES
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 100.0, 'KWH', '2024-01-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 110.0, 'KWH', '2024-02-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 120.0, 'KWH', '2024-03-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 130.0, 'KWH', '2024-04-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 125.0, 'KWH', '2024-05-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 115.0, 'KWH', '2024-06-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 140.0, 'KWH', '2024-07-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 150.0, 'KWH', '2024-08-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 130.0, 'KWH', '2024-09-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 120.5, 'KWH', '2024-10-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 115.3, 'KWH', '2024-11-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 110.7, 'KWH', '2024-12-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0);

-- =======================
-- Alice: Estonia/Tartu
-- =======================
INSERT INTO consumption (
    id,
    metering_point_id,
    amount,
    amount_unit,
    consumption_time,
    created_at,
    created_by,
    updated_at,
    updated_by,
    version
)
VALUES
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 90.0,  'KWH', '2024-01-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 95.0,  'KWH', '2024-02-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 100.0, 'KWH', '2024-03-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 110.0, 'KWH', '2024-04-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 120.0, 'KWH', '2024-05-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 130.0, 'KWH', '2024-06-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 125.0, 'KWH', '2024-07-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 140.0, 'KWH', '2024-08-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 135.0, 'KWH', '2024-09-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 125.0, 'KWH', '2024-10-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 115.0, 'KWH', '2024-11-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 110.0, 'KWH', '2024-12-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0);

-- ==================
-- Bob: Estonia/Parnu
-- ==================
INSERT INTO consumption (
    id,
    metering_point_id,
    amount,
    amount_unit,
    consumption_time,
    created_at,
    created_by,
    updated_at,
    updated_by,
    version
)
VALUES
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e',  60.0, 'KWH', '2024-01-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e',  65.0, 'KWH', '2024-02-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e',  70.0, 'KWH', '2024-03-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e',  80.0, 'KWH', '2024-04-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e',  90.0, 'KWH', '2024-05-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e',  95.0, 'KWH', '2024-06-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 100.0, 'KWH', '2024-07-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 105.0, 'KWH', '2024-08-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 110.0, 'KWH', '2024-09-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 120.0, 'KWH', '2024-10-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 130.0, 'KWH', '2024-11-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 135.0, 'KWH', '2024-12-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0);

-- ==================
-- Carol: Estonia/Saarema
-- ==================
INSERT INTO consumption (
    id,
    metering_point_id,
    amount,
    amount_unit,
    consumption_time,
    created_at,
    created_by,
    updated_at,
    updated_by,
    version
)
VALUES
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  55.0, 'KWH', '2024-01-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  60.0, 'KWH', '2024-02-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  65.0, 'KWH', '2024-03-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  75.0, 'KWH', '2024-04-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  80.0, 'KWH', '2024-05-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  88.0, 'KWH', '2024-06-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  90.0, 'KWH', '2024-07-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312',  95.0, 'KWH', '2024-08-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', 100.0, 'KWH', '2024-09-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', 105.0, 'KWH', '2024-10-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', 110.0, 'KWH', '2024-11-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', 115.0, 'KWH', '2024-12-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0);

-- =====================
-- David: Estonia/Rakvere
-- =====================
INSERT INTO consumption (
    id,
    metering_point_id,
    amount,
    amount_unit,
    consumption_time,
    created_at,
    created_by,
    updated_at,
    updated_by,
    version
)
VALUES
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  40.0, 'KWH', '2024-01-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  45.0, 'KWH', '2024-02-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  48.0, 'KWH', '2024-03-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  50.0, 'KWH', '2024-04-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  55.0, 'KWH', '2024-05-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  58.0, 'KWH', '2024-06-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  62.0, 'KWH', '2024-07-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  65.0, 'KWH', '2024-08-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  70.0, 'KWH', '2024-09-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  75.0, 'KWH', '2024-10-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  80.0, 'KWH', '2024-11-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',  85.0, 'KWH', '2024-12-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0);

COMMIT;