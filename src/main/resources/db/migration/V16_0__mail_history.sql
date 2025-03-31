CREATE TABLE `mail_history` (
                                  `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                                  `addresses` TEXT NOT NULL,
                                  `message` TEXT DEFAULT NULL,
                                  `created_date` datetime DEFAULT  NULL,
                                  FULLTEXT(addresses, message),
                                  PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;