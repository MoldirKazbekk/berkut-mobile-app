CREATE TABLE child_task
(
    id SERIAL PRIMARY KEY,
    task_description        VARCHAR(500),
    coins    INT default 5,
    status   VARCHAR(50),
    type VARCHAR(255),
    parent_id INT REFERENCES app_user(id)
);