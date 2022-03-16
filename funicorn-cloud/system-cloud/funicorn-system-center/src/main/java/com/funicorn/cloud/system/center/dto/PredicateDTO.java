package com.funicorn.cloud.system.center.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2022/3/10 9:04
 */
@Data
public class PredicateDTO {

    /**
     * 断言id
     * */
    private String id;

    /**
     * 路由id
     * */
    private String routeId;

    /**
     * 类型
     * */
    private String type;

    /**
     * 修改值
     * */
    private String value;
}
