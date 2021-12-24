package com.funicorn.cloud.upms.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用信息
 * @author Aimee
 * @since 2021/1/27 17:47
 */
@Data
public class AppInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     * */
    private String id;

    /**
     * 应用名称
     * */
    private String name;

    /**
     * 客户端id
     * */
    private String clientId;

    /**
     * 授权范围
     * */
    private String scope;

    /**
     * 授权模式(password,refresh_token,authorization_code)
     * */
    private String authorizedGrantTypes;

    /**
     * 回调地址
     * */
    private String webServerRedirectUri;

    /**
     * token有效期 单位秒
     * */
    private Integer accessTokenValidity;

    /**
     * 刷新token有效期 单位秒
     * */
    private Integer refreshTokenValidity;

    /**
     * 自动批准
     * */
    private String autoapprove;

    /**
     * 图标
     * */
    private String logoUrl;

    /**
     * 应用首页地址
     * */
    private String homeUrl;

    /**
     * 路由
     * */
    private String router;

    /**
     * 描述
     * */
    private String description;

    /**
     * 应用状态，0启用 1禁用
     * */
    private Integer status;
}
