package com.funicorn.basic.common.timer.annotation;


import java.lang.annotation.*;

/**
 * xxl-job bean模式
 * @author Aimee
 * @since 2021/09/25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface XxlJobBeanHandler {
}
