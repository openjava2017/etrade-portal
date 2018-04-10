-- ----------------------------
-- 系统ID生成器数据模型
-- ----------------------------
DROP TABLE IF EXISTS `etrade_sequence_key`;
CREATE TABLE `etrade_sequence_key` (
  `id` BIGINT NOT NULL,
  `key` VARCHAR(50) NOT NULL,
  `start_with` BIGINT DEFAULT '1',
  `inc_span` BIGINT DEFAULT '1',
  `scope` VARCHAR(50),
  `description` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sequence_key_key` (`key`, `scope`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 组织机构数据模型
-- ----------------------------
DROP TABLE IF EXISTS `etrade_institution`;
CREATE TABLE `etrade_institution` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(20) NOT NULL COMMENT '机构编码',
  `name` VARCHAR(50) NOT NULL COMMENT '机构名称',
  `level` TINYINT UNSIGNED NOT NULL COMMENT '级别',
  `parent_code` VARCHAR(20) COMMENT '上级编码',
  `description` VARCHAR(100) DEFAULT NULL,
  `created_time` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_institution_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 系统用户数据模型设计
-- ----------------------------
DROP TABLE IF EXISTS `etrade_user`;
CREATE TABLE `etrade_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `account` VARCHAR(20) NOT NULL COMMENT '登陆账号',
  `name` VARCHAR(20) NOT NULL COMMENT '姓名',
  `gender` TINYINT UNSIGNED COMMENT '性别',
  `mobile` VARCHAR(20) COMMENT '手机号',
  `telphone` VARCHAR(20) COMMENT '固定电话',
  `email` VARCHAR(80) COMMENT '邮箱地址',
  `password` VARCHAR(50) NOT NULL COMMENT '登陆密码',
  `pwd_change` TINYINT UNSIGNED COMMENT '是否修改登陆密码',
  `pwd_errors` TINYINT UNSIGNED NOT NULL COMMENT '密码错误次数',
  `login_time` DATETIME COMMENT '最近登陆时间',
  `role` TINYINT UNSIGNED NOT NULL COMMENT '用户角色-财务',
  `position` TINYINT UNSIGNED COMMENT '用户职位',
  `access_token` VARCHAR(40) NULL COMMENT '登陆TokenID',
  `status` TINYINT UNSIGNED NOT NULL COMMENT '状态',
  `inst_code` VARCHAR(20) NOT NULL COMMENT '组织机构编码',
  `inst_name` VARCHAR(50) COMMENT '机构名称-保留字段',
  `description` VARCHAR(250) COMMENT '备注',
  `version` INTEGER UNSIGNED NOT NULL COMMENT '数据版本号',
  `created_time` DATETIME COMMENT '创建时间',
  `modified_time` DATETIME COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_account` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 用户-角色数据模型设计
-- ----------------------------
DROP TABLE IF EXISTS `etrade_user_role`;
CREATE TABLE `etrade_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` BIGINT NOT NULL COMMENT '员工ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `created_time` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role_userId` (`user_id`, `role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 用户角色数据模型设计
-- ----------------------------
DROP TABLE IF EXISTS `etrade_role`;
CREATE TABLE `etrade_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(20) NOT NULL COMMENT '角色名称',
  `description` VARCHAR(250) COMMENT '备注',
  `inst_code` VARCHAR(20) NOT NULL COMMENT '组织机构编码',
  `inst_name` VARCHAR(50) COMMENT '机构名称-保留字段',
  `created_time` DATETIME COMMENT '创建时间',
  `modified_time` DATETIME COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 角色-资源数据模型设计
-- ----------------------------
DROP TABLE IF EXISTS `etrade_role_permission`;
CREATE TABLE `etrade_role_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `code` VARCHAR(20) NOT NULL COMMENT '权限编码',
  `created_time` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_permission_roleId` (`role_id`) USING BTREE,
  KEY `idx_role_permission_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 页面资源数据模型设计
-- ----------------------------
DROP TABLE IF EXISTS `etrade_page_resource`;
CREATE TABLE `etrade_page_resource` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` VARCHAR(20) NOT NULL COMMENT '资源编码, 20-10',
  `name` VARCHAR(20) NOT NULL COMMENT '资源名称',
  `url` VARCHAR(80) COMMENT '请求路径',
  `path` VARCHAR(20) COMMENT '资源路径-views/user/list.html',
  `level` TINYINT UNSIGNED NOT NULL COMMENT '级别',
  `parent_code` VARCHAR(20) COMMENT '上级资源编码',
  `sequence` TINYINT UNSIGNED COMMENT '资源顺序',
  `description` VARCHAR(250) COMMENT '备注: 系统设置-用户管理',
  `created_time` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_page_resource_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 页面权限数据模型设计
-- ----------------------------
DROP TABLE IF EXISTS `etrade_page_permission`;
CREATE TABLE `etrade_page_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` VARCHAR(20) NOT NULL COMMENT '权限编码, 10-12-8',
  `name` VARCHAR(20) NOT NULL COMMENT '权限名称',
  `page_code` VARCHAR(20) NOT NULL COMMENT '所属页面编码',
  `mask` SMALLINT UNSIGNED NOT NULL COMMENT '权限掩码, 取值范围1 <= 2^n <= 65535',
  `description` VARCHAR(250) COMMENT '备注:用户管理-新增用户',
  `created_time` DATETIME COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_page_permission_code` (`code`) USING BTREE,
  KEY `idx_page_permission_pageCode` (`page_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;