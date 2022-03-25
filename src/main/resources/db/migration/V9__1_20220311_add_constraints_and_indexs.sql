ALTER TABLE users
    ALTER COLUMN created SET NOT NULL;

ALTER TABLE activity
    ALTER COLUMN created SET NOT NULL;

ALTER TABLE activity
    ALTER COLUMN contact SET NOT NULL;

CREATE INDEX ON users (email);
CREATE INDEX ON sport (label);
CREATE INDEX ON hobbies (label);
CREATE INDEX ON activity (creator);