package cn.edu.moe.smiling.datasource.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class QuestionCaseVo implements Serializable {
    @ApiModelProperty(value = "示例问题", required = true)
    @NotBlank(message = "question为必填项")
    private String question;
    @ApiModelProperty(value = "示例问题序号", required = true)
    @NotNull(message = "sortNumber为必填项")
    private Long sortNumber;
    @ApiModelProperty(value = "问题类型", required = true)
    @NotBlank(message = "type为必填项")
    private String type;
}
