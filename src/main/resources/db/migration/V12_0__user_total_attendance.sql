CREATE TABLE `user_total_attendance`
(
    `id`                  int(11) NOT NULL AUTO_INCREMENT,
    `user_id`             int(11) NOT NULL,
    `total_free_days`     int(11) DEFAULT NULL,
    `total_working_hours` int(11) DEFAULT NULL,
    `created_by` VARCHAR (255) DEFAULT NULL,
    `last_modified_by` VARCHAR (255) DEFAULT NULL,
    `created_date` datetime DEFAULT  NULL,
    `last_modified_date` datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    CONSTRAINT `fk_user_total_attendance_v1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;