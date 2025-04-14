ALTER TABLE `calendar`
    ADD COLUMN `description` VARCHAR(255) DEFAULT NULL AFTER `type`;