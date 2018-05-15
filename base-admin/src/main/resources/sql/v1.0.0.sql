INSERT INTO base_user(id, username, PASSWORD, creater, create_time, updater, update_time) VALUES(1, 'admin', 'admin', 'admin', NOW(), 'admin', NOW());
INSERT INTO base_user(id, username, PASSWORD, creater, create_time, updater, update_time) VALUES(2, 'user', 'user', 'admin', NOW(), 'admin', NOW());

INSERT INTO base_role(id, NAME, remark) VALUES(1, 'admin', '超级管理员');
INSERT INTO base_role(id, NAME, remark) VALUES(2, 'user', '用户');

-- 系统管理
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(11, 'perm_role', '角色管理', NULL);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1101, 'role:create', '录入', 11);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1102, 'role:view', '查看', 11);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1103, 'role:update', '修改', 11);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1104, 'role:delete', '删除', 11);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(12, 'perm_user', '用户管理', NULL);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1201, 'user:create', '录入', 12);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1202, 'user:view', '查看', 12);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1203, 'user:update', '修改', 12);
INSERT INTO base_perm(id, NAME, remark, parent_id) VALUES(1204, 'user:delete', '删除', 12);


INSERT INTO base_user_role VALUES(1, 1);
INSERT INTO base_user_role VALUES(2, 2);

INSERT INTO base_role_perm VALUES(11, 1);
INSERT INTO base_role_perm VALUES(1101, 1);
INSERT INTO base_role_perm VALUES(1102, 1);
INSERT INTO base_role_perm VALUES(1103, 1);
INSERT INTO base_role_perm VALUES(1104, 1);
INSERT INTO base_role_perm VALUES(12, 1);
INSERT INTO base_role_perm VALUES(1201, 1);
INSERT INTO base_role_perm VALUES(1202, 1);
INSERT INTO base_role_perm VALUES(1203, 1);
INSERT INTO base_role_perm VALUES(1204, 1);
