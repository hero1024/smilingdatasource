package cn.edu.moe.smiling.datasource.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "knowledge_file",autoResultMap = true)
public class KnowledgeFileEntity implements Serializable {
    /**
     * id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 文件数据集id
     */
    private String datasetId;
    /**
     * 文件向量化id
     */
    private String documentId;
    /**
     * 文件状态
     */
    private String status;
    /**
     * 文件状态描述
     */
    private String statusDesc;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "update_time", update = "now()")
    private Date updateTime;
    @TableLogic(value="0",delval="1")
    /**
     * 是否删除
     */
    private Integer delState;

}

