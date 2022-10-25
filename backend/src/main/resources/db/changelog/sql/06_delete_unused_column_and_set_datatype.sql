alter table auth_user drop column created_at;
alter table auth_user drop column blocked_at;
alter table auth_user add column blocked_at boolean default false;
