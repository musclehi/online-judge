package cn.idealismxxm.onlinejudge.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author idealism
 * @date 2018/3/29
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取 applicationContext
     *
     * @return applicationContext
     */
    private static ApplicationContext getApplicationContext() {
        return SpringContextUtil.applicationContext;
    }

    /**
     * 通过 name 获取 bean
     *
     * @param name bean 名称
     * @return bean
     */
    public static Object getBean(String name) {
        return SpringContextUtil.getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz bean class
     * @param <T> 类型
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return SpringContextUtil.getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name bean 名称
     * @param clazz bean class
     * @param <T> 类型
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return SpringContextUtil.getApplicationContext().getBean(name, clazz);
    }
}