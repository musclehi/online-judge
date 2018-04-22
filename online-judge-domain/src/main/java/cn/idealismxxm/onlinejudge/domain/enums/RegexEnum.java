package cn.idealismxxm.onlinejudge.domain.enums;

import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * 正则表达式枚举类
 *
 * @author idealism
 * @date 2018/4/22
 */
public enum RegexEnum {
    EMAIL("邮箱", "^(?=\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$).{0,50}$", "邮箱最长为50个字符"),
    USERNAME("用户名", "^[a-zA-Z0-9_-]{4,10}$", "用户名只能由大小写字母、数字、下划线和减号组成，长度在4~10之间"),
    PASSWORD("密码", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$", "密码至少包含大写字母、小写字母和数字各1个，长度在8~20之间"),
    ;

    /**
     * 正则表达式
     */
    private String regex;

    /**
     * 正则表达式描述
     */
    private String description;

    /**
     * 正则表达式匹配失败后的提示信息
     */
    private String message;

    RegexEnum(String description, String regex, String message) {
        this.regex = regex;
        this.description = description;
        this.message = message;
    }

    public String getRegex() {
        return regex;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 验证字符串是否符合正则表达式
     *
     * @param value 待验证字符串
     */
    public void validate(String value) {
        if (StringUtils.isBlank(value)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        if (!value.matches(this.getRegex())) {
            throw BusinessException.buildCustomizedMessageException(this.getMessage());
        }
    }

    /**
     * 测试字符串是否符合正则表达式
     *
     * @param value 待测试字符串
     * @return true / false
     */
    public Boolean test(String value) {
        if (StringUtils.isBlank(value)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        return value.matches(this.getRegex());
    }
}
