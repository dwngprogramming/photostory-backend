-- Baseline schema, matching the JPA entities in space.photostory.entity.
-- Enum columns (@Enumerated STRING) have no CHECK constraints on purpose: the Java enums validate the values,
-- and adding an enum constant then needs no migration. Renaming/removing one still needs a data migration.

create table users
(
    id         varchar(26)                 not null,
    username   varchar(30),
    email      varchar(50),
    password   varchar(255)                not null,
    full_name  varchar(100),
    dob        date,
    gender     varchar(10),
    avatar     varchar(255),
    status     varchar(20)                 not null,
    created_at timestamp(6) with time zone not null,
    created_by varchar(100)                not null,
    updated_at timestamp(6) with time zone,
    updated_by varchar(100),
    deleted_at timestamp(6) with time zone,
    constraint pk_users primary key (id),
    constraint uk_users_username unique (username),
    constraint uk_users_email unique (email)
);

create table albums
(
    id                         varchar(26)                 not null,
    code                       varchar(30),
    public_access_key          varchar(16),
    pin                        varchar(255),
    owner_id                   varchar(26)                 not null,
    visibility                 varchar(255)                not null,
    relationship_type          varchar(255)                not null,
    custom_relationship        varchar(30),
    custom_relationship_locale varchar(255),
    saved_date                 date                        not null,
    title                      varchar(255)                not null,
    description                varchar(255),
    theme_song_url             varchar(255),
    french_flip_note           varchar(350)                not null,
    french_flip_place          varchar(20),
    avatar_url                 varchar(255),
    avatar_gender              varchar(255),
    table_of_contents          jsonb,
    preface                    text,
    highlight_photo_url        varchar(255),
    afterword                  text,
    status                     varchar(20)                 not null,
    created_at                 timestamp(6) with time zone not null,
    created_by                 varchar(100)                not null,
    updated_at                 timestamp(6) with time zone,
    updated_by                 varchar(100),
    deleted_at                 timestamp(6) with time zone,
    constraint pk_albums primary key (id),
    constraint uk_albums_code unique (code),
    constraint uk_albums_public_access_key unique (public_access_key),
    constraint fk_albums_owner foreign key (owner_id) references users (id)
);

create index idx_albums_owner_id on albums (owner_id);

create table album_recipients
(
    album_id   varchar(26) not null,
    recipients varchar(255),
    constraint fk_album_recipients_album foreign key (album_id) references albums (id)
);

create index idx_album_recipients_album_id on album_recipients (album_id);

create table album_shared_users
(
    album_id varchar(26) not null,
    user_id  varchar(26) not null,
    constraint pk_album_shared_users primary key (album_id, user_id),
    constraint fk_album_shared_users_album foreign key (album_id) references albums (id),
    constraint fk_album_shared_users_user foreign key (user_id) references users (id)
);

-- the primary key already covers lookups by album_id
create index idx_album_shared_users_user_id on album_shared_users (user_id);

create table stories
(
    id                   varchar(26)                 not null,
    album_id             varchar(26)                 not null,
    title                varchar(40)                 not null,
    event_date           date                        not null,
    display_order_in_day integer                     not null,
    weather              varchar(255),
    content              text                        not null,
    music_url            varchar(255),
    status               varchar(20)                 not null,
    created_at           timestamp(6) with time zone not null,
    created_by           varchar(100)                not null,
    updated_at           timestamp(6) with time zone,
    updated_by           varchar(100),
    deleted_at           timestamp(6) with time zone,
    constraint pk_stories primary key (id),
    constraint fk_stories_album foreign key (album_id) references albums (id)
);

create index idx_stories_album_id on stories (album_id);

create table photos
(
    id            varchar(26)                 not null,
    story_id      varchar(26)                 not null,
    media_url     varchar(255)                not null,
    media_type    varchar(255)                not null,
    orientation   varchar(255)                not null,
    caption       varchar(50),
    display_order integer                     not null,
    status        varchar(20)                 not null,
    created_at    timestamp(6) with time zone not null,
    created_by    varchar(100)                not null,
    updated_at    timestamp(6) with time zone,
    updated_by    varchar(100),
    deleted_at    timestamp(6) with time zone,
    constraint pk_photos primary key (id),
    constraint fk_photos_story foreign key (story_id) references stories (id)
);

create index idx_photos_story_id on photos (story_id);

create table locations
(
    id             varchar(26)                 not null,
    story_id       varchar(26)                 not null,
    name           varchar(100)                not null,
    location_theme varchar(15)                 not null,
    map_url        varchar(512),
    display_order  integer                     not null,
    status         varchar(20)                 not null,
    created_at     timestamp(6) with time zone not null,
    created_by     varchar(100)                not null,
    updated_at     timestamp(6) with time zone,
    updated_by     varchar(100),
    deleted_at     timestamp(6) with time zone,
    constraint pk_locations primary key (id),
    constraint fk_locations_story foreign key (story_id) references stories (id)
);

create index idx_locations_story_id on locations (story_id);
