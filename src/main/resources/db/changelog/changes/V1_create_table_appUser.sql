CREATE TABLE berkut.app_user (
                          id SERIAL PRIMARY KEY,
                          username VARCHAR(255) NOT NULL,
                          phone_number VARCHAR(20) NOT NULL,
                          image BYTEA
);