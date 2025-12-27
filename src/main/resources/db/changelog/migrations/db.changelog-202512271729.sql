--liquibase formatted sql
--changeset leticia:202512271729
--comment: create block table

CREATE TABLE block (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason VARCHAR(255) NOT NULL,
    unblocked_at TIMESTAMP NULL,
    unblock_reason VARCHAR(255) NOT NULL,
    card_id BIGINT NOT NULL,

    CONSTRAINT card__block_fk FOREIGN KEY (card_id) REFERENCES card(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- rollback DROP TABLE block