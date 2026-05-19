-- V6__add_email_to_users.sql

ALTER TABLE users
    ADD COLUMN email VARCHAR(255);

UPDATE users
SET email = username
WHERE email IS NULL;

ALTER TABLE users
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT uk_users_email UNIQUE (email);