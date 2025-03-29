CREATE TABLE `mail_history` (
                                  `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                                  `sender`  VARCHAR(255) NOT NULL,
                                  `send_to` VARCHAR(255) NOT NULL,
                                  `subject` VARCHAR(255) DEFAULT NULL,
                                  `service_name` VARCHAR(255) DEFAULT NULL,
                                  `endpoint` VARCHAR(255) DEFAULT NULL,
                                  `created_by` VARCHAR (255) DEFAULT NULL,
                                  `created_date` datetime DEFAULT  NULL,
                                  PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;