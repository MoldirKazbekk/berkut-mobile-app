CREATE TABLE pin_verification
(
    phone_number VARCHAR(20) PRIMARY KEY,
    pin_code     VARCHAR(6) NOT NULL,
    created_at   TIMESTAMP  NOT NULL
);