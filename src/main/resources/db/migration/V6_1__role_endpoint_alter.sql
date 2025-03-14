ALTER TABLE `role_endpoint`
    ADD CONSTRAINT unique_role_endpoint_v1 UNIQUE (role_id, endpoint_id);