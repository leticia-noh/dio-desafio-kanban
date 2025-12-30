--liquibase formatted sql
--changeset leticia:202512301614
--comment: set unblock_reason nullable

    ALTER TABLE block MODIFY COLUMN unblock_reason VARCHAR(255) NULL;

-- rollback ALTER TABLE block MODIFY COLUMN unblock_reason VARCHAR(255) NOT NULL;