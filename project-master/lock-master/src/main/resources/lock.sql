
DROP TABLE IF EXISTS `lock`;
CREATE TABLE `lock`  (
  `uid` tinyint(2) NOT NULL AUTO_INCREMENT,
  `lockName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `times` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

INSERT INTO `lock` (uid,lockName) values( 1,'锁1');


UPDATE `lock` SET times= 1 WHERE uid =2;

SELECT * FROM `lock`  where uid = 8 for update

