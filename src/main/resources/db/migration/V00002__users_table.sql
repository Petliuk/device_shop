TRUNCATE TABLE users;

ALTER TABLE users
DROP COLUMN login;

ALTER TABLE users
ADD CONSTRAINT unique_email UNIQUE (email);