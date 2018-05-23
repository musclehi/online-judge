package cn.idealismxxm.onlinejudge.domain.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限删除状态枚举类
 *
 * @author idealism
 * @date 2018/5/22
 */
public enum PrivilegeEnum {
    SIGN_IN(1, "登录"),
    MANAGE_PRIVILEGE(2, "管理权限"),
    MANAGE_PROBLEM(3, "管理题目"),
    MANAGE_CONTEST(4, "管理比赛"),
    ;

    /**
     * 权限代码
     */
    private Integer code;

    /**
     * 权限描述
     */
    private String description;

    PrivilegeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 获取不可给用户的权限枚举列表
     *
     * @return 权限枚举列表
     */
    private static List<PrivilegeEnum> getNotEditablePrivilegeEnums() {
        List<PrivilegeEnum> result = new ArrayList<>(1);
        result.add(PrivilegeEnum.SIGN_IN);

        return result;
    }

    /**
     * 获取可给用户的权限枚举列表
     *
     * @return 权限枚举列表
     */
    public static List<PrivilegeEnum> getEditablePrivilegeEnums() {
        List<PrivilegeEnum> result = new ArrayList<>(Arrays.asList(PrivilegeEnum.values()));

        // 去除不可给用户的权限
        result.removeAll(PrivilegeEnum.getNotEditablePrivilegeEnums());

        return result;
    }

    /**
     * 通过 权限 代码 返回 权限 枚举类型
     *
     * @param code 权限 代码
     * @return 权限 枚举类型
     */
    public static PrivilegeEnum getLanguageEnumByCode(Integer code) {
        for (PrivilegeEnum privilegeEnum : PrivilegeEnum.values()) {
            if (privilegeEnum.getCode().equals(code)) {
                return privilegeEnum;
            }
        }
        return null;
    }
}
