#create database
CREATE DATABASE IF NOT EXISTS tipmd_test DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

#create user and grant permissions to database
GRANT ALL PRIVILEGES ON tipmd_test.* TO 'tipmd_test_user'@'%' IDENTIFIED BY '1plus1equals3' WITH GRANT OPTION;  
GRANT ALL PRIVILEGES ON tipmd_test.* TO 'tipmd_test_user'@'localhost' IDENTIFIED BY '1plus1equals3' WITH GRANT OPTION; 
FLUSH PRIVILEGES; 

USE tipmd_test;
 
/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50537
Source Host           : localhost:3306
Source Database       : tipmd_test

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2015-03-17 17:12:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `courses`
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(24) NOT NULL,
  `credit` int(11) NOT NULL COMMENT '学分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES ('10', '语文', '6');
INSERT INTO `courses` VALUES ('11', '数学', '6');
INSERT INTO `courses` VALUES ('12', '英语', '6');

-- ----------------------------
-- Table structure for `scores`
-- ----------------------------
DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  `points` int(11) NOT NULL COMMENT '考试分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scores
-- ----------------------------
INSERT INTO `scores` VALUES ('1', '37', '10', '60');
INSERT INTO `scores` VALUES ('2', '37', '11', '70');
INSERT INTO `scores` VALUES ('3', '37', '12', '88');

-- ----------------------------
-- Table structure for `students`
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `birthday` date NOT NULL,
  `sex` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('37', 'jolie', '123456', '2015-03-11', '1');
INSERT INTO `students` VALUES ('38', '汤东', '123456', '2000-06-01', '0');

