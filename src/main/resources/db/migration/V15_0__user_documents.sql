CREATE TABLE `user_documents` (
                            `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                            `user_id` INT (11) NOT NULL,
                            `document_path` VARCHAR (70) NOT NULL,
                            PRIMARY KEY ( `id` ) USING BTREE,
                            CONSTRAINT `fk_user_documents_v1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;