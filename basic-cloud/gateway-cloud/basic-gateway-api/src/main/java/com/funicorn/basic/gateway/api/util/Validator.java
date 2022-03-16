package com.funicorn.basic.gateway.api.util;

/**
 * @author Aimee
 * @since 2022/3/16 11:22
 */

public interface Validator<T> {

    /**
     * 校验
     * @param t 入参
     * */
    void validate(T t);
}
