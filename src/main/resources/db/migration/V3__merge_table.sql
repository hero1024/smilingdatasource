DROP TABLE IF EXISTS `question_history`;
CREATE TABLE `question_history` (
                               `id` bigint(11) NOT NULL AUTO_INCREMENT,
                               `question` text,
                               `answer` longtext,
                               `del_state` int NOT NULL DEFAULT 0,
                               #sessionid varchar
                               `user_id` bigint(11) NULL,
                               `user_ip` varchar(255) DEFAULT NULL,
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=596 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `data_feedback` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `history_id` bigint(20) DEFAULT NULL,
                                 `feedback` varchar(255) DEFAULT NULL,
                                 `bad_reason` text,
                                 `create_time` datetime DEFAULT NULL,
                                 `update_time` datetime DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;