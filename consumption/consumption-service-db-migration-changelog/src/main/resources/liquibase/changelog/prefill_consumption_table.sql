BEGIN;

-- =======================
-- Alice: Estonia/Tallinn
-- =======================
INSERT INTO consumption (
    id,
    metering_point_id,
    customer_id,
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
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 'edb24e51-1c16-4df9-8ba5-845435cf66d7', 100.0, 'kWH', '2024-01-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10','edb24e51-1c16-4df9-8ba5-845435cf66d7', 110.0, 'kWH', '2024-02-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',120.0, 'kWH', '2024-03-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',130.0, 'kWH', '2024-04-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',125.0, 'kWH', '2024-05-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10','edb24e51-1c16-4df9-8ba5-845435cf66d7', 115.0, 'kWH', '2024-06-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10','edb24e51-1c16-4df9-8ba5-845435cf66d7', 140.0, 'kWH', '2024-07-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10','edb24e51-1c16-4df9-8ba5-845435cf66d7', 150.0, 'kWH', '2024-08-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',130.0, 'kWH', '2024-09-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10','edb24e51-1c16-4df9-8ba5-845435cf66d7', 120.5, 'kWH', '2024-10-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10','edb24e51-1c16-4df9-8ba5-845435cf66d7', 115.3, 'kWH', '2024-11-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'e2d51caa-fb3a-4c80-a7e3-7350421aea10', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',110.7, 'kWH', '2024-12-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0);

-- =======================
-- Alice: Estonia/Tartu
-- =======================
INSERT INTO consumption (
    id,
    metering_point_id,
    customer_id,
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
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38','edb24e51-1c16-4df9-8ba5-845435cf66d7', 90.0,  'kWH', '2024-01-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',95.0,  'kWH', '2024-02-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38','edb24e51-1c16-4df9-8ba5-845435cf66d7', 100.0, 'kWH', '2024-03-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',110.0, 'kWH', '2024-04-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',120.0, 'kWH', '2024-05-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',130.0, 'kWH', '2024-06-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38','edb24e51-1c16-4df9-8ba5-845435cf66d7', 125.0, 'kWH', '2024-07-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',140.0, 'kWH', '2024-08-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',135.0, 'kWH', '2024-09-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38','edb24e51-1c16-4df9-8ba5-845435cf66d7', 125.0, 'kWH', '2024-10-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38', 'edb24e51-1c16-4df9-8ba5-845435cf66d7',115.0, 'kWH', '2024-11-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0),
    (gen_random_uuid(), 'a790caaa-beef-4a1f-8ee3-273f94f82c38','edb24e51-1c16-4df9-8ba5-845435cf66d7', 110.0, 'kWH', '2024-12-15T00:00:00Z', now(), 'alice@example.com', now(), 'alice@example.com', 0);

-- ==================
-- Bob: Estonia/Parnu
-- ==================
INSERT INTO consumption (
    id,
    metering_point_id,
    customer_id,
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
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',  60.0, 'kWH', '2024-01-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4', 65.0, 'kWH', '2024-02-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e','d0d3d547-1c9b-4df9-9153-c2ef451a20d4',  70.0, 'kWH', '2024-03-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4', 80.0, 'kWH', '2024-04-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e','d0d3d547-1c9b-4df9-9153-c2ef451a20d4',  90.0, 'kWH', '2024-05-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e','d0d3d547-1c9b-4df9-9153-c2ef451a20d4', 95.0, 'kWH', '2024-06-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',100.0, 'kWH', '2024-07-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',105.0, 'kWH', '2024-08-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',110.0, 'kWH', '2024-09-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',120.0, 'kWH', '2024-10-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',130.0, 'kWH', '2024-11-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0),
    (gen_random_uuid(), 'f19dc54d-bb61-4b41-8402-9a74eb83c12e', 'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',135.0, 'kWH', '2024-12-15T00:00:00Z', now(), 'bob@example.com', now(), 'bob@example.com', 0);

-- ==================
-- Carol: Estonia/Saarema
-- ==================
INSERT INTO consumption (
    id,
    metering_point_id,
    customer_id,
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
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',  55.0, 'kWH', '2024-01-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',  60.0, 'kWH', '2024-02-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2', 65.0, 'kWH', '2024-03-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2', 75.0, 'kWH', '2024-04-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2', 80.0, 'kWH', '2024-05-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2', 88.0, 'kWH', '2024-06-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2', 90.0, 'kWH', '2024-07-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2', 95.0, 'kWH', '2024-08-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',100.0, 'kWH', '2024-09-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',105.0, 'kWH', '2024-10-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',110.0, 'kWH', '2024-11-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0),
    (gen_random_uuid(), 'df39bcf7-20ad-40dc-8671-72ab8b13e312', '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',115.0, 'kWH', '2024-12-15T00:00:00Z', now(), 'carol@example.com', now(), 'carol@example.com', 0);

-- =====================
-- David: Estonia/Rakvere
-- =====================
INSERT INTO consumption (
    id,
    metering_point_id,
    customer_id,
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
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582',  40.0, 'kWH', '2024-01-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582',  45.0, 'kWH', '2024-02-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 48.0, 'kWH', '2024-03-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 50.0, 'kWH', '2024-04-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 55.0, 'kWH', '2024-05-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 58.0, 'kWH', '2024-06-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 62.0, 'kWH', '2024-07-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 65.0, 'kWH', '2024-08-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56','b30e6bd3-b301-4ae0-80b9-808f64ecf582',  70.0, 'kWH', '2024-09-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 75.0, 'kWH', '2024-10-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 80.0, 'kWH', '2024-11-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0),
    (gen_random_uuid(), '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56', 'b30e6bd3-b301-4ae0-80b9-808f64ecf582', 85.0, 'kWH', '2024-12-15T00:00:00Z', now(), 'david@example.com', now(), 'david@example.com', 0);

COMMIT;