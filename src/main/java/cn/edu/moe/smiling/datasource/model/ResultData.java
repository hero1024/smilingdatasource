package cn.edu.moe.smiling.datasource.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@Schema(name = "ResultData", description = "统一响应结果类")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class ResultData<T> {
    /** 结果状态 ,具体状态码参见ReturnCode.java*/
    @Schema(description = "响应代码")
    private int status;
    @Schema(description = "响应消息")
    private String message;
    @Schema(description = "响应数据")
    private T data;
    @Schema(description = "响应时间")
    private long timestamp ;

    public ResultData (){
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC1000.getCode());
        resultData.setMessage(ReturnCode.RC1000.getMessage());
        resultData.setData(data);
        resultData.setTimestamp(System.currentTimeMillis());
        return resultData;
    }

    public static <T> ResultData<T> fail() {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC9999.getCode());
        resultData.setMessage(ReturnCode.RC9999.getMessage());
        resultData.setTimestamp(System.currentTimeMillis());
        return resultData;
    }

    public static <T> ResultData<T> fail(String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC9999.getCode());
        resultData.setMessage(message);
        resultData.setTimestamp(System.currentTimeMillis());
        return resultData;
    }

    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        resultData.setTimestamp(System.currentTimeMillis());
        return resultData;
    }

}
