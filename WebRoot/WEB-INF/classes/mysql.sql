

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `TB_CLAZZ`
-- ----------------------------
DROP TABLE IF EXISTS `TB_CLAZZ`;
CREATE TABLE `TB_CLAZZ` (
 		`CLAZZ_ID` varchar(100) NOT NULL,
		`NAME` varchar(50) DEFAULT NULL COMMENT '班级名称',
		`CLAZZ_TYPE` int(11) NOT NULL COMMENT '班级类型',
		`CREATE_USER` VARCHAR(100) NOT NULL COMMENT '创建用户',
		`CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
		`SORT` int(11) NOT NULL COMMENT '排序',
		`STATUS` varchar(5) DEFAULT NULL COMMENT '状态',
		`YEAR` int(5) DEFAULT NULL COMMENT '开班年份',
		`ORG_ID` varchar(100) DEFAULT NULL COMMENT '组织代码',
  		PRIMARY KEY (`CLAZZ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
