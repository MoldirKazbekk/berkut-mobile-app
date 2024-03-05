create table hotline_number
(
    id           SERIAL PRIMARY KEY,
    phone_number varchar(12),
    name         varchar(50),
    child_id     INT references app_user (id)
);
