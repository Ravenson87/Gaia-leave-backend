ALTER TABLE `user`
    ADD COLUMN `refresh_token_expire_time` DATETIME DEFAULT NULL AFTER refresh_token;