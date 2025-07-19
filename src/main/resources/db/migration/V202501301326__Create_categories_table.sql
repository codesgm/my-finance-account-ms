-- Create categories table
CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('REVENUE', 'EXPENSE')),
    icon VARCHAR(50),
    color VARCHAR(7),
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT fk_categories_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create indexes
CREATE INDEX idx_categories_user_id ON categories(user_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_categories_type ON categories(type) WHERE deleted_at IS NULL;
CREATE INDEX idx_categories_deleted_at ON categories(deleted_at);

-- Para reverter essa migration executar o script abaixo:
-- DROP INDEX IF EXISTS idx_categories_deleted_at;
-- DROP INDEX IF EXISTS idx_categories_type;
-- DROP INDEX IF EXISTS idx_categories_user_id;
-- DROP TABLE IF EXISTS categories;
-- DELETE FROM flyway_schema_history WHERE version = '202501301326';
