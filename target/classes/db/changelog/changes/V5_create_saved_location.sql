CREATE TABLE saved_location
(
    id SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    latitude    DOUBLE PRECISION,
    longitude   DOUBLE PRECISION,
    parent_id INT REFERENCES app_user(id)
);