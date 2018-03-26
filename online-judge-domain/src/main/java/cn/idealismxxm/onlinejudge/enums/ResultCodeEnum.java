package cn.idealismxxm.onlinejudge.enums;

/**
 * 结果代码枚举类
 *
 * @author idealism
 * @date 2018/3/24
 */
public enum ResultCodeEnum {
    SUCCESS("0000", "SUCCESS"),
    ;

    /**
     * 结果代码
     */
    private String code;

    /**
     * 结果信息
     */
    private String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
