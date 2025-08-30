CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE books (
                       uuid        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       title       VARCHAR(255) NOT NULL,
                       author      VARCHAR(255) NOT NULL,
                       isbn        VARCHAR(20)  NOT NULL,
                       CONSTRAINT uq_books_isbn UNIQUE (isbn)
);

CREATE TABLE clients (
                         uuid         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         last_name    VARCHAR(100) NOT NULL,
                         first_name   VARCHAR(100) NOT NULL,
                         middle_name  VARCHAR(100),
                         birth_date   DATE NOT NULL
);

CREATE TABLE readings (
                          uuid        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          client_uuid UUID NOT NULL REFERENCES clients(uuid) ON DELETE CASCADE,
                          book_uuid   UUID NOT NULL REFERENCES books(uuid)   ON DELETE RESTRICT,
                          taken_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_readings_client_uuid ON readings(client_uuid);
CREATE INDEX idx_readings_book_uuid   ON readings(book_uuid);
CREATE INDEX idx_readings_taken_at    ON readings(taken_at);

CREATE INDEX idx_clients_last_name    ON clients (LOWER(last_name));
CREATE INDEX idx_clients_first_name   ON clients (LOWER(first_name));