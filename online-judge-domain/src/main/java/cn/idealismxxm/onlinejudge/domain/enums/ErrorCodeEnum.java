package cn.idealismxxm.onlinejudge.domain.enums;

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
    OJ_ENUM_NOT_EXISTS("0004", "OJ不存在"),
    ILLEGAL_ARGUMENT("0005", "非法参数"),
    DAO_CALL_ERROR("0006", "调用dao层异常"),
    SERVICE_CALL_ERROR("0007", "service调用异常"),
    PROBLEM_NOT_EXISTS("0008", "题目不存在"),
    MESSAGE_PRODUCE_ERROR("0009", "消息生产异常"),
    MESSAGE_CONSUME_ERROR("0010", "消息消费异常"),
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
