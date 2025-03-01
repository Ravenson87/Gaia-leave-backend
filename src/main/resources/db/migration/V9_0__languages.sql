CREATE TABLE `languages` (
    `id` INT (11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR (255) NOT NULL,
    `code` VARCHAR (255) DEFAULT NULL,
    `created_by` VARCHAR (255) DEFAULT NULL,
    `last_modified_by` VARCHAR (255) DEFAULT NULL,
    `created_date` datetime DEFAULT  NULL,
    `last_modified_date` datetime DEFAULT NULL,
    PRIMARY KEY(`id`) USING BTREE,
    UNIQUE KEY `name` (`name`) USING BTREE
    UNIQUE KEY `code` (`code`) USING BTREE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;