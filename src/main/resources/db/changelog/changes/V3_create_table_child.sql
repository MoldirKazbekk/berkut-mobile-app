create table child
(
    id          SERIAL PRIMARY KEY,
    app_user_id INT not null,
    parent_id   INT not null,
    CONSTRAINT fk_app_user_id foreign key (app_user_id) references app_user (id),
    CONSTRAINT fk_parent_id foreign key (parent_id) references parent (id)
);