package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * 编程语言枚举类
 *
 * @author idealism
 * @date 2018/4/3
 */
public enum LanguageEnum {
    C(1, "C", ".c", ".o", "gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c11 %s -lm -o %s"),
    C_PLUS_PLUS(2, "C++", ".cpp", ".o", "g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++14 %s -lm -o %s"),
    JAVA(3, "Java", ".java", ".class", "javac %s -d -encoding UTF8"),
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
     * 源代码文件后缀
     */
    private String sourceFileSuffix;

    /**
     * 编译后文件后缀
     */
    private String targetFileSuffix;

    /**
     * 编译命令
     */
    private String compilationCommand;

    LanguageEnum(Integer code, String description, String sourceFileSuffix, String targetFileSuffix, String compilationCommand) {
        this.code = code;
        this.description = description;
        this.sourceFileSuffix = sourceFileSuffix;
        this.targetFileSuffix = targetFileSuffix;
        this.compilationCommand = compilationCommand;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceFileSuffix() {
        return sourceFileSuffix;
    }

    public String getTargetFileSuffix() {
        return targetFileSuffix;
    }

    public String getCompilationCommand(String workspacePath, String sourceFileName) {
        String sourceFilePath = workspacePath + "/" + sourceFileName + this.getSourceFileSuffix();
        // Java 只需要指定目录
        if(this == JAVA) {
            return String.format(compilationCommand, sourceFilePath, workspacePath);
        }
        else {
            String targetFilePath = workspacePath + "/" + sourceFileName + this.getTargetFileSuffix();
            return String.format(compilationCommand, sourceFilePath, targetFilePath);
        }

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