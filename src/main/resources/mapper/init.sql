create database smiling_datasource;
create table if not exists smiling_datasource.database_source
(
    id              bigint auto_increment
    primary key,
    datasource_name varchar(255)                      null
    unique,
    url             varchar(255)                      null,
    username        varchar(255)                      null,
    password        varchar(255)                      null,
    driver_class    varchar(255)                      null,
    database_type   varchar(255)                      null,
    db_source      varchar(255) default (database()) null
    );

CREATE TABLE if not exists `question_case`  (
                                  `id` bigint(11) NOT NULL AUTO_INCREMENT,
                                  `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
                                  `sort_number` bigint(11) NOT NULL,
                                  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;