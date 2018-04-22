package cn.idealismxxm.onlinejudge.web.interceptor;

import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录拦截器
 *
 * @author idealism
 * @date 2018/4/22
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private Set<String> exclusionUris;

    @Value("AuthenticationInterceptor.signInUri")
    private String signInUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO 改成注解形式，只对有注解的方法拦截
        // 如果当前路径不需要拦截，则直接返回 true
        if(this.isExclusive(request.getRequestURI())) {
            return true;
        }

        // 用户已登录，则返回 true
        if(request.getSession().getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER) != null) {
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

    private Boolean isExclusive(String uri) {
        return exclusionUris.contains(uri);
    }

    @Value("AuthenticationInterceptor.exclusionUriStr")
    private void initExclusions(String exclusionUriStr) {
        exclusionUris = new HashSet<>(Arrays.asList(exclusionUriStr.split(",")));
    }
}
