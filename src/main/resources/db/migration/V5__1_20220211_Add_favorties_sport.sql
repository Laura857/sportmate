CREATE TABLE user_favorite_sport (
    fk_id_user  int NOT NULL REFERENCES users (id),
    fk_id_sport int NOT NULL REFERENCES sport (id),
    PRIMARY KEY (fk_id_user, fk_id_sport)
);