CREATE TABLE user_relationship
(
    parent_id INT REFERENCES app_user (id),
    child_id  INT REFERENCES app_user (id),
    PRIMARY KEY (parent_id, child_id)
);