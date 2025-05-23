INSERT INTO customer (id,
                      email,
                      first_name,
                      last_name,
                      password,
                      customer_type,
                      customer_status,
                      created_at,
                      created_by,
                      updated_at,
                      updated_by,
                      version)
VALUES ('edb24e51-1c16-4df9-8ba5-845435cf66d7',
        'alice@example.com',
        'Alice',
        'Anderson',
        '$2a$10$N82gXLt1GF39ZvwXSdz6EOSSY0dsPlZJxfW84XN8wNCzZMeOvSL7a',
        'CUSTOMER',
        'ACTIVE',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        0),
       ('d0d3d547-1c9b-4df9-9153-c2ef451a20d4',
        'bob@example.com',
        'Bob',
        'Brown',
        '$2a$10$N82gXLt1GF39ZvwXSdz6EOSSY0dsPlZJxfW84XN8wNCzZMeOvSL7a',
        'CUSTOMER',
        'ACTIVE',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        0),
       ('3d017f49-9a37-4dbf-aa02-1acfdef7b4c2',
        'carol@example.com',
        'Carol',
        'Clark',
        '$2a$10$N82gXLt1GF39ZvwXSdz6EOSSY0dsPlZJxfW84XN8wNCzZMeOvSL7a',
        'CUSTOMER',
        'ACTIVE',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        0),
       ('b30e6bd3-b301-4ae0-80b9-808f64ecf582',
        'david@example.com',
        'David',
        'Davis',
        '$2a$10$N82gXLt1GF39ZvwXSdz6EOSSY0dsPlZJxfW84XN8wNCzZMeOvSL7a',
        'CUSTOMER',
        'INACTIVE',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        CURRENT_TIMESTAMP,
        'anonymousUser',
        0);
