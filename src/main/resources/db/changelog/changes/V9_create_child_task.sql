CREATE TABLE child_task
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description        VARCHAR(500),
    coins    INT default 5,
    status   VARCHAR(50),
    child_id INT REFERENCES app_user(id)
);