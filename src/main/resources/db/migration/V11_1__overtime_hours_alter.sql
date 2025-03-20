ALTER TABLE `overtime_hours`
    ADD COLUMN `overtime_compensation` VARCHAR(255) DEFAULT NULL AFTER overtime_hours;