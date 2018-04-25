package cn.idealismxxm.onlinejudge.domain.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * request 工具
 *
 * @author idealism
 * @date 2018/4/25
 */
public class RequestUtil {

    /**
     * 获取 当前线程的 request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 设置 session 的 属性
     *
     * @param name  属性名
     * @param value 属性值
     */
    public static void setAttribute(String name, Object value) {
        RequestUtil.getRequest().getSession().setAttribute(name, value);
    }

    /**
     * 获取 session 的 属性
     *
     * @param name 属性名
     * @return 属性值
     */
    public static Object getAttribute(String name) {
        return RequestUtil.getRequest().getSession().getAttribute(name);
    }

    /**
     * 移除 session 的 属性
     *
     * @param name 属性名
     */
    public static void removeAttribute(String name) {
        RequestUtil.getRequest().getSession().removeAttribute(name);
    }
}