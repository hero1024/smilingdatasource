
CREATE TABLE if not exists `question_case`  (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `sort_number` bigint(11) NOT NULL,
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE if not exists `question_history`  (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `question_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `del_state` int NOT NULL DEFAULT 0,
    `user_id` bigint(11) NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;