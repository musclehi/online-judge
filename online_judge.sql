/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost:3306
 Source Schema         : online_judge

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : 65001

 Date: 03/06/2018 18:32:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Schema for online_judge
-- ----------------------------
DROP SCHEMA IF EXISTS `online_judge`;
CREATE SCHEMA `online_judge` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- ----------------------------
-- Table structure for contest
-- ----------------------------
DROP TABLE IF EXISTS `contest`;
CREATE TABLE `contest`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '比赛名称',
  `start_time` datetime NOT NULL COMMENT '比赛开始时间',
  `end_time` datetime NOT NULL COMMENT '比赛结束时间',
  `problem_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目id数组json串',
  `creator` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者的用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '比赛' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contest_contestant
-- ----------------------------
DROP TABLE IF EXISTS `contest_contestant`;
CREATE TABLE `contest_contestant`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contest_id` int(11) NOT NULL COMMENT '比赛id',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参赛者的用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '比赛参赛者' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contest_submission
-- ----------------------------
DROP TABLE IF EXISTS `contest_submission`;
CREATE TABLE `contest_submission`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contest_id` int(11) NOT NULL COMMENT '比赛id',
  `problem_id` int(11) NOT NULL COMMENT '题目id',
  `submission_id` int(11) NOT NULL COMMENT '提交记录id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '比赛提交记录关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for description
-- ----------------------------
DROP TABLE IF EXISTS `description`;
CREATE TABLE `description`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目描述',
  `input` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目输入描述',
  `output` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目输出描述',
  `sample_input` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目输入样例',
  `sample_output` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目输出样例',
  `extension` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '扩充字段（Json串）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题目描述' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '题目标题',
  `description_id` int(10) NOT NULL COMMENT '题目描述id',
  `tag` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '题目标签',
  `time_limit` int(10) NOT NULL DEFAULT 1000 COMMENT '题目时间限制（单位：ms）',
  `memory_limit` int(10) NOT NULL DEFAULT 32768 COMMENT '题目空间限制（单位：KB）',
  `public_status` tinyint(3) NOT NULL DEFAULT 1 COMMENT '公开状态（0：私密，1：公开）',
  `original_oj` smallint(5) NOT NULL COMMENT '原始OJ',
  `original_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原始题号',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目链接',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `problem_tag`;
CREATE TABLE `problem_tag`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `problem_id` int(11) NOT NULL COMMENT '题目主键',
  `tag_id` int(11) NOT NULL COMMENT '标签主键',
  `updator` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人的用户名',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `deleted_status` tinyint(3) NOT NULL COMMENT '逻辑删除状态（0：未删除，1：已删除）\r\n',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题目标签关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for submission
-- ----------------------------
DROP TABLE IF EXISTS `submission`;
CREATE TABLE `submission`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `problem_id` int(10) NOT NULL COMMENT '题目id',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交的用户名',
  `language` int(10) NOT NULL COMMENT '提交的语言',
  `submitted_time` datetime NOT NULL COMMENT '提交时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `time` int(10) NOT NULL COMMENT '运行时间（单位：ms）',
  `memory` int(10) NOT NULL COMMENT '运行内存（单位：KB）',
  `result` tinyint(3) NOT NULL COMMENT '评测结果',
  `visible_status` tinyint(3) NOT NULL COMMENT '提交记录可见状态（0：私密，1：全部可见，2：仅比赛内可见）',
  `public_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '源代码公开状态（0：私密，1：公开）',
  `remote_account_id` int(10) NOT NULL COMMENT '原OJ提交帐号id',
  `remote_submission_id` int(10) NOT NULL COMMENT '原OJ提交id',
  `source` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '源代码',
  `extension` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '扩充字段（Json串）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提交记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `parent_id` int(11) NOT NULL COMMENT '上级标签的主键',
  `updator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人的用户名',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for test_case
-- ----------------------------
DROP TABLE IF EXISTS `test_case`;
CREATE TABLE `test_case`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `problem_id` int(10) NOT NULL COMMENT '题目id',
  `score` tinyint(3) NOT NULL COMMENT '测试点分数',
  `input` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '输入',
  `output` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标准输出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试用例' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `nickname` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'Pa55word', 'admin@163.com', '管理员', '2018-04-22 22:23:33');

-- ----------------------------
-- Table structure for user_privilege
-- ----------------------------
DROP TABLE IF EXISTS `user_privilege`;
CREATE TABLE `user_privilege`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `privilege` int(11) NOT NULL COMMENT '权限标识',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  `updator` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改人的用户名',
  `deleted_status` tinyint(3) NOT NULL COMMENT '逻辑删除状态（0：未删除，1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_privilege
-- ----------------------------
INSERT INTO `user_privilege` VALUES (1, 'admin', 2, '2018-06-01 00:00:00', 'admin', 0);
INSERT INTO `user_privilege` VALUES (2, 'admin', 3, '2018-06-01 00:00:00', 'admin', 0);
INSERT INTO `user_privilege` VALUES (3, 'admin', 4, '2018-06-01 00:00:00', 'admin', 0);
INSERT INTO `user_privilege` VALUES (4, 'admin', 5, '2018-06-01 00:00:00', 'admin', 0);

SET FOREIGN_KEY_CHECKS = 1;
