INSERT INTO authority (authority_id, manager_role, created_date, last_modified_date)
VALUES
    (1, 'ROLE_MANAGE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'ROLE_SUPER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (manager_role) DO NOTHING;