package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * 编程语言枚举类
 *
 * @author idealism
 * @date 2018/4/3
 */
public enum LanguageEnum {
    // TODO 编译命令输出目录需要配置
    C(1, "C", ".c", "gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c11 %s -lm"),
    C_PLUS_PLUS(2, "C++", ".cpp", "g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++14 %s -lm"),
    JAVA(3, "Java", ".java", "javac %s -d -encoding UTF8"),
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

    /**
     * 编译命令
     */
    private String compilationCommand;

    LanguageEnum(Integer code, String description, String suffix, String compilationCommand) {
        this.code = code;
        this.description = description;
        this.suffix = suffix;
        this.compilationCommand = compilationCommand;
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

    public String getCompilationCommand(String sourceFilePath) {
        return String.format(compilationCommand, sourceFilePath);
    }

    /**
     * 通过 语言 代码 返回 语言 枚举类型
     *
     * @param code 语言 代码
     * @return 语言 枚举类型
     */
    public static LanguageEnum getLanguageEnumByCode(Integer code) {
        for (LanguageEnum languageEnum : values()) {
            if (languageEnum.getCode().equals(code)) {
                return languageEnum;
            }
        }
        return null;
    }
}