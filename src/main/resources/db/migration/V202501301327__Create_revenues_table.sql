-- Create revenues table
CREATE TABLE revenues (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    date DATE NOT NULL,
    user_id UUID NOT NULL,
    category_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    CONSTRAINT fk_revenues_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_revenues_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Create indexes
CREATE INDEX idx_revenues_user_id ON revenues(user_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_revenues_category_id ON revenues(category_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_revenues_date ON revenues(date) WHERE deleted_at IS NULL;
CREATE INDEX idx_revenues_deleted_at ON revenues(deleted_at);

-- Para reverter essa migration executar o script abaixo:
-- DROP INDEX IF EXISTS idx_revenues_deleted_at;
-- DROP INDEX IF EXISTS idx_revenues_date;
-- DROP INDEX IF EXISTS idx_revenues_category_id;
-- DROP INDEX IF EXISTS idx_revenues_user_id;
-- DROP TABLE IF EXISTS revenues;
-- DELETE FROM flyway_schema_history WHERE version = '202501301327';
