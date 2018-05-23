package cn.idealismxxm.onlinejudge.domain.annotation;

import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限拦截注解
 *
 * @author idealism
 * @date 2018/5/23
 */
// 注解作用在方法上
@Target(ElementType.METHOD)
// 注解在运行时起作用
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePrivilege {
    // 所需要的权限，默认不需要任何权限
    PrivilegeEnum[] privilegeEnum() default {};
}
