CREATE TABLE child_location
(
    id        SERIAL PRIMARY KEY,
    time      TIMESTAMP,
    latitude  DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    child_id  INT REFERENCES app_user (id)
);