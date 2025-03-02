CREATE TABLE `user_total_attendance` (
                            `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                            `user_id` INT (11) NOT NULL,
                            `total_free_days` INT (11) NOT NULL,
                            `total_working_hours` INT (11),
                            PRIMARY KEY ( `id` ) USING BTREE,
                            CONSTRAINT `fk_user_total_attendance_v1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;