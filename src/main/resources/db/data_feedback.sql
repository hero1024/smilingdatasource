/*
 Navicat Premium Data Transfer

 Source Server         : 10.20.13.195
 Source Server Type    : MySQL
 Source Server Version : 50738 (5.7.38-230102-log)
 Source Host           : 10.20.13.195:3306
 Source Schema         : graphdb

 Target Server Type    : MySQL
 Target Server Version : 50738 (5.7.38-230102-log)
 File Encoding         : 65001

 Date: 28/04/2024 09:56:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_feedback
-- ----------------------------
DROP TABLE IF EXISTS `data_feedback`;
CREATE TABLE `data_feedback`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question_id` bigint(20) NULL DEFAULT NULL,
  `answer_id` bigint(20) NULL DEFAULT NULL,
  `feedback` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bad_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_feedback
-- ----------------------------
INSERT INTO `data_feedback` VALUES (2, 1, 1, 'good', '', '2024-03-19 10:01:08', NULL);
INSERT INTO `data_feedback` VALUES (3, 1, 1, 'good', '', '2024-04-02 10:38:03', NULL);
INSERT INTO `data_feedback` VALUES (9, 26, 26, 'good', NULL, '2024-04-08 09:25:43', NULL);
INSERT INTO `data_feedback` VALUES (10, 29, 29, 'bad', '内容不准确;没有帮助', '2024-04-10 19:05:21', NULL);
INSERT INTO `data_feedback` VALUES (11, 143, 143, 'bad', '还不够好', '2024-04-16 15:11:00', NULL);
INSERT INTO `data_feedback` VALUES (12, 152, 152, 'good', NULL, '2024-04-16 15:23:56', NULL);
INSERT INTO `data_feedback` VALUES (13, 153, 153, 'good', NULL, '2024-04-16 15:26:45', NULL);
INSERT INTO `data_feedback` VALUES (14, 366, 366, 'good', NULL, '2024-04-19 14:50:25', NULL);
INSERT INTO `data_feedback` VALUES (15, 364, 364, 'bad', NULL, '2024-04-19 14:50:32', '2024-04-19 14:50:42');
INSERT INTO `data_feedback` VALUES (17, 367, 367, 'good', NULL, '2024-04-19 14:52:22', NULL);

SET FOREIGN_KEY_CHECKS = 1;
