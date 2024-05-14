package cn.edu.moe.smiling.datasource.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class FeedbackVo implements Serializable {
    @ApiModelProperty("问题记录id")
    private Long historyId;
    @ApiModelProperty(value = "用户评价", required = true)
    @NotBlank(message = "feedback为必填项")
    private String feedback;
    @ApiModelProperty("评价内容")
    private String badReason;
}
