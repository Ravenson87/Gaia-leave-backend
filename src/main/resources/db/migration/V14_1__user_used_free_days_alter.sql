ALTER TABLE `user_used_free_days`
   DROP CONSTRAINT fk_user_used_free_days_v1;

ALTER TABLE `user_used_free_days`
ADD CONSTRAINT fk_user_used_free_days_v1
FOREIGN KEY (`user_id`)
REFERENCES `user`(`id`)
ON DELETE CASCADE
ON UPDATE CASCADE;
