package cn.edu.moe.smiling.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("data_feedback")
public class DataFeedbackEntity implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long questionId;
    private Long answerId;
    private String feedback;
    private String badReason;
    private Date createTime;
    private Date updateTime;
}
