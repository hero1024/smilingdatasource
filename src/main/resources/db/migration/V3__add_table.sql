CREATE TABLE IF NOT EXISTS `knowledge_file`
(
    `id`          bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(255)        DEFAULT NULL COMMENT '文件名称',
    `path`        varchar(255)        DEFAULT NULL COMMENT '文件路径',
    `type`        varchar(255)        DEFAULT NULL COMMENT '文件类型',
    `size`        varchar(255)        DEFAULT NULL COMMENT '文件大小',
    `user_id`     varchar(255)        DEFAULT NULL COMMENT '用户id',
    `status`      varchar(255)        DEFAULT NULL COMMENT '文件状态',
    `status_desc` varchar(2048) DEFAULT NULL COMMENT '文件状态描述',
    `create_time` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;
