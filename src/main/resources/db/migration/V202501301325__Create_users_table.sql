-- Create users table with UUID primary key and audit fields
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_access TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by UUID,
    updated_by UUID
);

-- Create index for email lookups
CREATE INDEX idx_users_email ON users(email) WHERE deleted_at IS NULL;

-- Create index for soft delete queries
CREATE INDEX idx_users_deleted_at ON users(deleted_at);

-- Para reverter essa migration executar o script abaixo:
-- DROP INDEX IF EXISTS idx_users_deleted_at;
-- DROP INDEX IF EXISTS idx_users_email;
-- DROP TABLE IF EXISTS users;
-- DELETE FROM flyway_schema_history WHERE version = '202501301325';