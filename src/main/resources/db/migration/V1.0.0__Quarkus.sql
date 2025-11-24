CREATE TABLE application_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'User'
);

CREATE TABLE media (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_media_user FOREIGN KEY (user_id) REFERENCES application_user(id) ON DELETE CASCADE
);

CREATE TABLE duration (
    id BIGSERIAL PRIMARY KEY,
    minutes INTEGER NOT NULL,
    seconds INTEGER NOT NULL
);

CREATE TABLE music (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    rating INTEGER,
    duration_id BIGINT,
    media_id BIGINT NOT NULL,
    CONSTRAINT fk_music_duration FOREIGN KEY (duration_ID) REFERENCES duration(id) ON DELETE SET NULL,
    CONSTRAINT fk_music_media FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE
);

CREATE TABLE video (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    director VARCHAR(255),
    rating INTEGER,
    duration_id BIGINT,
    media_id BIGINT NOT NULL,
    CONSTRAINT fk_video_duration FOREIGN KEY (duration_ID) REFERENCES duration(id) ON DELETE SET NULL,
    CONSTRAINT fk_video_media FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE
);

CREATE TABLE game (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    platform VARCHAR(255),
    description VARCHAR(255),
    rating INTEGER,
    media_id BIGINT NOT NULL,
    CONSTRAINT fk_video_media FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE
);

CREATE TABLE genre (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Join Table: Media <-> Genre (Many-to-Many)
CREATE TABLE media_genre (
    media_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (media_id, genre_id),
    CONSTRAINT fk_media_genre_media FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE,
    CONSTRAINT fk_media_genre_genre FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

CREATE INDEX idx_media_user_id ON media(user_id);
CREATE INDEX idx_music_media_id ON music(media_id);
CREATE INDEX idx_video_media_id ON video(media_id);
CREATE INDEX idx_game_media_id ON game(media_id);
CREATE INDEX idx_application_user_email ON application_user(email);

INSERT INTO genre (name) VALUES
    ('Rock'),
    ('Pop'),
    ('Jazz'),
    ('Classical'),
    ('Action'),
    ('Comedy'),
    ('Drama'),
    ('Horror'),
    ('RPG'),
    ('Strategy'),
    ('Adventure'),
    ('Puzzle');