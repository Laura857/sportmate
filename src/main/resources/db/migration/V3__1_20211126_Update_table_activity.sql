ALTER TABLE activity
    ALTER COLUMN longitude DROP NOT NULL;

ALTER TABLE activity
    ALTER COLUMN latitude DROP NOT NULL;

ALTER TABLE activity
    ADD COLUMN description VARCHAR;

ALTER TABLE activity
    ADD COLUMN contact VARCHAR;