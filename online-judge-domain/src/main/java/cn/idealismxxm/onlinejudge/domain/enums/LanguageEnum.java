package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * 编程语言枚举类
 * @author idealism
 * @date 2018/4/3
 */
public enum LanguageEnum {
    ;

    /**
     * 语言代码
     */

    private Integer code;

    /**
     * 语言描述
     */
    private String description;

    /**
     * 源代码后缀
     */
    private String suffix;

    LanguageEnum(Integer code, String description, String suffix) {
        this.code = code;
        this.description = description;
        this.suffix = suffix;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getSuffix() {
        return suffix;
    }

    /**
     * 通过 语言 代码 返回 语言 枚举类型
     * @param code 语言 代码
     * @return 语言 枚举类型
     */
    public static LanguageEnum getLanguageEnumByCode(Integer code) {
        for (LanguageEnum languageEnum : values()) {
            if(languageEnum.getCode().equals(code)) {
                return languageEnum;
            }
        }
        return null;
    }
}
