package cn.edu.moe.smiling.datasource.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QuestionAndAnswerVo {
    @ApiModelProperty(value = "会话编号")
    @NotBlank(message = "chat_no为必填项")
    private String chatNo;
    @ApiModelProperty(value = "问题", required = true)
    @NotBlank(message = "question为必填项")
    private String question;
    @ApiModelProperty(value = "回答", required = true)
    @NotBlank(message = "answer为必填项")
    private String answer;
}
