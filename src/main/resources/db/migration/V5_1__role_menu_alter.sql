ALTER TABLE `role_menu`
ADD CONSTRAINT unique_role_menu_v1 UNIQUE (role_id, menu_id);