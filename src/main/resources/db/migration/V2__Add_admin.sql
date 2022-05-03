insert into app_user (id, username, password, is_active)
    values (1, 'admin', 'string', true);

insert into user_role (user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');