package com.funicorn.basic.common.feign.annotation;

import java.lang.annotation.*;

/**
 * @author Aimee
 * @since 2021/12/27
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {

    /**
     * 是否仅允许Feign之间调用
     *
     * @return false, true
     */
    boolean value() default true;
}
