package com.funicorn.cloud.system.center.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2022/3/8 11:18
 */
@Data
public class RouteDTO {

    /**
     * 断言
     * */
    private String predicate;

    /**
     * 截取层级
     * */
    private String filter;
}
