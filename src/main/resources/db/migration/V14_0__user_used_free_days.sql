CREATE TABLE `user_used_free_days` (
                            `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                            `user_id` INT (11) NOT NULL,
                            `calendar_id` INT (11) NOT NULL,
                            `free_day_type_id` INT (11) DEFAULT NULL,
                            `created_by` VARCHAR (255) DEFAULT NULL,
                            `last_modified_by` VARCHAR (255) DEFAULT NULL,
                            `created_date` datetime DEFAULT  NULL,
                            `last_modified_date` datetime DEFAULT NULL,
                            PRIMARY KEY ( `id` ) USING BTREE,
                            CONSTRAINT `fk_user_used_free_days_v1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                            CONSTRAINT `fk_user_used_free_days_v2` FOREIGN KEY (`calendar_id`) REFERENCES `calendar`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                            CONSTRAINT `fk_user_used_free_days_v3` FOREIGN KEY (`free_day_type_id`) REFERENCES `free_day_type`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;