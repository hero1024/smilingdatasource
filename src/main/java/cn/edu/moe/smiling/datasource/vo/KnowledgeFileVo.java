package cn.edu.moe.smiling.datasource.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KnowledgeFileVo implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "文件id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String id;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String name;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String type;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long size;
    /**
     * 文件访问权限
     */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 文件状态
     */
    @ApiModelProperty(value = "文件状态")
    private String status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

}

