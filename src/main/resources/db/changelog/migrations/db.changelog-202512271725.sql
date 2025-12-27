--liquibase formatted sql
--changeset leticia:202512271725
--comment: create card table

CREATE TABLE card (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    board_column_id BIGINT NOT NULL,

    CONSTRAINT board_column__card_fk FOREIGN KEY (board_column_id) REFERENCES board_column(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- rollback DROP TABLE card