create table auth_user
(
    id                   uuid                      not null primary key,
    username             varchar(255),
    email                varchar(255)       not null,
    password_hash        varchar(255),
    password_reset_token varchar(255),
    created_at            timestamp without time zone
);