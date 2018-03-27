package cn.idealismxxm.onlinejudge.enums;

/**
 * 公开状态枚举类
 *
 * @author idealism
 * @date 2018/3/27
 */
public enum PublicStatusEnum {
    PRIVATE(0, "私密"),
    PUBLIC(1, "公开"),
    ;
    /**
     * 公开状态代码
     */
    private Integer code;

    /**
     * 公开状态描述
     */
    private String description;

    PublicStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
