DROP TABLE IF EXISTS `data_feedback`;
CREATE TABLE `data_feedback` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `history_id` bigint(20) DEFAULT NULL COMMENT '问题记录id',
                                 `feedback` varchar(255) DEFAULT NULL COMMENT '用户评价',
                                 `bad_reason` varchar(1024) COMMENT '评价内容',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `question_history`;
CREATE TABLE `question_history` (
                                    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `chat_no` varchar(255) DEFAULT NULL COMMENT '会话编号',
                                    `question` text COMMENT '问题',
                                    `answer` longtext COMMENT '回答',
                                    `del_state` int NOT NULL DEFAULT 0 COMMENT '删除状态',
                                    `user_id` bigint(11) NULL COMMENT '用户id',
                                    `user_ip` varchar(255) DEFAULT NULL COMMENT '用户IP',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=596 DEFAULT CHARSET=utf8mb4;

