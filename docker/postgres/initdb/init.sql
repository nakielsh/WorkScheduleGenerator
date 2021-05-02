CREATE ROLE wsg_user WITH
    LOGIN
    PASSWORD 'eU9NCE0OXqYCiUeISFzfZXLzuqvEQrkyr+tqsYx+SB4='
    NOSUPERUSER
    INHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;

CREATE DATABASE wsg_database WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    LC_COLLATE = 'pl_PL.UTF-8'
    LC_CTYPE = 'pl_PL.UTF-8'
    CONNECTION LIMIT = -1;

\connect wsg_database

CREATE TABLE employee (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    days_left INTEGER,
    volume_factor DECIMAL(10,2),
    app_user_id BIGSERIAL NOT NULL,
    num_of_working_days INTEGER
);

CREATE TABLE employee_availability (
    employee_id BIGSERIAL,
    availability INTEGER
);

CREATE TABLE app_user (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(15) NOT NULL,
    last_name VARCHAR(15) NOT NULL,
    password VARCHAR(100) NOT NULL,
    username VARCHAR(20) NOT NULL,
    app_user_role varchar(10),
    locked boolean,
    enabled boolean
);

INSERT INTO app_user (
    first_name,
    last_name,
    username,
    password,
    app_user_role,
    locked,
    enabled
) VALUES (
    'admin',
    'admin',
    'admin',
    '$2a$10$6l5lmpxymHc9MNAumCPljeOZaLuSDrchoX5IZHlai2duUzi0jZjNa',
    'ADMIN',
    false,
    true
);