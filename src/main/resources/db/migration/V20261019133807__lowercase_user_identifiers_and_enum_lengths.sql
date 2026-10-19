-- 1. Username and email are lowercase only (Postgres compares text case-sensitively).
--    Existing rows are lowercased first, then CHECK constraints reject any uppercase value.
--    The baseline unique constraints (uk_users_username, uk_users_email) then cover uniqueness.
update users
set username = lower(trim(username))
where username <> lower(trim(username));

update users
set email = lower(trim(email))
where email <> lower(trim(email));

alter table users
    add constraint ck_users_username_lowercase check (username = lower(username)),
    add constraint ck_users_email_lowercase check (email = lower(email));

-- 2. Enum columns (@Enumerated STRING): varchar(255) -> varchar(20), matching the entities.
alter table albums
    alter column visibility type varchar(20),
    alter column relationship_type type varchar(20),
    alter column custom_relationship_locale type varchar(20),
    alter column avatar_gender type varchar(20);

alter table photos
    alter column media_type type varchar(20),
    alter column orientation type varchar(20);

alter table stories
    alter column weather type varchar(20);
