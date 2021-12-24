package com.funicorn.cloud.system.center.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/12/1 14:56
 */
@Data
public class RouteConfigDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 转发地址
     */
    private String uri;

    /**
     * 路由匹配集合
     */
    private String predicates;

    /**
     * 过滤等级
     */
    private String filters;

    /**
     * 顺序
     */
    private String rOrder;
}
