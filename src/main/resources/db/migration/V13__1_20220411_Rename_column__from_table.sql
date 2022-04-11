ALTER TABLE user_favorite_sport
    RENAME COLUMN fk_id_user TO user_id;

ALTER TABLE user_favorite_sport
    RENAME COLUMN fk_id_sport TO sport_id;

ALTER TABLE user_favorite_sport
    RENAME COLUMN fk_id_level TO level_id;

ALTER TABLE user_activity
    RENAME COLUMN fk_id_user TO user_id;

ALTER TABLE user_activity
    RENAME COLUMN fk_id_activity TO activity_id;

