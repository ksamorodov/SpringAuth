alter table auth_user add column role varchar(50) not null;
alter table auth_user add column blocked_at timestamp without time zone;
alter table auth_user add column is_valid_password boolean;
INSERT INTO public.auth_user (id, username, password_hash, password_reset_token, created_at, first_name, last_name, role, blocked_at, is_valid_password) VALUES ('d81827e3-ff51-4419-bbad-950ab7d7e7fd', 'admin', '$2a$10$xibZdN6wGLzvCDERz4vZ5OFuFwuYqNZXOVHCe08USUyEwsHIaSHVK', null, '2022-10-22 12:37:30.000000', 'Кирилл', 'Александрович', 'ADMIN', null, false);
