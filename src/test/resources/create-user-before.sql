delete from user_role;
delete from app_user;

insert into app_user(id, is_active, password, username) values
    (1, true, '$2a$08$UWNQXRgW1cmVgR0UiC.vh.kf5RHN4v2IDQXNzpyI2DNC7RdPd8uhG', 'admin'),
    (2, true, '$2a$08$UWNQXRgW1cmVgR0UiC.vh.kf5RHN4v2IDQXNzpyI2DNC7RdPd8uhG', 'user');

insert into user_role(user_id, roles) values
    (1, 'USER'), (1, 'ADMIN'), (2, 'USER');