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
    OJ_NOT_SUPPORT("0004", "OJ不支持"),
    ILLEGAL_ARGUMENT("0005", "非法参数"),

    DAO_CALL_ERROR("0006", "调用dao层异常"),
    SERVICE_CALL_ERROR("0007", "service调用异常"),
    PROBLEM_NOT_EXIST("0008", "题目不存在"),
    MESSAGE_PRODUCE_ERROR("0009", "消息生产异常"),
    MESSAGE_CONSUME_ERROR("0010", "消息消费异常"),

    MESSAGE_TYPE_NOT_SUPPORT("0011", "消息类型不支持"),
    MESSAGE_CONTENT_NOT_SUPPORT("0012", "消息内容不支持"),
    SUBMISSION_NOT_EXIST("0013", "提交记录不存在"),
    DESCRIPTION_NOT_EXIST("0014", "题目描述不存在"),
    TEST_CASE_NOT_EXIST("0015", "测试用例不存在"),

    DATA_SAVE_ERROR("0016", "数据保存异常"),
    FILE_CREATE_ERROR("0017", "文件创建失败"),
    FILE_WRITE_ERROR("0018", "文件写入失败"),
    FILE_READ_ERROR("0019", "文件读取失败"),
    LIBRARY_LOAD_ERROR("0020", "库载入失败"),

    USER_NOT_EXIST("0021", "用户不存在"),
    USER_NOT_SIGN_IN("0022", "用户未登录"),
    CONTEST_NOT_EXIST("0023", "比赛不存在"),
    USER_PRIVILEGE_NOT_EXIST("0024", "用户权限不存在"),
    USER_PRIVILEGE_ALREADY_EXIST("0025", "用户权限已存在"),

    USER_PRIVILEGE_ALREADY_CANCEL("0026", "用户权限已取消"),
    USER_PRIVILEGE_NOT_ENOUGH("0027", "用户权限不足"),
    TAG_NOT_EXIST("0028", "标签不存在"),
    PROBLEM_TAG_NOT_EXIST("0029", "题目标签关系不存在"),
    PROBLEM_TAG_ALREADY_EXIST("0030", "题目标签关系已存在"),

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
