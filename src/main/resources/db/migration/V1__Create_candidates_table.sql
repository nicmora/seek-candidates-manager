-- V1__Create_candidates_table.sql

CREATE TABLE candidates
(
    id             BIGINT,
    name           VARCHAR(255),
    email          VARCHAR(255),
    gender         CHAR(1),
    salary_expected INT
);