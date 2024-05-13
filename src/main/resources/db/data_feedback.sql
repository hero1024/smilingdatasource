/*
Navicat MySQL Data Transfer

Source Server         : graph
Source Server Version : 50738
Source Host           : 10.20.13.195:3306
Source Database       : graphdb

Target Server Type    : MYSQL
Target Server Version : 50738
File Encoding         : 65001

Date: 2024-05-05 16:40:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_feedback
-- ----------------------------
DROP TABLE IF EXISTS `data_feedback`;
CREATE TABLE `data_feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question_id` bigint(20) DEFAULT NULL,
  `answer_id` bigint(20) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `bad_reason` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of data_feedback
-- ----------------------------
INSERT INTO `data_feedback` VALUES ('2', '1', '1', 'good', '', '2024-03-19 10:01:08', null);
INSERT INTO `data_feedback` VALUES ('3', '1', '1', 'good', '', '2024-04-02 10:38:03', null);
INSERT INTO `data_feedback` VALUES ('9', '26', '26', 'good', null, '2024-04-08 09:25:43', null);
INSERT INTO `data_feedback` VALUES ('10', '29', '29', 'bad', '内容不准确;没有帮助', '2024-04-10 19:05:21', null);
INSERT INTO `data_feedback` VALUES ('11', '143', '143', 'bad', '还不够好', '2024-04-16 15:11:00', null);
INSERT INTO `data_feedback` VALUES ('12', '152', '152', 'good', null, '2024-04-16 15:23:56', null);
INSERT INTO `data_feedback` VALUES ('13', '153', '153', 'good', null, '2024-04-16 15:26:45', null);
INSERT INTO `data_feedback` VALUES ('14', '366', '366', 'good', null, '2024-04-19 14:50:25', null);
INSERT INTO `data_feedback` VALUES ('15', '364', '364', 'bad', null, '2024-04-19 14:50:32', '2024-04-19 14:50:42');
INSERT INTO `data_feedback` VALUES ('17', '367', '367', 'good', null, '2024-04-19 14:52:22', null);
