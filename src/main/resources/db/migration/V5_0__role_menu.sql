CREATE TABLE `role_menu` (
    `id` INT (11) NOT NULL AUTO_INCREMENT,
    `role_id` INT (11) NOT NULL,
    `menu_id` INT (11) NOT NULL,
    PRIMARY KEY(`id`) USING BTREE,
    CONSTRAINT `fk_role_menu_v1` FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_role_menu_v2` FOREIGN KEY (`menu_id`) REFERENCES `menu`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;