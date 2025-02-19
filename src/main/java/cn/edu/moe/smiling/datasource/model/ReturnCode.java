package cn.edu.moe.smiling.datasource.model;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
public enum ReturnCode {
    /**操作成功**/
    RC1000(1000,"操作成功"),
    /**操作失败**/
    RC9999(9999,"操作失败"),
    /**服务限流**/
    RC2000(2000,"服务开启限流保护,请稍后再试!"),
    /**服务降级**/
    RC2001(2001,"服务开启降级保护,请稍后再试!"),
    /**热点参数限流**/
    RC2002(2002,"热点参数限流,请稍后再试!"),
    /**系统规则不满足**/
    RC2003(2003,"系统规则不满足要求,请稍后再试!"),
    /**授权规则不通过**/
    RC2004(2004,"授权规则不通过,请稍后再试!"),
    /**access_denied**/
    RC4003(4003,"无访问权限,请联系管理员授予权限"),
    /**access_denied**/
    RC4001(4001,"匿名用户访问无权限资源时的异常"),
    /**服务异常**/
    SERVER_ERROR(5000,"系统异常，请稍后重试"),
    INVALID_PARAM(4000,"访问参数不合法"),
    NOT_FOUND(4004,"访问资源不存在"),
    CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

    /**自定义状态码**/
    private final int code;
    /**自定义描述**/
    private final String message;

    ReturnCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
