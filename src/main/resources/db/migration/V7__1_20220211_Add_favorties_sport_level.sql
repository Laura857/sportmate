ALTER TABLE user_favorite_sport
    ADD COLUMN level int;

ALTER TABLE user_favorite_sport
    ADD CONSTRAINT fk_id_level FOREIGN KEY (level) REFERENCES level (id);

ALTER TABLE user_favorite_sport
    ALTER COLUMN level SET NOT NULL;