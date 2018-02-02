-- 组织机构
INSERT INTO etrade_institution(code, name, level, parent_code, created_time) VALUES ('88', '地利集团', 1, null, now());
INSERT INTO etrade_institution(code, name, level, parent_code, created_time) VALUES ('8888', '哈尔滨地利', 1, '88', now());

-- 超级管理员
INSERT INTO etrade_user(account, name, gender, mobile, telphone, email, password, pwd_change, pwd_errors, role, status, inst_code, inst_name, version, created_time)
VALUES ('root', '超级管理员', 1, '13880460138', '13880460138', 'huangjian@diligrp.com', '01c7c5d57bf54091121f299d50d0dbe46bb758a8', 0, 10, 0, 1, '88', '地利集团', 1, now());