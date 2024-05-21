
CREATE TABLE IF NOT EXISTS `question_case`  (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '推荐问题',
    `sort_number` bigint(11) NOT NULL COMMENT '排序序号',
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `question_case` (`id`, `question`, `sort_number`, `type`, `update_time`) VALUES (1, '2013-2022年北京、河北、广东普通高校专任教师中博士学历人数的比率', 1, NULL, '2024-05-14 16:27:40');
INSERT INTO `question_case` (`id`, `question`, `sort_number`, `type`, `update_time`) VALUES (2, '2013-2022北京、广东、四川普通高校研究生招生人数', 2, NULL, '2024-05-14 16:27:40');
INSERT INTO `question_case` (`id`, `question`, `sort_number`, `type`, `update_time`) VALUES (3, '2013至2022年北京、黑龙江省小学入学人数', 3, NULL, '2024-05-14 16:27:40');
INSERT INTO `question_case` (`id`, `question`, `sort_number`, `type`, `update_time`) VALUES (4, '2022年各省幼儿园数量', 4, NULL, '2024-05-14 16:27:40');
INSERT INTO `question_case` (`id`, `question`, `sort_number`, `type`, `update_time`) VALUES (5, '2013-2022北京、上海普通高中在校生人数', 5, NULL, '2024-05-14 16:27:40');

CREATE TABLE IF NOT EXISTS `question_history` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `question_feedback` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `history_id` bigint(20) DEFAULT NULL COMMENT '问题记录id',
                                 `feedback` varchar(255) DEFAULT NULL COMMENT '用户评价',
                                 `bad_reason` varchar(1024) COMMENT '评价内容',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;