INSERT IGNORE INTO `role` (
       id,
       name,
       description,
       created_by,
       last_modified_by,
       created_date,
       last_modified_date
) VALUES (
        1,
        'super_admin',
        'Super admin role',
        'system',
        'system',
        NOW(),
        NOW()
);

INSERT IGNORE INTO `job_position` (
       id,
       title,
       description
) VALUES (
       1,
       'Technical Lead',
       'The Technical Lead will guide the team through the development process.'
);

INSERT IGNORE INTO `user` (
       id,
       role_id,
       job_position_id,
       first_name,
       last_name,
       email,
       username,
       password,
       status,
       created_by,
       last_modified_by,
       created_date,
       last_modified_date
) VALUES (
       1,
       1,
       1,
       'system',
       'system',
       'admin@example.com',   -- neutral placeholder email
       'system',
       '$2a$10$PLACEHOLDERHASHVALUE1234567890abcdef',  -- placeholder hash
       1,
       'system',
       'system',
       NOW(),
       NOW()
);

INSERT IGNORE INTO `free_day_type` (
        id,
        `type`,
        description,
        created_by,
        last_modified_by,
        created_date,
        last_modified_date
) VALUES (
        1,
        'vacation',
        'vacation',
        'system',
        'system',
        NOW(),
        NOW()
);
