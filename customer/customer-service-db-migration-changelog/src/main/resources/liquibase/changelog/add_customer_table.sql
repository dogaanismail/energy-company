
CREATE TABLE IF NOT EXISTS customer (

    id UUID NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    customer_type VARCHAR(50) NOT NULL,
    customer_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(255),
    deleted_reason VARCHAR(255),
    version smallint NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX idx_customer_email ON customer (email);
