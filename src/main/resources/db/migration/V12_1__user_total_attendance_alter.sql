ALTER TABLE `user_total_attendance`
    ADD COLUMN `overtime_hours_sum` SMALLINT DEFAULT NULL AFTER total_working_hours;