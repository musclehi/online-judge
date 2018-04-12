package cn.idealismxxm.onlinejudge.domain.enums;

/**
 *  扩展字段key 枚举类
 * @author idealism
 * @date 2018/4/11
 */
public enum ExtensionKeyEnum {
    COMPILATION_INFO("compilationInfo", "编译信息"),
    ;

    /**
     * 扩展字段 key
     */
    private String key;

    /**
     * 扩展字段 key 的描述
     */
    private String description;

    ExtensionKeyEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
