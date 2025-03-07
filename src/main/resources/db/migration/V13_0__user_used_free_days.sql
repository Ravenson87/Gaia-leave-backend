CREATE TABLE `user_used_free_days` (
                            `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                            `user_id` INT (11) NOT NULL,
                            `calendar_id` INT (11) NOT NULL,
                            `free_day_type` ENUM ( 'VACATION_LEAVE', 'SICK_LEAVE', 'TOIL'),
                            `created_by` VARCHAR (255) DEFAULT NULL,
                            `last_modified_by` VARCHAR (255) DEFAULT NULL,
                            `created_date` datetime DEFAULT  NULL,
                            `last_modified_date` datetime DEFAULT NULL,
                            PRIMARY KEY ( `id` ) USING BTREE,
                            CONSTRAINT `fk_user_used_free_days_v1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                            CONSTRAINT `fk_user_used_free_days_v2` FOREIGN KEY (`calendar_id`) REFERENCES `calendar`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;