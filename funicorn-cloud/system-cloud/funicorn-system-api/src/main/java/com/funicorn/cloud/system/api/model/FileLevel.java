package com.funicorn.cloud.system.api.model;

/**
 * 文件级别
 * @author Aimee
 * @since 2021/11/9 17:39
 */
public enum FileLevel {

    /**
     * 私有的
     * */
    PRIVATE("private"),

    /**
     * 公共的
     * */
    PUBLIC("public"),
    ;

    private final String value;

    FileLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * 判断值是否存在枚举类中
     * */
    public static boolean containsLevel(String value) {
        for (FileLevel fileLevel:FileLevel.values()) {
            if (fileLevel.value.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
