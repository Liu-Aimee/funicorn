/*
Navicat MySQL Data Transfer

Source Server         : 百度云
Source Server Version : 80026
Source Host           : 106.13.217.154:3306
Source Database       : funicorn_upms

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2022-05-05 13:54:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_tenant
-- ----------------------------
DROP TABLE IF EXISTS `app_tenant`;
CREATE TABLE `app_tenant` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用id',
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
  `tenant_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0:已禁用 1:已启用 2:申请开通中 3:拒绝开通',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_app_tenant` (`app_id`,`tenant_id`) USING BTREE COMMENT '应用与租户唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='应用与租户关系';

-- ----------------------------
-- Records of app_tenant
-- ----------------------------
INSERT INTO `app_tenant` VALUES ('1390225039803490306', '1', '权限中心', '-1', '伽麟科技', '1', 'admin', '2021-05-06 16:40:53', 'admin', '2021-06-24 14:14:52');
INSERT INTO `app_tenant` VALUES ('1461139986544640002', '3', '系统管理', '-1', '伽麟科技', '1', 'admin', '2021-11-18 09:11:34', null, null);
INSERT INTO `app_tenant` VALUES ('1461607060593205250', '3', '系统管理', '1461594232993931266', '浩联科技', '1', 'admin', '2021-11-19 16:07:33', null, null);
INSERT INTO `app_tenant` VALUES ('1464066919041462273', '1', '权限中心', '1460815761736802305', '友佳粉店', '1', 'admin', '2021-11-26 11:02:09', null, null);
INSERT INTO `app_tenant` VALUES ('1464066919121154049', '3', '系统管理', '1460815761736802305', '友佳粉店', '1', 'admin', '2021-11-26 11:02:09', null, null);
INSERT INTO `app_tenant` VALUES ('1466682171191308289', '1466679358201638913', '图表中心', '-1', '伽麟科技', '1', 'admin', '2021-12-03 16:14:14', null, null);
INSERT INTO `app_tenant` VALUES ('1470958065007255553', '1468044822102204417', '任务中心', '-1', '伽麟科技', '1', 'admin', '2021-12-15 11:25:06', null, null);
INSERT INTO `app_tenant` VALUES ('1508983367828017153', '1507282371406024706', '表单设计系统', '-1', '伽麟科技', '3', 'admin', '2022-03-30 09:44:05', 'admin', '2022-04-12 11:30:50');
INSERT INTO `app_tenant` VALUES ('1512601926237753346', '1507282855550341121', '智慧物联', '-1', '伽麟科技', '3', 'admin', '2022-04-09 09:22:57', 'admin', '2022-04-12 11:30:45');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '父ID',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用id',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型（menu/button）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限',
  `router` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'none' COMMENT '路由',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'show' COMMENT 'status: hidden->隐藏,  show->显示',
  `level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'public' COMMENT '级别 public/private',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否删除 0未删 1已删',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'none' COMMENT '图标',
  `sort` int DEFAULT '1' COMMENT '排序 升序',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单管理';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '0', '1', 'menu', '平台管理', null, null, 'show', 'public', '0', 'el-icon-setting', '0', 'admin', '2021-05-19 11:08:13', null, null);
INSERT INTO `menu` VALUES ('1460867698511413250', '0', '3', 'menu', '操作日志', null, '/system/log', 'show', 'public', '0', 'el-icon-warning-outline', '3', 'admin', '2021-11-17 15:09:36', 'admin', '2022-03-11 16:40:38');
INSERT INTO `menu` VALUES ('1461156485514760194', '0', '3', 'menu', '文件管理', null, '', 'show', 'public', '0', 'el-icon-folder', '1', 'admin', '2021-11-18 10:17:08', 'admin', '2022-03-18 16:02:27');
INSERT INTO `menu` VALUES ('1462676612898914306', '1460867698511413250', '3', 'button', '搜索', 'system:log:page', 'none', 'show', 'public', '0', 'none', '0', 'admin', '2021-11-22 14:57:34', 'admin', '2021-11-22 15:01:34');
INSERT INTO `menu` VALUES ('1462676892461858817', '1460867698511413250', '3', 'button', '删除', 'system:log:delete', 'none', 'show', 'public', '0', 'none', '1', 'admin', '2021-11-22 14:58:41', 'admin', '2021-11-22 15:05:33');
INSERT INTO `menu` VALUES ('1466212665268461569', '0', '3', 'menu', '数据字典', null, '/system/dictType', 'show', 'public', '0', 'el-icon-star-off', '2', 'admin', '2021-12-02 09:08:35', 'admin', '2022-03-11 16:40:42');
INSERT INTO `menu` VALUES ('1466213137299628033', '1466212665268461569', '3', 'menu', '字典类型', null, '/system/dictType', 'show', 'public', '1', 'none', '0', 'admin', '2021-12-02 09:10:28', 'admin', '2021-12-02 15:31:32');
INSERT INTO `menu` VALUES ('1466213276902842370', '1466212665268461569', '3', 'menu', '字典项', null, '/system/dictItem', 'show', 'public', '1', 'none', '1', 'admin', '2021-12-02 09:11:01', 'admin', '2021-12-02 09:40:15');
INSERT INTO `menu` VALUES ('1466682279249162242', '0', '1466679358201638913', 'menu', '图表广场', null, '/chart/market', 'hidden', 'public', '0', 'el-icon-platform-eleme', '0', 'admin', '2021-12-03 16:14:40', 'admin', '2022-03-14 15:04:04');
INSERT INTO `menu` VALUES ('1466682622506807298', '0', '1466679358201638913', 'menu', '图表管理', null, 'none', 'show', 'public', '0', 'el-icon-setting', '3', 'admin', '2021-12-03 16:16:02', 'admin', '2022-03-14 14:05:40');
INSERT INTO `menu` VALUES ('1466682801108660226', '1466682622506807298', '1466679358201638913', 'menu', '图表类型', null, '/chart/manage/chartType', 'show', 'public', '0', '', '0', 'admin', '2021-12-03 16:16:44', 'admin', '2021-12-03 16:22:24');
INSERT INTO `menu` VALUES ('1466683128142737410', '1466682622506807298', '1466679358201638913', 'menu', '数据源', null, '/chart/manage/dataSource', 'show', 'public', '0', 'none', '1', 'admin', '2021-12-03 16:18:02', 'admin', '2021-12-03 16:22:29');
INSERT INTO `menu` VALUES ('1466683398520156162', '0', '1466679358201638913', 'menu', '函数管理', null, 'none', 'show', 'public', '0', 'el-icon-phone', '2', 'admin', '2021-12-03 16:19:07', null, null);
INSERT INTO `menu` VALUES ('1466683500265582594', '1466683398520156162', '1466679358201638913', 'menu', 'SQL', null, '/chart/function/sql', 'show', 'public', '0', 'none', '0', 'admin', '2021-12-03 16:19:31', 'admin', '2021-12-03 16:22:35');
INSERT INTO `menu` VALUES ('1466683576895516673', '1466683398520156162', '1466679358201638913', 'menu', 'HTTP', null, '/chart/function/http', 'show', 'public', '0', 'none', '1', 'admin', '2021-12-03 16:19:49', 'admin', '2021-12-03 16:22:42');
INSERT INTO `menu` VALUES ('1466683830575411202', '0', '1466679358201638913', 'menu', '数据集', null, '/chart/dataset', 'show', 'public', '0', 'el-icon-s-help', '4', 'admin', '2021-12-03 16:20:50', 'admin', '2022-03-14 14:05:43');
INSERT INTO `menu` VALUES ('1466683986226032642', '0', '1466679358201638913', 'menu', '我的图表', null, '/chart/container', 'show', 'public', '0', 'el-icon-picture', '1', 'admin', '2021-12-03 16:21:27', 'admin', '2022-03-14 14:05:17');
INSERT INTO `menu` VALUES ('1470958200273559553', '0', '1468044822102204417', 'menu', '模型管理', null, '/workflow/model', 'show', 'public', '0', 'el-icon-s-help', '0', 'admin', '2021-12-15 11:25:39', null, null);
INSERT INTO `menu` VALUES ('1499626169668497409', '1507165478864834561', '1', 'menu', '组织机构', null, '/tenant/organization', 'show', 'public', '0', null, '0', 'admin', '2022-03-04 14:01:55', 'admin', '2022-04-01 16:59:37');
INSERT INTO `menu` VALUES ('1501105395827478530', '0', '3', 'menu', '服务网关', null, null, 'show', 'public', '0', 'el-icon-s-promotion', '0', 'admin', '2022-03-08 15:59:50', null, null);
INSERT INTO `menu` VALUES ('1501105574324473857', '1501105395827478530', '3', 'menu', '路由', null, '/gateway/route', 'show', 'public', '0', 'none', '0', 'admin', '2022-03-08 16:00:33', null, null);
INSERT INTO `menu` VALUES ('1501105647577993218', '1501105395827478530', '3', 'menu', '断言', null, '/gateway/predicate', 'show', 'public', '0', 'none', '1', 'admin', '2022-03-08 16:00:50', null, null);
INSERT INTO `menu` VALUES ('1501105734291034113', '1501105395827478530', '3', 'menu', '过滤器', null, '/system/gateway/filter', 'show', 'public', '0', 'none', '2', 'admin', '2022-03-08 16:01:11', null, null);
INSERT INTO `menu` VALUES ('1504729585520508930', '1461156485514760194', '3', 'menu', '文件列表', null, '/system/file/list', 'show', 'public', '0', 'none', '0', 'admin', '2022-03-18 16:01:04', 'admin', '2022-03-18 16:08:19');
INSERT INTO `menu` VALUES ('1504729854438309889', '1461156485514760194', '3', 'menu', '桶列表', null, '/system/file/bucket', 'show', 'public', '1', 'none', '1', 'admin', '2022-03-18 16:02:08', 'admin', '2022-03-18 16:02:15');
INSERT INTO `menu` VALUES ('1504729854924849153', '1461156485514760194', '3', 'menu', '桶管理', null, '/system/file/bucket', 'show', 'public', '0', 'none', '1', 'admin', '2022-03-18 16:02:09', 'admin', '2022-03-18 16:03:11');
INSERT INTO `menu` VALUES ('1507165478864834561', '0', '1', 'menu', '租户管理', null, 'none', 'show', 'public', '0', 'el-icon-house', '1', 'admin', '2022-03-25 09:20:27', null, null);
INSERT INTO `menu` VALUES ('1507184949604143105', '1', '1', 'menu', '我的应用', null, '/portal/myapps', 'show', 'public', '0', 'none', '2', 'admin', '2022-03-25 10:37:49', 'admin', '2022-03-25 10:55:05');
INSERT INTO `menu` VALUES ('1507185007267434497', '1', '1', 'menu', '应用市场', '', '/portal/appmarket', 'show', 'public', '0', 'none', '0', 'admin', '2022-03-25 10:38:03', 'admin', '2022-03-25 10:47:48');
INSERT INTO `menu` VALUES ('1509817615715700737', '1507165478864834561', '1', 'menu', '角色管理', null, '/tenant/role', 'show', 'public', '0', '', '2', 'admin', '2022-04-01 16:59:05', 'admin', '2022-04-01 17:09:29');
INSERT INTO `menu` VALUES ('2', '1', '1', 'menu', '我的租户', null, '/portal/tenants', 'show', 'public', '0', null, '1', 'admin', '2021-05-19 11:08:13', 'admin', '2022-03-25 11:01:55');
INSERT INTO `menu` VALUES ('3', '1', '1', 'menu', '应用管理', 'upms:app:page', '/portal/apps', 'show', 'public', '0', null, '3', 'admin', '2021-05-19 11:08:13', 'admin', '2022-03-25 10:50:41');
INSERT INTO `menu` VALUES ('4', '1', '1', 'menu', '菜单管理', null, '/portal/menu', 'show', 'public', '0', null, '4', 'admin', '2021-05-19 11:08:13', 'admin', '2022-03-25 10:38:51');
INSERT INTO `menu` VALUES ('5', '1507165478864834561', '1', 'menu', '用户信息', null, '/tenant/user', 'show', 'public', '0', null, '1', 'admin', '2021-05-19 11:08:13', 'admin', '2022-04-01 16:59:45');

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称',
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用标识',
  `client_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用密钥',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回调地址',
  `resource_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '可访问资源列表',
  `scope` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权范围',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权模式:authorization_code 授权码,password 密码,refresh_token 刷新access_token,implicit 简化,client_credentials 客户端',
  `authorities` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `access_token_validity` int DEFAULT '7200' COMMENT '令牌有效期 单位秒 默认7200',
  `refresh_token_validity` int DEFAULT '14400' COMMENT '刷新令牌有效期 单位秒 默认 14400',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '附加信息',
  `autoapprove` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'true' COMMENT '自动批准 true/false',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'logo地址',
  `router` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由地址',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用访问地址',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'private' COMMENT '应用级别，private 私有/public 公开',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标识 0 未删 1已删',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='应用管理';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('1', '权限中心', 'upms-client', '$2a$10$iRroviMcIwMhWCN7WlrsIO8VBudjIi1pAzKg7s/iOZnqa0bMFXmnG', 'http://cloud.funicorn.cn/index,http://test.cloud.funicorn.cn/index,http://dev.cloud.funicorn.cn/index,http://dev.cloud.funicorn.cn:9090/#/index,http://test.cloud.funicorn.cn:9090/#/index', null, 'all', 'password,refresh_token,authorization_code', null, '7200', '14400', null, 'true', 'http://192.168.140.140:9000/funicorn-public/1511585278257065985.jpg', '/project/upms', 'http://test.cloud.funicorn.cn:9090/', '权限管理中心，管理权限、菜单、角色、用户、机构等等。', 'public', '0', 'admin', 'admin', '2021-04-30 14:45:24', '2022-04-06 14:03:12');
INSERT INTO `oauth_client_details` VALUES ('1466679358201638913', '图表中心', 'chart-client', '$2a$10$13txo2wJx3s8zV.hNLt9oeIO563CcLG/yiNtPOszdwqgTXSUzPz7y', 'http://192.168.200.244/index', null, 'all', 'password,refresh_token,authorization_code', null, '7200', '14400', null, 'true', null, '/chart', '', '将数据以图表的信息展示出来', 'public', '0', 'admin', null, '2021-12-03 16:03:03', null);
INSERT INTO `oauth_client_details` VALUES ('1468044822102204417', '任务中心', 'task-client', '$2a$10$7iNuBF8fhWdOMYKkzr8vZ.QYuZjGUh97KXsSNbZgHP/QEg7YzYCBm', 'http://192.168.200.244/index', null, 'all', 'password,refresh_token,authorization_code', null, '7200', '14400', null, 'true', null, '/workflow', '', '任务中心', 'public', '0', 'admin', 'admin', '2021-12-07 10:28:55', '2021-12-15 11:26:08');
INSERT INTO `oauth_client_details` VALUES ('1507282371406024706', '表单设计系统', 'form-design', '$2a$10$cntqeLw3yufIWICnxSCFnOke9Tv.rH3K5Qj89H/WsUBZS/srp8rqO', 'http://cloud.funicorn.cn/index', null, 'all', 'password,refresh_token,authorization_code', null, '7200', '14400', null, 'true', null, '/design', '', '自定义表单', 'public', '0', 'admin', null, '2022-03-25 17:04:56', null);
INSERT INTO `oauth_client_details` VALUES ('1507282855550341121', '智慧物联', 'iot-cloud', '$2a$10$1P/ecesYhvQtGwXdDbLjiO3yCXMSHtBZfeIkwr9nfwnSI7vNq0UzS', 'http://cloud.funicorn.cn/index', null, 'all', 'password,refresh_token,authorization_code', null, '7200', '14400', null, 'true', null, '/iot', '', '物联网', 'public', '0', 'admin', null, '2022-03-25 17:06:51', null);
INSERT INTO `oauth_client_details` VALUES ('1507285401509646337', '消息中心', 'message-client', '$2a$10$G38j2qHYdH5TzR7TKJvte.HYRa.yFMFgTlNl2gXFI7BMlbPABK2cm', 'http://cloud.funicorn.cn/index', null, 'all', 'password,refresh_token,authorization_code', null, '7200', '14400', null, 'true', null, '/message', '', '消息中心', 'public', '0', 'admin', null, '2022-03-25 17:16:58', null);
INSERT INTO `oauth_client_details` VALUES ('3', '系统管理', 'system-client', '$2a$10$iRroviMcIwMhWCN7WlrsIO8VBudjIi1pAzKg7s/iOZnqa0bMFXmnG', 'http://dev.cloud.funicorn.cn:9091/#/index,http://test.cloud.funicorn.cn:9091/#/index', null, 'all', 'password,refresh_token,authorization_code', null, '7200', '7200', null, 'true', null, '/project/system', 'http://test.cloud.funicorn.cn:9091/', '系统管理', 'public', '0', 'admin', null, '2021-04-30 14:45:27', null);

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称·',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `sort` int DEFAULT '0' COMMENT '排序 升序',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
  `is_delete` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否删除:1删除,0未删',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织机构管理';

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('1499642264378961922', '0', '浩联集团', null, '0', '1461594232993931266', '0', 'admin', '2022-03-04 15:05:52', null, null);
INSERT INTO `organization` VALUES ('1499918323712299010', '1499642264378961922', '浩联科技', null, '0', '1461594232993931266', '0', 'admin', '2022-03-05 09:22:50', null, null);
INSERT INTO `organization` VALUES ('1499934125781979138', '0', '友佳集团', null, '0', '1460815761736802305', '0', 'admin', '2022-03-05 10:25:38', null, null);
INSERT INTO `organization` VALUES ('1499934241725124610', '1499934125781979138', '友佳粉店长沙分店', null, '0', '1460815761736802305', '0', 'admin', '2022-03-05 10:26:05', null, null);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '-1' COMMENT '租户id 默认-1',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否删除 0 未删除 1已删除',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人账号',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人账号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色管理';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', 'ROLE_super_admin', '-1', '0', 'admin', null, '2021-04-29 10:27:38', null);
INSERT INTO `role` VALUES ('1463071067627900930', '租户管理员', 'ROLE_tenant_admin', '1460815761736802305', '0', 'admin', null, '2021-11-23 17:05:00', null);
INSERT INTO `role` VALUES ('1509816742176395266', '租户管理员', 'ROLE_tenant_admin', '-1', '0', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role` VALUES ('1510090976345763841', '店长', 'ROLE_shopowner', '1460815761736802305', '0', 'admin', null, '2022-04-02 11:05:20', null);

-- ----------------------------
-- Table structure for role_app
-- ----------------------------
DROP TABLE IF EXISTS `role_app`;
CREATE TABLE `role_app` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用id',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_role_app` (`role_id`,`app_id`,`tenant_id`) USING BTREE COMMENT '角色与应用唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色与应用关系';

-- ----------------------------
-- Records of role_app
-- ----------------------------
INSERT INTO `role_app` VALUES ('1', '1', '1', '-1', 'admin', null, '2021-05-07 09:49:10', null);
INSERT INTO `role_app` VALUES ('1461139986871795714', '1', '3', '-1', 'admin', null, '2021-11-18 09:11:34', null);
INSERT INTO `role_app` VALUES ('1466682279458877442', '1', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:14:40', null);
INSERT INTO `role_app` VALUES ('1470958200495857665', '1', '1468044822102204417', '-1', 'admin', null, '2021-12-15 11:25:39', null);
INSERT INTO `role_app` VALUES ('1509816742411276290', '1509816742176395266', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_app` VALUES ('1509816742419664897', '1509816742176395266', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_app` VALUES ('1509816742419664898', '1509816742176395266', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_app` VALUES ('1509816742428053506', '1509816742176395266', '1468044822102204417', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_app` VALUES ('1509816742428053507', '1509816742176395266', '1507282371406024706', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_app` VALUES ('1509817615879278593', '1', '1507282371406024706', '-1', 'admin', null, '2022-04-01 16:59:05', null);
INSERT INTO `role_app` VALUES ('1510171865515851777', '1510090976345763841', '3', '-1', 'admin', null, '2022-04-02 16:26:45', null);
INSERT INTO `role_app` VALUES ('1511532072623976449', '1463071067627900930', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_app` VALUES ('1511532072632365057', '1463071067627900930', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_app` VALUES ('1511536374146961409', '1463071067627900930', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用id',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户ID',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色与菜单关系';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '1', '1', '-1', 'admin', null, '2021-11-08 11:23:47', null);
INSERT INTO `role_menu` VALUES ('1461139987068928002', '1', '1460867698511413250', '3', '-1', 'admin', null, '2021-11-18 09:11:34', null);
INSERT INTO `role_menu` VALUES ('1461156485716086785', '1', '1461156485514760194', '3', '-1', 'admin', null, '2021-11-18 10:17:08', null);
INSERT INTO `role_menu` VALUES ('1462675684812685314', '1', '1462675684250648577', '3', '-1', 'admin', null, '2021-11-22 14:53:53', null);
INSERT INTO `role_menu` VALUES ('1462675910369771522', '1', '1462675910139084801', '3', '-1', 'admin', null, '2021-11-22 14:54:47', null);
INSERT INTO `role_menu` VALUES ('1462676613112823809', '1', '1462676612898914306', '3', '-1', 'admin', null, '2021-11-22 14:57:34', null);
INSERT INTO `role_menu` VALUES ('1462676892684156929', '1', '1462676892461858817', '3', '-1', 'admin', null, '2021-11-22 14:58:41', null);
INSERT INTO `role_menu` VALUES ('1464066919481864194', '1463071067627900930', '1460867698511413250', '3', '1460815761736802305', 'admin', null, '2021-11-26 11:02:09', null);
INSERT INTO `role_menu` VALUES ('1464066919490252802', '1463071067627900930', '1461156485514760194', '3', '1460815761736802305', 'admin', null, '2021-11-26 11:02:09', null);
INSERT INTO `role_menu` VALUES ('1464066919490252803', '1463071067627900930', '1462676612898914306', '3', '1460815761736802305', 'admin', null, '2021-11-26 11:02:09', null);
INSERT INTO `role_menu` VALUES ('1464066919490252804', '1463071067627900930', '1462676892461858817', '3', '1460815761736802305', 'admin', null, '2021-11-26 11:02:09', null);
INSERT INTO `role_menu` VALUES ('1465944402122858498', '1', '1465944399971180545', '3', '-1', 'admin', null, '2021-12-01 15:22:36', null);
INSERT INTO `role_menu` VALUES ('1466212665696280578', '1', '1466212665268461569', '3', '-1', 'admin', null, '2021-12-02 09:08:35', null);
INSERT INTO `role_menu` VALUES ('1466212666430283777', '1463071067627900930', '1466212665268461569', '3', '1460815761736802305', 'admin', null, '2021-12-02 09:08:35', null);
INSERT INTO `role_menu` VALUES ('1466213137542897665', '1', '1466213137299628033', '3', '-1', 'admin', null, '2021-12-02 09:10:28', null);
INSERT INTO `role_menu` VALUES ('1466213137802944513', '1463071067627900930', '1466213137299628033', '3', '1460815761736802305', 'admin', null, '2021-12-02 09:10:28', null);
INSERT INTO `role_menu` VALUES ('1466213277108363265', '1', '1466213276902842370', '3', '-1', 'admin', null, '2021-12-02 09:11:01', null);
INSERT INTO `role_menu` VALUES ('1466213277343244289', '1463071067627900930', '1466213276902842370', '3', '1460815761736802305', 'admin', null, '2021-12-02 09:11:01', null);
INSERT INTO `role_menu` VALUES ('1466682279702147074', '1', '1466682279249162242', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:14:40', null);
INSERT INTO `role_menu` VALUES ('1466682622800408578', '1', '1466682622506807298', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:16:02', null);
INSERT INTO `role_menu` VALUES ('1466682801440010242', '1', '1466682801108660226', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:16:44', null);
INSERT INTO `role_menu` VALUES ('1466683128436338689', '1', '1466683128142737410', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:18:02', null);
INSERT INTO `role_menu` VALUES ('1466683398885060609', '1', '1466683398520156162', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:19:07', null);
INSERT INTO `role_menu` VALUES ('1466683500580155393', '1', '1466683500265582594', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:19:31', null);
INSERT INTO `role_menu` VALUES ('1466683577323335681', '1', '1466683576895516673', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:19:49', null);
INSERT INTO `role_menu` VALUES ('1466683830877401090', '1', '1466683830575411202', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:20:50', null);
INSERT INTO `role_menu` VALUES ('1466683986548994049', '1', '1466683986226032642', '1466679358201638913', '-1', 'admin', null, '2021-12-03 16:21:27', null);
INSERT INTO `role_menu` VALUES ('1470958200965619714', '1', '1470958200273559553', '1468044822102204417', '-1', 'admin', null, '2021-12-15 11:25:39', null);
INSERT INTO `role_menu` VALUES ('1499626171216195585', '1', '1499626169668497409', '1', '-1', 'admin', null, '2022-03-04 14:01:56', null);
INSERT INTO `role_menu` VALUES ('1501105397937213441', '1', '1501105395827478530', '3', '-1', 'admin', null, '2022-03-08 15:59:51', null);
INSERT INTO `role_menu` VALUES ('1501105398553776129', '1463071067627900930', '1501105395827478530', '3', '1460815761736802305', 'admin', null, '2022-03-08 15:59:51', null);
INSERT INTO `role_menu` VALUES ('1501105574748098562', '1', '1501105574324473857', '3', '-1', 'admin', null, '2022-03-08 16:00:33', null);
INSERT INTO `role_menu` VALUES ('1501105575146557442', '1463071067627900930', '1501105574324473857', '3', '1460815761736802305', 'admin', null, '2022-03-08 16:00:33', null);
INSERT INTO `role_menu` VALUES ('1501105647984840706', '1', '1501105647577993218', '3', '-1', 'admin', null, '2022-03-08 16:00:50', null);
INSERT INTO `role_menu` VALUES ('1501105648261664770', '1463071067627900930', '1501105647577993218', '3', '1460815761736802305', 'admin', null, '2022-03-08 16:00:50', null);
INSERT INTO `role_menu` VALUES ('1501105734639161346', '1', '1501105734291034113', '3', '-1', 'admin', null, '2022-03-08 16:01:11', null);
INSERT INTO `role_menu` VALUES ('1501105734957928450', '1463071067627900930', '1501105734291034113', '3', '1460815761736802305', 'admin', null, '2022-03-08 16:01:11', null);
INSERT INTO `role_menu` VALUES ('1504729587076595713', '1', '1504729585520508930', '3', '-1', 'admin', null, '2022-03-18 16:01:05', null);
INSERT INTO `role_menu` VALUES ('1504729587688964098', '1463071067627900930', '1504729585520508930', '3', '1460815761736802305', 'admin', null, '2022-03-18 16:01:05', null);
INSERT INTO `role_menu` VALUES ('1504729854941626369', '1', '1504729854438309889', '3', '-1', 'admin', null, '2022-03-18 16:02:09', null);
INSERT INTO `role_menu` VALUES ('1504729855310725122', '1463071067627900930', '1504729854438309889', '3', '1460815761736802305', 'admin', null, '2022-03-18 16:02:09', null);
INSERT INTO `role_menu` VALUES ('1504729855411388418', '1', '1504729854924849153', '3', '-1', 'admin', null, '2022-03-18 16:02:09', null);
INSERT INTO `role_menu` VALUES ('1504729855948259330', '1463071067627900930', '1504729854924849153', '3', '1460815761736802305', 'admin', null, '2022-03-18 16:02:09', null);
INSERT INTO `role_menu` VALUES ('1507165480072794114', '1', '1507165478864834561', '1', '-1', 'admin', null, '2022-03-25 09:20:27', null);
INSERT INTO `role_menu` VALUES ('1507184950010990594', '1', '1507184949604143105', '1', '-1', 'admin', null, '2022-03-25 10:37:49', null);
INSERT INTO `role_menu` VALUES ('1507185007602978817', '1', '1507185007267434497', '1', '-1', 'admin', null, '2022-03-25 10:38:03', null);
INSERT INTO `role_menu` VALUES ('1509816743006867458', '1509816742176395266', '1', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743015256066', '1509816742176395266', '1460867698511413250', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743015256067', '1509816742176395266', '1461156485514760194', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743015256068', '1509816742176395266', '1462676612898914306', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743048810498', '1509816742176395266', '1462676892461858817', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743048810499', '1509816742176395266', '1466212665268461569', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743048810500', '1509816742176395266', '1466682279249162242', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743048810501', '1509816742176395266', '1466682622506807298', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199106', '1509816742176395266', '1466682801108660226', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199107', '1509816742176395266', '1466683128142737410', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199108', '1509816742176395266', '1466683398520156162', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199109', '1509816742176395266', '1466683500265582594', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199110', '1509816742176395266', '1466683576895516673', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199111', '1509816742176395266', '1466683830575411202', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199112', '1509816742176395266', '1466683986226032642', '1466679358201638913', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743057199113', '1509816742176395266', '1470958200273559553', '1468044822102204417', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587714', '1509816742176395266', '1499626169668497409', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587715', '1509816742176395266', '1501105395827478530', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587716', '1509816742176395266', '1501105574324473857', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587717', '1509816742176395266', '1501105647577993218', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587718', '1509816742176395266', '1501105734291034113', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587719', '1509816742176395266', '1504729585520508930', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587720', '1509816742176395266', '1504729854924849153', '3', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587721', '1509816742176395266', '1507165478864834561', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587722', '1509816742176395266', '1507184949604143105', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587723', '1509816742176395266', '1507185007267434497', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587724', '1509816742176395266', '2', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587725', '1509816742176395266', '3', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743065587726', '1509816742176395266', '4', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509816743073976321', '1509816742176395266', '5', '1', '-1', 'admin', null, '2022-04-01 16:55:37', null);
INSERT INTO `role_menu` VALUES ('1509817616269348865', '1', '1509817615715700737', '1', '-1', 'admin', null, '2022-04-01 16:59:05', null);
INSERT INTO `role_menu` VALUES ('1509817616785248257', '1509816742176395266', '1509817615715700737', '1', '-1', 'admin', null, '2022-04-01 16:59:06', null);
INSERT INTO `role_menu` VALUES ('1510171865608126465', '1510090976345763841', '1461156485514760194', '3', '-1', 'admin', null, '2022-04-02 16:26:45', null);
INSERT INTO `role_menu` VALUES ('1510171865616515073', '1510090976345763841', '1504729585520508930', '3', '-1', 'admin', null, '2022-04-02 16:26:45', null);
INSERT INTO `role_menu` VALUES ('1510171865616515074', '1510090976345763841', '1504729854924849153', '3', '-1', 'admin', null, '2022-04-02 16:26:45', null);
INSERT INTO `role_menu` VALUES ('1511532072640753666', '1463071067627900930', '1507185007267434497', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072640753667', '1463071067627900930', '2', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142274', '1463071067627900930', '1507184949604143105', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142275', '1463071067627900930', '1507165478864834561', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142276', '1463071067627900930', '1499626169668497409', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142277', '1463071067627900930', '5', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142278', '1463071067627900930', '1509817615715700737', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142279', '1463071067627900930', '1', '1', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142280', '1463071067627900930', '1460867698511413250', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142281', '1463071067627900930', '1462676612898914306', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142282', '1463071067627900930', '1462676892461858817', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142283', '1463071067627900930', '1461156485514760194', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142284', '1463071067627900930', '1504729585520508930', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142285', '1463071067627900930', '1504729854924849153', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142286', '1463071067627900930', '1466212665268461569', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072649142287', '1463071067627900930', '1501105395827478530', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072657530881', '1463071067627900930', '1501105574324473857', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072657530882', '1463071067627900930', '1501105647577993218', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511532072657530883', '1463071067627900930', '1501105734291034113', '3', '-1', 'admin', null, '2022-04-06 10:31:44', null);
INSERT INTO `role_menu` VALUES ('1511536374222458881', '1463071067627900930', '1507185007267434497', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('1511536374222458882', '1463071067627900930', '2', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('1511536374222458883', '1463071067627900930', '1507184949604143105', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('1511536374222458884', '1463071067627900930', '1507165478864834561', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('1511536374230847490', '1463071067627900930', '1499626169668497409', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('1511536374230847491', '1463071067627900930', '5', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('1511536374230847492', '1463071067627900930', '1509817615715700737', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('1511536374230847493', '1463071067627900930', '1', '1', '1460815761736802305', 'admin', null, '2022-04-06 10:48:49', null);
INSERT INTO `role_menu` VALUES ('2', '1', '2', '1', '-1', 'admin', null, '2021-11-08 11:23:47', null);
INSERT INTO `role_menu` VALUES ('3', '1', '3', '1', '-1', 'admin', null, '2021-11-08 11:23:47', null);
INSERT INTO `role_menu` VALUES ('4', '1', '4', '1', '-1', 'admin', null, '2021-11-08 11:23:47', null);
INSERT INTO `role_menu` VALUES ('5', '1', '5', '1', '-1', 'admin', null, '2021-11-08 11:23:47', null);

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键 租户id',
  `tenant_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'logo地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否删除，1删除',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人账号',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人账号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='租户管理';

-- ----------------------------
-- Records of tenant
-- ----------------------------
INSERT INTO `tenant` VALUES ('-1', '伽麟科技', 'http://192.168.140.140:9000/funicorn-public/1511550402120183809.jpg', '超级租户，内部自用', '0', 'admin', 'admin', '2021-04-26 08:29:39', '2022-04-06 11:44:33');
INSERT INTO `tenant` VALUES ('1460815761736802305', '友佳粉店', 'http://192.168.140.140:9000/funicorn-public/1511550420633841665.jpg', '全国连锁', '0', 'admin', 'admin', '2021-11-17 11:43:13', '2022-04-06 11:44:37');
INSERT INTO `tenant` VALUES ('1461594232993931266', '浩联科技', 'http://192.168.140.140:9000/funicorn-public/1511550447364141057.png', '浩联科技', '0', 'admin', 'admin', '2021-11-19 15:16:35', '2022-04-06 11:44:44');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `id_type` int DEFAULT '0' COMMENT '证件类型 0身份证 1港澳通行证 2学生证 3护照 4士官证 5驾驶证',
  `id_card` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证件号码',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系地址',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `head_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
  `enabled` int NOT NULL DEFAULT '0' COMMENT '账号是否可用:0可用,1不可用',
  `history_pwd` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '历史密码记录，保存最近3个',
  `expire_time` datetime DEFAULT NULL COMMENT '密码失效时间',
  `locked` int NOT NULL DEFAULT '0' COMMENT '账号是否被锁定:0正常,1锁定',
  `is_delete` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '软删标识 0未删 1已删',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_index_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息管理';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '$2a$10$3g2/hXilYmEe2tygbmmsXu3XfEwQtOuOr6wqsYL6Me4l8BBLrq4GC', '407770627@qq.com', '18318676273', '0', '430321199010100101', '湖南省长沙市', '超级管理员', null, '1', null, '2022-05-30 12:25:43', '0', '0', 'admin', '2021-04-27 12:26:03', '2022-02-23 14:51:22', 'admin');
INSERT INTO `user` VALUES ('1463071067413991425', 'yjadmin', '$2a$10$uIq7GFEKokKEQ7V4LKB0Z.PMvkLjTsxVSDzvVWBSNou5aEiBwSL5y', '407770628@qq.com', '13924627281', '0', '4303211111111111111', '湖南省长沙市雨花区', '友佳粉店管理员', null, '1', null, '2022-02-23 17:05:00', '0', '0', 'admin', '2021-11-23 17:05:00', '2022-04-01 16:55:37', 'admin');

-- ----------------------------
-- Table structure for user_org
-- ----------------------------
DROP TABLE IF EXISTS `user_org`;
CREATE TABLE `user_org` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `org_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织机构id',
  `org_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织机构名称',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与机构关系';

-- ----------------------------
-- Records of user_org
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人账号',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人账号',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与角色关系';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1', '-1', 'admin', null, '2021-04-29 10:28:53', null);
INSERT INTO `user_role` VALUES ('1463071067686621185', '1463071067413991425', '1463071067627900930', '1460815761736802305', 'admin', null, '2021-11-23 17:05:00', null);
INSERT INTO `user_role` VALUES ('1509816742235115521', '1463071067413991425', '1509816742176395266', '-1', 'admin', null, '2022-04-01 16:55:37', null);

-- ----------------------------
-- Table structure for user_tenant
-- ----------------------------
DROP TABLE IF EXISTS `user_tenant`;
CREATE TABLE `user_tenant` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名账号',
  `type` int NOT NULL DEFAULT '0' COMMENT '用户类别,-1:超级管理员 0:普通用户 1:租户管理员',
  `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
  `tenant_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_t_index` (`user_id`,`tenant_id`) USING BTREE COMMENT '用户与租户唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与租户关系';

-- ----------------------------
-- Records of user_tenant
-- ----------------------------
INSERT INTO `user_tenant` VALUES ('1', '1', 'admin', '-1', '-1', '伽麟科技', 'admin', '2021-10-26 12:35:26', null, null);
INSERT INTO `user_tenant` VALUES ('1463071067535626242', '1463071067413991425', 'yjadmin', '1', '1460815761736802305', '友佳粉店', 'admin', '2021-11-23 17:05:00', null, null);
INSERT INTO `user_tenant` VALUES ('1509816742058954753', '1463071067413991425', 'yjadmin', '1', '-1', '伽麟科技', 'admin', '2022-04-01 16:55:37', null, null);
