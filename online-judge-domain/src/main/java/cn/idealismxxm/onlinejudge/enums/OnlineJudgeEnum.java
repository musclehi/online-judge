package cn.idealismxxm.onlinejudge.enums;

/**
 * OJ 枚举类
 *
 * @author idealism
 * @date 2018/3/27
 */
public enum OnlineJudgeEnum {
    ORIGINAL(1, "本平台", "%s"),
    HDU(2, "HDU", "http://acm.hdu.edu.cn/showproblem.php?pid=%s"),
    POJ(3, "POJ", "http://poj.org/problem?id=%s"),;

    /**
     * OJ 代码
     */
    private Integer code;

    /**
     * OJ 名字
     */
    private String name;

    /**
     * OJ 题目 url 形式：http://acm.hdu.edu.cn/showproblem.php?pid=%s （%s代表题号）
     */
    private String urlFormat;

    OnlineJudgeEnum(Integer code, String name, String urlFormat) {
        this.code = code;
        this.name = name;
        this.urlFormat = urlFormat;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUrl(String problemId) {
        return String.format(urlFormat, problemId);
    }
}
