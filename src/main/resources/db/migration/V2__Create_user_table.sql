CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    second_name VARCHAR(100),
    patronymic VARCHAR(100),
    phone_number VARCHAR(20) NOT NULL,
    email_address VARCHAR(100) NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    address_id BIGINT UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE SET NULL
);


--составные индексы???

CREATE INDEX IF NOT EXISTS idx_users_second_name ON users(second_name);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email_address);
CREATE INDEX IF NOT EXISTS idx_users_phone ON users(phone_number);