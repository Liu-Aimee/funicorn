package com.funicorn.basic.common.timer.core;

import com.funicorn.basic.common.timer.annotation.XxlJobBeanHandler;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

/**
 * xxl-job定时任务处理器自动注册
 * @author Aimee
 * @since 2021/9/25 11:18
 */
@Slf4j
@Component
public class XxlJobBeanHandlerRegister implements ApplicationContextAware, CommandLineRunner {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Override
    public void run(String... args) {
        Class<? extends Annotation> annotationClass = XxlJobBeanHandler.class;
        Map<String, Object> beanWithAnnotation = applicationContext.getBeansWithAnnotation(annotationClass);
        Set<Map.Entry<String, Object>> entitySet = beanWithAnnotation.entrySet();
        for (Map.Entry<String, Object> entry : entitySet) {
            Class<? extends IJobHandler> clazz = (Class<? extends IJobHandler>) entry.getValue().getClass();
            XxlJobExecutor.registJobHandler(entry.getKey(), applicationContext.getBean(clazz));
        }
    }
}
