package cn.edu.moe.smiling.datasource.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionVo {
    private Long id;
    @ApiModelProperty("问题")
    private String content;
    @ApiModelProperty("答案")
    private String answer;
    @ApiModelProperty("系统编号")
    private String chatNo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;
    @ApiModelProperty("用户评价")
    private String feedback;
    @ApiModelProperty("评价内容")
    private String badReason;
    @ApiModelProperty("用户姓名")
    private String username;
}
