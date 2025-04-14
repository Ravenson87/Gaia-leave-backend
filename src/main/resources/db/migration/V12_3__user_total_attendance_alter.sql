ALTER TABLE `user_total_attendance`
    MODIFY COLUMN `total_free_days` int(11) DEFAULT 0,
    MODIFY COLUMN `total_working_hours` int(11) DEFAULT 0,
    MODIFY COLUMN `overtime_hours_sum` int(11) DEFAULT 0;
