package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/10/31 13:34
 */
@Data
public class AppDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 应用id
     */
    @NotBlank(message = "应用id不能为空",groups = Update.class)
    private String id;

    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空",groups = Insert.class)
    private String name;

    /**
     * 应用标识
     */
    @NotBlank(message = "应用标识不能为空",groups = Insert.class)
    private String clientId;

    /**
     * 应用密钥
     */
    @NotBlank(message = "应用密钥不能为空",groups = Insert.class)
    private String clientSecret;

    /**
     * 授权范围 read,write,all
     */
    private String scope;

    /**
     * 授权模式:authorization_code 授权码,password 密码,refresh_token 刷新access_token,implicit 简化,client_credentials 客户端
     */
    @NotBlank(message = "应用id不能为空",groups = Update.class)
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    @NotBlank(message = "应用id不能为空",groups = Update.class)
    private String webServerRedirectUri;

    /**
     * 令牌有效期 单位秒 默认7200
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期 单位秒 默认 14400
     */
    private Integer refreshTokenValidity;

    /**
     * 附加信息
     */
    private String additionalInformation;

    /**
     * 自动批准 true/false 默认false
     */
    private String autoapprove;

    /**
     * logo地址
     */
    private String logoUrl;

    /**
     * 路由地址
     */
    private String router;

    /**
     * 描述
     */
    private String description;

    /**
     * 应用状态，0启用 1禁用
     */
    private Integer status;

    /**
     * 应用级别，private 私有/public 公开 默认private
     */
    private String level;
}
