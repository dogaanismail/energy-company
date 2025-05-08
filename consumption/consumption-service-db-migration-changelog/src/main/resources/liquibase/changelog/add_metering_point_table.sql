
CREATE TABLE IF NOT EXISTS metering_point (

    id UUID NOT NULL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    customer_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(255) NOT NULL DEFAULT 'anonymousUser',
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(255),
    deleted_reason VARCHAR(255),
    version smallint NOT NULL DEFAULT 0
);

CREATE INDEX idx_metering_point_customer_id ON metering_point (customer_id);