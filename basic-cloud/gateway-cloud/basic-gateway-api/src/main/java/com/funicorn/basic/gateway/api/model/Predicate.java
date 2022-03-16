package com.funicorn.basic.gateway.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aimee
 * @since 2022/3/16 10:29
 */
@Data
public class Predicate implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 断言id
     */
    private String id;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 类型 After/Before/Between/Cookie/Header/Host/Method/Path/Query/RemoteAddr
     * required
     */
    private String type;

    /**
     * 值
     * required
     */
    private String value;
}
