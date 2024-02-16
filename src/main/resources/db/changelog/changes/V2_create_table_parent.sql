create table berkut.parent
(
    id          SERIAL PRIMARY KEY,
    app_user_id INT NOT NULL,
    CONSTRAINT fk_app_user_id FOREIGN KEY (app_user_id) references app_user (id)
);