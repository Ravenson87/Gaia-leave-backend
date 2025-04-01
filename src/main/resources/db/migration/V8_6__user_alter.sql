ALTER TABLE `user`
    ADD COLUMN `hash` VARCHAR(150) DEFAULT NULL AFTER refresh_token_expire_time,
    ADD COLUMN `link_expired` DATETIME DEFAULT NULL AFTER hash;