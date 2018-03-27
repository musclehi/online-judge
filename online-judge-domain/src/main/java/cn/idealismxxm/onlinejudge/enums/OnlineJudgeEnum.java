package cn.idealismxxm.onlinejudge.enums;

/**
 * OJ 枚举类
 *
 * @author idealism
 * @date 2018/3/27
 */
public enum OnlineJudgeEnum {
    ORIGINAL(1, "本平台"),
    HDU(2, "HDU"),
    POJ(3, "POJ"),;
    /**
     * OJ 代码
     */
    private Integer code;

    /**
     * OJ 名字
     */
    private String name;

    OnlineJudgeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
