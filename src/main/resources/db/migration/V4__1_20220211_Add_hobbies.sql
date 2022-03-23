CREATE TABLE hobbies (
    id    SERIAL  NOT NULL PRIMARY KEY,
    label varchar NOT NULL
);

INSERT INTO hobbies
VALUES (1, 'Cuisine'),
       (2, 'Cinéma'),
       (3, 'Voiture'),
       (4, 'Animaux'),
       (5, 'Livre'),
       (6, 'Musique');

CREATE TABLE user_hobbies (
    fk_id_user    int NOT NULL REFERENCES users (id),
    fk_id_hobbies int NOT NULL REFERENCES hobbies (id),
    created TIMESTAMP,
    PRIMARY KEY (fk_id_user, fk_id_hobbies)
);