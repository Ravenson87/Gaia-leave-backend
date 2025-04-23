CREATE TABLE `endpoint` (
    `id` VARCHAR(255) NOT NULL,
    `method` VARCHAR (7) NOT NULL,
    `service` VARCHAR (255) DEFAULT NULL,
    `controller` VARCHAR (255) DEFAULT NULL,
    `controller_alias` VARCHAR (255) DEFAULT NULL,
    `action` VARCHAR (7) DEFAULT NULL,
    `endpoint` VARCHAR (255) DEFAULT NULL,
    PRIMARY KEY(`id`) USING BTREE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;