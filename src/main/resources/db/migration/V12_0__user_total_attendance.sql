CREATE TABLE `user_total_attendance`
(
    `id`                  int(11) NOT NULL AUTO_INCREMENT,
    `user_id`             int(11) NOT NULL,
    `total_free_days`     int(11) NOT NULL DEFAULT 0,
    `total_working_hours` int(11) NOT NULL DEFAULT 0,
    `overtime_hours_sum` smallint NOT NULL DEFAULT 0,
    `created_by` VARCHAR (255) DEFAULT NULL,
    `last_modified_by` VARCHAR (255) DEFAULT NULL,
    `created_date` datetime DEFAULT  NULL,
    `last_modified_date` datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `user_id` (`user_id`) USING BTREE,
    CONSTRAINT `fk_user_total_attendance_v1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;