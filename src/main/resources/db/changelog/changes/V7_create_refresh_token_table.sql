create table refresh_token
(
    id          SERIAL primary key,
    token       varchar(100) not null,
    expire_date timestamp,
    user_id     BIGINT       not null,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) references app_user (id)
)