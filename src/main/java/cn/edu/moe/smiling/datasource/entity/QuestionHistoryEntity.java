package cn.edu.moe.smiling.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("question_history")
public class QuestionHistoryEntity implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String questionNo;
    private String question;
    private String answer;
    private Long userId;
    @TableLogic(value="0",delval="1")
    private Integer delState;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;
}
