package com.funicorn.basic.common.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Aimee
 * @since 2021/10/28
 */
@Component
public class AppContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        AppContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取上下文实例对象
     * @return ApplicationContext
     * */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过bean名称获取bean实例
     * @param name beanName
     * @return 实例对象
     * */
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * 通过Class类获取bean实例
     * @param requiredType Class对象
     * @param <T> 泛型
     * @return T 泛型
     * */
    public static <T> T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }
}
