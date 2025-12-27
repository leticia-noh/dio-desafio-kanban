--liquibase formatted sql
--changeset leticia:202512271703
--comment: create board table

CREATE TABLE board (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- rollback DROP TABLE board