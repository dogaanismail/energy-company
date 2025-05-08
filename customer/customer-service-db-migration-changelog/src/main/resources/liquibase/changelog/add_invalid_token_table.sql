
CREATE TABLE IF NOT EXISTS invalid_token (
    id VARCHAR NOT NULL PRIMARY KEY,
    token_id VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(255) NOT NULL DEFAULT 'anonymousUser',
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMPTZ,
    deleted_by VARCHAR(255),
    deleted_reason VARCHAR(255),
    version smallint NOT NULL DEFAULT 0
);

CREATE INDEX idx_invalid_token_token_id ON invalid_token (token_id);
