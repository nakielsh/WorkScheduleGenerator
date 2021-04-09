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
CREATE SCHEMA IF NOT EXISTS wsg;

CREATE TABLE employee (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    availability INTEGER[] NOT NULL,
    days_left INTEGER,
    volume_factor DECIMAL(10,2)
);

CREATE TABLE app_user (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(15) NOT NULL,
    last_name VARCHAR(15) NOT NULL,
    password VARCHAR(20) NOT NULL,
    username VARCHAR(20) NOT NULL
);