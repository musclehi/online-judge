package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * 评测结果 枚举类
 *
 * @author idealism
 * @date 2018/3/30
 */
public enum ResultEnum {
    QUEUING(1, "Queuing"),
    JUDGING(2, "Judging"),
    ACCEPTED(3, "Accepted"),
    COMPILATION_ERROR(4, "Compilation Error"),
    TIME_LIMIT_EXCEED(5, "Time Limit Exceed"),
    MEMORY_LIMIT_EXCEED(6, "Memory Limit Exceed"),
    OUTPUT_LIMIT_EXCEED(7, "Output Limit Exceed"),
    RUNTIME_ERROR(8, "Runtime Error"),
    SYSTEM_ERROR(99, "System Error"),
    ;

    /**
     * 评测结果 代码
     */
    private Integer code;

    /**
     * 评测结果 描述
     */
    private String description;

    ResultEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 通过 评测结果 代码 返回 评测结果 枚举类型
     * @param code 评测结果 代码
     * @return 评测 枚举类型
     */
    public static ResultEnum getResultEnumByCode(Integer code) {
        for (ResultEnum resultEnum : values()) {
            if(resultEnum.getCode().equals(code)) {
                return resultEnum;
            }
        }
        return null;
    }
}
