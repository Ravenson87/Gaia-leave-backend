CREATE TABLE `calendar` (
                            `id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
                            `date` DATE NOT NULL,
                            `days` VARCHAR ( 30 ) NOT NULL,
                            `type` ENUM ( 'weekend', 'national_holiday', 'working_day' ),
                            PRIMARY KEY ( `id` ) USING BTREE,
                            UNIQUE KEY `date` ( `date` ) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;