package cn.edu.moe.smiling.datasource.model;

import javax.xml.bind.ValidationException;

/**
 * 自定义异常
 * @author cbalt
 */
public class ValidException extends ValidationException {

    private int code;

    public ValidException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ValidException(ReturnCode returnCode) {
        super(returnCode.getMessage());
        this.code = returnCode.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
