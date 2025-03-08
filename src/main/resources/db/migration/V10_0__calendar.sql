CREATE TABLE `calendar` (
                            `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                            `date` DATE NOT NULL,
                            `days` VARCHAR ( 30 ) NOT NULL,
                            `type` ENUM ( 'WEEKEND', 'NATIONAL_HOLIDAY','RELIGIOUS_HOLIDAY', 'WORKING_DAY' ),
                            PRIMARY KEY ( `id` ) USING BTREE,
                            UNIQUE KEY `date` ( `date` ) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;