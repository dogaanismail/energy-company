
CREATE TABLE IF NOT EXISTS consumption (

    id UUID NOT NULL PRIMARY KEY,
    metering_point_id UUID NOT NULL,
    customer_id UUID NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    amount_unit VARCHAR(3) NOT NULL,
    consumption_time TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(255) NOT NULL DEFAULT 'anonymousUser',
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(255),
    deleted_reason VARCHAR(255),
    version smallint NOT NULL DEFAULT 0
);

CREATE INDEX idx_consumption_metering_point_id ON consumption (metering_point_id);
CREATE INDEX idx_consumption_customer_id ON consumption (customer_id);