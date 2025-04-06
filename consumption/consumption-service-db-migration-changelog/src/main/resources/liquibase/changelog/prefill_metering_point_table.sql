INSERT INTO metering_point (
    id,
    address,
    customer_id,
    created_at,
    created_by,
    updated_at,
    updated_by,
    version
)
VALUES
    -- Alice has two metering points
    (
        'e2d51caa-fb3a-4c80-a7e3-7350421aea10',
        'Estonia/Tallinn',
        'edb24e51-1c16-4df9-8ba5-845435cf66d7',       -- Alice's UUID
        '2023-01-01 00:00:00',
        'alice@example.com',                          -- "created_by" = Alice's email
        '2023-01-01 00:00:00',
        'alice@example.com',                          -- "updated_by" = Alice's email
        0
    ),
    (
        'a790caaa-beef-4a1f-8ee3-273f94f82c38',
        'Estonia/Tartu',
        'edb24e51-1c16-4df9-8ba5-845435cf66d7',       -- Alice's UUID
        '2023-01-01 00:00:00',
        'alice@example.com',
        '2023-01-01 00:00:00',
        'alice@example.com',
        0
    ),

    -- Bob has one metering point
    (
        'f19dc54d-bb61-4b41-8402-9a74eb83c12e',
        'Estonia/Parnu',
        'd0d3d547-1c9b-4df9-9153-c2ef451a20d4',       -- Bob's UUID
        '2023-01-01 00:00:00',
        'bob@example.com',
        '2023-01-01 00:00:00',
        'bob@example.com',
        0
    ),

    -- Carol has one metering point
    (
        'df39bcf7-20ad-40dc-8671-72ab8b13e312',
        'Estonia/Saarema',
        '3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',       -- Carol's UUID
        '2023-01-01 00:00:00',
        'carol@example.com',
        '2023-01-01 00:00:00',
        'carol@example.com',
        0
    ),

    -- David (INACTIVE) has one metering point
    (
        '3e5c1b31-249a-4ca1-bcb1-917e7a7d5d56',
        'Estonia/Rakvere',
        'b30e6bd3-b301-4ae0-80b9-808f64ecf582',       -- David's UUID
        '2023-01-01 00:00:00',
        'david@example.com',
        '2023-01-01 00:00:00',
        'david@example.com',
        0
    );