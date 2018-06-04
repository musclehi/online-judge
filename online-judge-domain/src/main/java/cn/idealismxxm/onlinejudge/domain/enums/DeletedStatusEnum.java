package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * 删除状态枚举类
 *
 * @author idealism
 * @date 2018/5/22
 */
public enum DeletedStatusEnum {
    VALID(0, "有效"),
    DELETED(1, "已删除"),
    ;
    /**
     * 删除状态代码
     */
    private Integer code;

    /**
     * 删除状态描述
     */
    private String description;

    DeletedStatusEnum(Integer code, String description) {
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
     * 通过 删除状态 代码 返回 删除状态 枚举类型
     *
     * @param code 权限 代码
     * @return 权限 枚举类型
     */
    public static DeletedStatusEnum getDeletedStatusEnumByCode(Integer code) {
        for (DeletedStatusEnum deletedStatusEnum : DeletedStatusEnum.values()) {
            if (deletedStatusEnum.getCode().equals(code)) {
                return deletedStatusEnum;
            }
        }
        return null;
    }
}
