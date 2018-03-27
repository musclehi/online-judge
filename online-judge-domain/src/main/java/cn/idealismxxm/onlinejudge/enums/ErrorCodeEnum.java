package cn.idealismxxm.onlinejudge.enums;

/**
 * 错误代码枚举类
 *
 * @author idealism
 * @date 2018/3/24
 */
public enum ErrorCodeEnum {
    SUCCESS("0000", "SUCCESS"),
    CUSTOM("0001", "自定义错误信息"),
    JSON_READ_VALUE_ERROR("0002", "json转对象错误"),
    JSON_WRITE_VALUE_ERROR("0003", "对象转json错误"),
    UNKNOWN("9999", "未知错误"),
    ;

    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String msg;

    ErrorCodeEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}
