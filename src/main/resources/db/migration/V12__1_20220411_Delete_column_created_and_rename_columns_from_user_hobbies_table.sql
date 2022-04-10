ALTER TABLE user_hobbies
    DROP COLUMN IF EXISTS created;

ALTER TABLE user_hobbies
    DROP COLUMN IF EXISTS id;

ALTER TABLE user_hobbies
    RENAME COLUMN fk_id_user TO user_id;

ALTER TABLE user_hobbies
    RENAME COLUMN fk_id_hobbies TO hobbies_id;

