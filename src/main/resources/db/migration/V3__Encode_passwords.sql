create extension if not exists pgcrypto;

update app_user set password = crypt(password, gen_salt('bf', 8));
-- gen_salt('bf', 8) - additional value which adds to the password to make more strength ('bf' - algorithm, 8 - strength)