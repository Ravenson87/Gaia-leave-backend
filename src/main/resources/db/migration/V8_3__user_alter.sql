ALTER TABLE `user`
    ADD COLUMN `profile_image` VARCHAR(70) DEFAULT NULL AFTER password;