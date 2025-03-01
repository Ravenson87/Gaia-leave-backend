CREATE TABLE `leave_days`
(
    `id`                 INT (11) NOT NULL AUTO_INCREMENT,
    `type`               VARCHAR(255) NOT NULL,
    `date`               datetime     NOT NULL,
    `year`               INT(4)       NOT NULL,
    `created_by`         VARCHAR(255) DEFAULT NULL,
    `last_modified_by`   VARCHAR(255) DEFAULT NULL,
    `created_date`       datetime     DEFAULT NULL,
    `last_modified_date` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `date` (`date`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;