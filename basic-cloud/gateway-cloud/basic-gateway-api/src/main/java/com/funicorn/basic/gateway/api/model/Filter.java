package com.funicorn.basic.gateway.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aimee
 * @since 2022/3/17 11:09
 */
@Data
public class Filter implements Serializable {

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
     * 类型
     * /AddRequestHeader/AddRequestParameter/AddResponseHeader/Hystrix/PrefixPath/PreserveHostHeader/name
     * /RedirectTo/RemoveRequestHeader/RemoveResponseHeader/RewritePath/RewriteResponseHeader/SaveSession
     * /SetPath/SetResponseHeader/SetStatus/StripPrefix
     */
    private String type;

    /**
     * 值
     * required
     */
    private String value;
}
