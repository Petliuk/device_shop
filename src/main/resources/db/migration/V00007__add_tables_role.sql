CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(20) NOT NULL,
                       UNIQUE (name)


);
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id BIGINT
);

