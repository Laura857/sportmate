CREATE TABLE users (
    id              serial     NOT NULL PRIMARY KEY,
    email           varchar NOT NULL UNIQUE,
    password        varchar NOT NULL,
    token           varchar,
    last_name       varchar NOT NULL,
    first_name      varchar NOT NULL,
    mobile          varchar NOT NULL,
    profile_picture varchar,
    sex             varchar NOT NULL,
    birthday        date,
    consents        boolean,
    created         timestamp,
    updated         timestamp
);

CREATE TABLE sport (
    id    serial     NOT NULL PRIMARY KEY,
    label varchar NOT NULL
);

INSERT INTO sport
VALUES (1, 'Course à pied'),
       (2, 'Natation');

CREATE TABLE level (
    id    serial     NOT NULL PRIMARY KEY,
    label varchar NOT NULL
);

INSERT INTO level
VALUES (1, 'Débutant'),
       (2, 'Intermédiaire'),
       (3, 'Confirmé');

CREATE TABLE activity (
    id             serial       NOT NULL PRIMARY KEY,
    is_event       BOOLEAN   NOT NULL,
    activity_name  varchar,
    activity_date  timestamp NOT NULL,
    creator        int       NOT NULL REFERENCES users (id),
    address        varchar   NOT NULL,
    longitude      varchar   NOT NULL,
    latitude       varchar   NOT NULL,
    participant    int,
    sport          int   NOT NULL REFERENCES sport (id),
    activity_level int   NOT NULL REFERENCES level (id),
    created        timestamp,
    updated        timestamp
);

CREATE TABLE user_activity (
    fk_id_user     int NOT NULL REFERENCES users (id),
    fk_id_activity int NOT NULL REFERENCES activity (id),
    PRIMARY KEY (fk_id_user, fk_id_activity)
);