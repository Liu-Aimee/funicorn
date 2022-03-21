package com.funicorn.cloud.system.api.model;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 文件级别
 * @author Aimee
 * @since 2021/11/9 17:39
 */
public enum FileLevel {

    /**
     * 私有的
     * */
    PRIVATE,

    /**
     * 公共的
     * */
    PUBLIC,
    ;

    /**
     * 判断是否存在枚举类
     * @param type type
     * @return boolean
     * */
    public static boolean hasType(String type) {
        return Arrays.stream(FileLevel.values()).map(FileLevel::name).collect(Collectors.toList()).contains(type.toUpperCase(Locale.ROOT));
    }

    /**
     * 判断是否是public
     * @param type type
     * @return boolean
     * */
    public static boolean isPublic(String type) {
        return hasType(type.toUpperCase(Locale.ROOT)) && FileLevel.PUBLIC.name().equals(type.toUpperCase(Locale.ROOT));
    }
}
