package cn.edu.moe.smiling.datasource.vo;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuestionHistoryVo implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String chatNo;
    private String question;
    private String answer;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    private String userIp;
    private Integer delState;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    private Boolean typeJson;

    public Boolean getTypeJson() {
        return JSONUtil.isTypeJSON(this.answer);
    }

}
