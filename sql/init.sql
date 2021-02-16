/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : small_erp

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 16/02/2021 16:58:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attach_association
-- ----------------------------
DROP TABLE IF EXISTS `attach_association`;
CREATE TABLE `attach_association`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `association_id` bigint(20) NULL DEFAULT NULL COMMENT '关联id',
  `real_attach_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实附件名称',
  `new_attach_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改后的附件名称',
  `attach_type` tinyint(2) NULL DEFAULT 0 COMMENT '附件类型',
  `attach_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件路径',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '1:删除;0：正常',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '附件关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for process_setting
-- ----------------------------
DROP TABLE IF EXISTS `process_setting`;
CREATE TABLE `process_setting`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `module_id` bigint(20) NULL DEFAULT NULL COMMENT '关联模块id',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联模块name',
  `process_definition_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例key',
  `assignees` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会审审批人',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除(默认：0；删除：1)',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_module_id`(`module_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业务模块关联流程' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dict_type_id` bigint(20) NULL DEFAULT NULL COMMENT '主表id',
  `dict_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `dict_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `is_default` tinyint(1) NULL DEFAULT 0 COMMENT '是否默认(1是;0否)',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `dict_sort` tinyint(3) NULL DEFAULT 0 COMMENT '字典排序',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '1:删除;0：正常',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '1:删除;0：正常',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `request_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `result_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回参数',
  `module_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(1成功;0失败)',
  `message` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法名称',
  `request_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求IP',
  `request_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除(默认：0；删除：1)',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `business_type` tinyint(4) NULL DEFAULT NULL COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1361577340882259968, '{\"id\":\"1361577340827734016\",\"createBy\":null,\"createTime\":1613460309594,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色11\",\"roleCode\":\"role11\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}', '{\"status\":true,\"code\":200,\"message\":\"\",\"data\":{\"id\":\"1361577340827734016\",\"createBy\":null,\"createTime\":1613460309594,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色11\",\"roleCode\":\"role11\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}}', '角色信息', 1, NULL, 'top.dongxibao.erp.controller.system.SysRoleController.insert()', '127.0.0.1', 'POST', 0, NULL, '2021-02-16 15:25:10', NULL, NULL, NULL, 1);
INSERT INTO `sys_log` VALUES (1361577360587100160, '{\"id\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色11\",\"roleCode\":\"role11\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}', NULL, '角色信息', 0, '新增角色\'role11\'失败，角色权限已存在', 'top.dongxibao.erp.controller.system.SysRoleController.insert()', '127.0.0.1', 'POST', 0, NULL, '2021-02-16 15:25:14', NULL, NULL, NULL, 1);
INSERT INTO `sys_log` VALUES (1361577859013021696, '{\"id\":\"1361577858912358400\",\"createBy\":\"admin\",\"createTime\":1613460433115,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色22\",\"roleCode\":\"role22\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}', '{\"status\":true,\"code\":200,\"message\":\"\",\"data\":{\"id\":\"1361577858912358400\",\"createBy\":\"admin\",\"createTime\":1613460433115,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色22\",\"roleCode\":\"role22\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}}', '角色信息', 1, NULL, 'top.dongxibao.erp.controller.system.SysRoleController.insert()', '127.0.0.1', 'POST', 0, NULL, '2021-02-16 15:27:13', NULL, 'admin', NULL, 1);
INSERT INTO `sys_log` VALUES (1361590809689985024, '{\"id\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色22\",\"roleCode\":\"role22\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}', NULL, '角色信息', 0, '新增角色\'role22\'失败，角色权限已存在', 'top.dongxibao.erp.controller.system.SysRoleController.insert()', '127.0.0.1', 'POST', 0, NULL, '2021-02-16 16:18:41', NULL, 'admin', NULL, 1);
INSERT INTO `sys_log` VALUES (1361590855143657472, '{\"id\":1361590855105908736,\"createBy\":\"admin\",\"createTime\":1613463531649,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色33\",\"roleCode\":\"role33\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}', '{\"status\":true,\"code\":200,\"message\":null,\"data\":{\"id\":1361590855105908736,\"createBy\":\"admin\",\"createTime\":1613463531649,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色33\",\"roleCode\":\"role33\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}}', '角色信息', 1, NULL, 'top.dongxibao.erp.controller.system.SysRoleController.insert()', '127.0.0.1', 'POST', 0, NULL, '2021-02-16 16:18:52', NULL, 'admin', NULL, 1);
INSERT INTO `sys_log` VALUES (1361591113672167424, '{\"id\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"delFlag\":null,\"remark\":\"\",\"params\":{},\"roleName\":\"角色33\",\"roleCode\":\"role33\",\"roleSort\":99,\"dataScope\":null,\"status\":null,\"userId\":null,\"menuIds\":null,\"deptIds\":null}', NULL, '角色信息', 0, '新增角色\'role33\'失败，角色权限已存在', 'top.dongxibao.erp.controller.system.SysRoleController.insert()', '127.0.0.1', 'POST', 0, NULL, '2021-02-16 16:19:53', NULL, 'admin', NULL, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `menu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `perms_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '权限标识',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(11) NULL DEFAULT 99 COMMENT '显示顺序',
  `routing_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型:M目录,C菜单,F按钮',
  `visible` tinyint(4) NULL DEFAULT 0 COMMENT '菜单状态:0显示,1隐藏',
  `icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除(默认：0；删除：1)',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `form_config_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单级关联列表配置id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_perms_code`(`perms_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notifice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notifice`;
CREATE TABLE `sys_notifice`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type` tinyint(3) NULL DEFAULT 0 COMMENT '类型',
  `from_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送用户',
  `to_user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接受用户',
  `title` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '阅读时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '1:删除;0：正常',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NULL DEFAULT 99 COMMENT '显示顺序',
  `data_scope` tinyint(4) NULL DEFAULT 1 COMMENT '数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '角色状态:0正常,1禁用',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除(默认：0；删除：1)',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录账号',
  `nick_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户昵称',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `post_id` bigint(20) NULL DEFAULT NULL COMMENT '岗位ID',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别(0未知;1男;2女)',
  `avatar` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '帐号状态:0正常,1禁用',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除(默认：0；删除：1)',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '超级管理员', '$2a$10$WeMWKGGBc5XJFP1XRdTYeuwGjNUqbxqywAR2dkbUh/OhFZqJpEouy', NULL, NULL, '', '', NULL, NULL, 0, 0, '', '', NULL, '', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`, `user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
