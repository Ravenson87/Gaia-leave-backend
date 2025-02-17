CREATE TABLE `job_position` (
    `id` INT (11) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR (255) NOT NULL,
    `description` VARCHAR (1000) DEFAULT NULL,
    `created_by` VARCHAR (255) DEFAULT NULL,
    `last_modified_by` VARCHAR (255) DEFAULT NULL,
    `created_date` datetime DEFAULT  NULL,
    `last_modified_date` datetime DEFAULT NULL,
    PRIMARY KEY(`id`) USING BTREE,
    UNIQUE KEY `title` (`title`) USING BTREE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;