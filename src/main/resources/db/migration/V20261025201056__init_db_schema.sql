-- Baseline schema, matching the JPA entities in space.photostory.entity.
-- Enum columns (@Enumerated STRING) have no CHECK constraints on purpose: the Java enums validate the values,
-- and adding an enum constant then needs no migration. Renaming/removing one still needs a data migration.
-- Postgres does not index foreign key columns automatically, so every FK column gets its own index
-- (unless a primary key already starts with it).

CREATE TABLE profiles
(
    id         VARCHAR(26)                 NOT NULL,
    account_id VARCHAR(36)                 NOT NULL,
    username   VARCHAR(30)                 NOT NULL,
    email      VARCHAR(50)                 NOT NULL,
    full_name  VARCHAR(100),
    dob        DATE,
    gender     VARCHAR(10),
    avatar     VARCHAR(255),
    status     VARCHAR(20)                 NOT NULL,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    created_by VARCHAR(100)                NOT NULL,
    updated_at TIMESTAMP(6) WITH TIME ZONE,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP(6) WITH TIME ZONE,
    CONSTRAINT pk_profiles PRIMARY KEY (id),
    CONSTRAINT uk_profiles_account_id UNIQUE (account_id),
    CONSTRAINT uk_profiles_username UNIQUE (username),
    CONSTRAINT uk_profiles_email UNIQUE (email),
    -- Postgres compares text case-sensitively: storing only trimmed lowercase values (see Profile.normalize())
    -- makes the unique constraints above case-insensitive
    CONSTRAINT ck_profiles_username_normalized CHECK (username = LOWER(TRIM(username))),
    CONSTRAINT ck_profiles_email_normalized CHECK (email = LOWER(TRIM(email)))
);

CREATE TABLE albums
(
    id                         VARCHAR(26)                 NOT NULL,
    code                       VARCHAR(30),
    public_access_key          VARCHAR(16),
    pin                        VARCHAR(255),
    owner_id                   VARCHAR(26)                 NOT NULL,
    visibility                 VARCHAR(20)                 NOT NULL,
    relationship_type          VARCHAR(20)                 NOT NULL,
    custom_relationship        VARCHAR(30),
    custom_relationship_locale VARCHAR(20),
    saved_date                 DATE                        NOT NULL,
    title                      VARCHAR(255)                NOT NULL,
    description                VARCHAR(255),
    theme_song_url             VARCHAR(255),
    french_flip_note           VARCHAR(350)                NOT NULL,
    french_flip_place          VARCHAR(20),
    avatar_url                 VARCHAR(255),
    avatar_gender              VARCHAR(20),
    table_of_contents          JSONB,
    preface                    TEXT,
    highlight_photo_url        VARCHAR(255),
    afterword                  TEXT,
    status                     VARCHAR(20)                 NOT NULL,
    created_at                 TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    created_by                 VARCHAR(100)                NOT NULL,
    updated_at                 TIMESTAMP(6) WITH TIME ZONE,
    updated_by                 VARCHAR(100),
    deleted_at                 TIMESTAMP(6) WITH TIME ZONE,
    CONSTRAINT pk_albums PRIMARY KEY (id),
    CONSTRAINT uk_albums_code UNIQUE (code),
    CONSTRAINT uk_albums_public_access_key UNIQUE (public_access_key),
    CONSTRAINT fk_albums_owner FOREIGN KEY (owner_id) REFERENCES profiles (id)
);

CREATE INDEX idx_albums_owner_id ON albums (owner_id);

CREATE TABLE album_recipients
(
    album_id   VARCHAR(26) NOT NULL,
    recipients VARCHAR(255),
    CONSTRAINT fk_album_recipients_album FOREIGN KEY (album_id) REFERENCES albums (id)
);

CREATE INDEX idx_album_recipients_album_id ON album_recipients (album_id);

CREATE TABLE album_shared_profiles
(
    album_id   VARCHAR(26) NOT NULL,
    profile_id VARCHAR(26) NOT NULL,
    CONSTRAINT pk_album_shared_profiles PRIMARY KEY (album_id, profile_id),
    CONSTRAINT fk_album_shared_profiles_album FOREIGN KEY (album_id) REFERENCES albums (id),
    CONSTRAINT fk_album_shared_profiles_profile FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

-- the primary key already covers lookups by album_id
CREATE INDEX idx_album_shared_profiles_profile_id ON album_shared_profiles (profile_id);

CREATE TABLE stories
(
    id                   VARCHAR(26)                 NOT NULL,
    album_id             VARCHAR(26)                 NOT NULL,
    title                VARCHAR(40)                 NOT NULL,
    event_date           DATE                        NOT NULL,
    display_order_in_day INTEGER                     NOT NULL,
    weather              VARCHAR(20),
    content              TEXT                        NOT NULL,
    music_url            VARCHAR(255),
    status               VARCHAR(20)                 NOT NULL,
    created_at           TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    created_by           VARCHAR(100)                NOT NULL,
    updated_at           TIMESTAMP(6) WITH TIME ZONE,
    updated_by           VARCHAR(100),
    deleted_at           TIMESTAMP(6) WITH TIME ZONE,
    CONSTRAINT pk_stories PRIMARY KEY (id),
    CONSTRAINT fk_stories_album FOREIGN KEY (album_id) REFERENCES albums (id)
);

CREATE INDEX idx_stories_album_id ON stories (album_id);

CREATE TABLE photos
(
    id            VARCHAR(26)                 NOT NULL,
    story_id      VARCHAR(26)                 NOT NULL,
    media_url     VARCHAR(255)                NOT NULL,
    media_type    VARCHAR(20)                 NOT NULL,
    orientation   VARCHAR(20)                 NOT NULL,
    caption       VARCHAR(50),
    display_order INTEGER                     NOT NULL,
    status        VARCHAR(20)                 NOT NULL,
    created_at    TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    created_by    VARCHAR(100)                NOT NULL,
    updated_at    TIMESTAMP(6) WITH TIME ZONE,
    updated_by    VARCHAR(100),
    deleted_at    TIMESTAMP(6) WITH TIME ZONE,
    CONSTRAINT pk_photos PRIMARY KEY (id),
    CONSTRAINT fk_photos_story FOREIGN KEY (story_id) REFERENCES stories (id)
);

CREATE INDEX idx_photos_story_id ON photos (story_id);

CREATE TABLE locations
(
    id             VARCHAR(26)                 NOT NULL,
    story_id       VARCHAR(26)                 NOT NULL,
    name           VARCHAR(100)                NOT NULL,
    location_theme VARCHAR(15)                 NOT NULL,
    map_url        VARCHAR(512),
    display_order  INTEGER                     NOT NULL,
    status         VARCHAR(20)                 NOT NULL,
    created_at     TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    created_by     VARCHAR(100)                NOT NULL,
    updated_at     TIMESTAMP(6) WITH TIME ZONE,
    updated_by     VARCHAR(100),
    deleted_at     TIMESTAMP(6) WITH TIME ZONE,
    CONSTRAINT pk_locations PRIMARY KEY (id),
    CONSTRAINT fk_locations_story FOREIGN KEY (story_id) REFERENCES stories (id)
);

CREATE INDEX idx_locations_story_id ON locations (story_id);
