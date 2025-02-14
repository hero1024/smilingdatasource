package cn.edu.moe.smiling.datasource.controller.advice;

import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.model.ReturnCode;
import cn.edu.moe.smiling.datasource.model.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.*;
import javax.xml.bind.ValidationException;

import java.util.stream.Collectors;


/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@Slf4j
@RestControllerAdvice(basePackages = "cn.edu.moe.smiling.datasource.controller")
public class RestExceptionHandler {

    /**
     * 默认全局异常处理。
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return ResultData.fail(ReturnCode.SERVER_ERROR.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ResultData<String>> handleValidatedException(Exception e) {
        ResultData<String> resp = null;

        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            resp = ResultData.fail(ReturnCode.INVALID_PARAM.getCode(),
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            ConstraintViolationException ex = (ConstraintViolationException) e;
            resp = ResultData.fail(ReturnCode.INVALID_PARAM.getCode(),
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException ex = (BindException) e;
            resp = ResultData.fail(ReturnCode.INVALID_PARAM.getCode(),
                    ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ValidException) {
            // BeanValidation GET object param
            ValidException ex = (ValidException) e;
            resp = ResultData.fail(ex.getCode(),
                    ex.getMessage());
            return new ResponseEntity<>(resp,HttpStatus.OK);
        }

        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }

}
