package cn.edu.moe.smiling.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("question_case")
public class QuestionCaseEntity implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String question;
    private Long sortNumber;
    private String type;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "update_time", update = "now()")
    private Date updateTime;
}
