-- =========================================
-- Refactor users table + create auth table
-- =========================================

-- Add new profile columns
ALTER TABLE users
    ADD COLUMN full_name VARCHAR(100);

ALTER TABLE users
    ADD COLUMN enabled BOOLEAN DEFAULT TRUE NOT NULL;

ALTER TABLE users
    ADD COLUMN account_non_locked BOOLEAN DEFAULT TRUE NOT NULL;

ALTER TABLE users
    ADD COLUMN account_non_expired BOOLEAN DEFAULT TRUE NOT NULL;

ALTER TABLE users
    ADD COLUMN credentials_non_expired BOOLEAN DEFAULT TRUE NOT NULL;


-- migrate old username to full_name
UPDATE users
SET full_name = username
WHERE full_name IS NULL;


ALTER TABLE users
    ALTER COLUMN full_name SET NOT NULL;


-- create auth table
CREATE TABLE user_auth_providers (
                                     id BIGSERIAL PRIMARY KEY,

                                     user_id BIGINT NOT NULL,

                                     provider_type VARCHAR(50) NOT NULL,

                                     provider_id VARCHAR(255) NOT NULL,

                                     password_hash VARCHAR(255),

                                     CONSTRAINT fk_user_auth_provider_user
                                         FOREIGN KEY (user_id)
                                             REFERENCES users(id)
                                             ON DELETE CASCADE,

                                     CONSTRAINT uk_provider_identity
                                         UNIQUE (provider_type, provider_id)
);


-- migrate existing login users
INSERT INTO user_auth_providers (
    user_id,
    provider_type,
    provider_id,
    password_hash
)
SELECT
    id,
    'MAIL',
    email,
    password
FROM users;


-- remove old auth columns
ALTER TABLE users
    DROP COLUMN username;

ALTER TABLE users
    DROP COLUMN password;