package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * 可见状态枚举类
 *
 * @author idealism
 * @date 2018/3/27
 */
public enum VisibleStatusEnum {
    PRIVATE(0, "私密"),
    VISIBLE(1, "全部可见"),
    CONTEST_VISIBLE(2, "仅比赛内可见"),
    ;
    /**
     * 可见状态代码
     */
    private Integer code;

    /**
     * 可见状态描述
     */
    private String description;

    VisibleStatusEnum(Integer code, String description) {
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
