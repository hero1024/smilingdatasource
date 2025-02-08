package cn.edu.moe.smiling.datasource.controller.advice;

import cn.edu.moe.smiling.datasource.model.ResultData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@RestControllerAdvice(basePackages = "cn.edu.moe.smiling.datasource.controller")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 设置跨域
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Headers", "X-User-ID");
        serverHttpResponse.getHeaders().setAll(headers);
        if(o == null || o instanceof String){
            return objectMapper.writeValueAsString(ResultData.success(o));
        }
        if(o instanceof ResultData){
            return o;
        }
        return ResultData.success(o);
    }

}
