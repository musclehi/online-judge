package cn.idealismxxm.onlinejudge.web.interceptor;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.entity.UserPrivilege;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.DeletedStatusEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.RequestUtil;
import cn.idealismxxm.onlinejudge.service.UserPrivilegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 权限拦截器
 *
 * @author idealism
 * @date 2018/4/22
 */
@Component
public class PrivilegeInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegeInterceptor.class);

    @Resource
    private UserPrivilegeService userPrivilegeService;

    @Value("PrivilegeInterceptor.signInUri")
    private String signInUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果没有映射到方法，则不进行拦截
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 当前方法不存在 @RequirePrivilege 注解，则抛出异常
        if (!method.isAnnotationPresent(RequirePrivilege.class)) {
            throw BusinessException.buildCustomizedMessageException("请求方法没有 RequirePrivilege 注解");
        }

        // 获取当前方法所需要的权限枚举列表，并取出登录权限（如果存在）
        RequirePrivilege requirePrivilege = method.getAnnotation(RequirePrivilege.class);
        List<PrivilegeEnum> privilegeEnums = Arrays.asList(requirePrivilege.privilegeEnum());

        // 校验权限
        return this.validatePrivilege(request, response, privilegeEnums);
    }

    /**
     * 校验权限
     *
     * @param request        request
     * @param response       response
     * @param privilegeEnums 所需要的权限列表
     * @return true / false
     * @throws Exception Exception
     */
    private boolean validatePrivilege(HttpServletRequest request, HttpServletResponse response, List<PrivilegeEnum> privilegeEnums) throws Exception {
        // 如果不需要任何权限，则返回 true
        if(privilegeEnums.isEmpty()) {
            return true;
        }

        // 是否需要登录
        boolean requireSignIn = privilegeEnums.contains(PrivilegeEnum.SIGN_IN);

        // 如果存在 登录权限，即需要登录
        if (requireSignIn) {
            // 如果用户未登录，则返回 false
            if (!this.isSignIn(request, response)) {
                return false;
            }
            // 去除登录权限
            privilegeEnums.remove(PrivilegeEnum.SIGN_IN);
        }

        // 获取当前用户的权限标识列表
        String username = ((User) RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER)).getUsername();
        List<UserPrivilege> userPrivileges = userPrivilegeService.listUserPrivilegeByUsername(username);

        userPrivileges.forEach(userPrivilege -> {
            // 若当前权限未被逻辑删除，则所需权限枚举列表中去除该权限
            if (DeletedStatusEnum.VALID.getCode().equals(userPrivilege.getDeletedStatus())) {
                privilegeEnums.remove(PrivilegeEnum.getLanguageEnumByCode(userPrivilege.getPrivilege()));
            }
        });

        // 若还存在没有删除的权限，则当前用户不存在这些权限，抛出 用户权限不足 异常
        if (!privilegeEnums.isEmpty()) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.USER_PRIVILEGE_NOT_ENOUGH);
        }
        return true;
    }

    /**
     * 判断用户是否登录，若未登录时，进行相关处理
     *
     * @param request  request
     * @param response response
     * @return true / false
     * @throws Exception Exception
     */
    private boolean isSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 用户已登录，则返回 true
        if (RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER) != null) {
            return true;
        }

        // 用户未登录
        String header = request.getHeader("X-Requested-With");
        // 若为ajax请求，则抛出异常，交给 handler 处理
        if (header != null && "XMLHttpRequest".equalsIgnoreCase(header)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.USER_NOT_SIGN_IN);
        } // 若为页面请求，则跳转到登录页面
        else {
            response.sendRedirect(this.signInUri);
        }
        return false;
    }
}
