package com.funicorn.cloud.upms.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 应用管理
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class App extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 应用名称
   */
  @TableField(value = "name")
  private String name;

  /**
   * 应用标识
   */
  @TableField(value = "client_id")
  private String clientId;

  /**
   * 应用密钥
   */
  @TableField(value = "client_secret")
  private String clientSecret;

  /**
   * 可访问资源列表
   */
  @TableField(value = "resource_ids")
  private String resourceIds;

  /**
   * 授权范围 read,write,all
   */
  @TableField(value = "scope")
  private String scope;

  /**
   * 授权模式:authorization_code 授权码,password 密码,refresh_token 刷新access_token,implicit 简化,client_credentials 客户端
   */
  @TableField(value = "authorized_grant_types")
  private String authorizedGrantTypes;

  /**
   * 回调地址
   */
  @TableField(value = "web_server_redirect_uri")
  private String webServerRedirectUri;

  @TableField(value = "authorities")
  private String authorities;

  /**
   * 令牌有效期 单位秒 默认7200
   */
  @TableField(value = "access_token_validity")
  private Integer accessTokenValidity;

  /**
   * 刷新令牌有效期 单位秒 默认 14400
   */
  @TableField(value = "refresh_token_validity")
  private Integer refreshTokenValidity;

  /**
   * 附加信息
   */
  @TableField(value = "additional_information")
  private String additionalInformation;

  /**
   * 自动批准 true/false
   */
  @TableField(value = "autoapprove")
  private String autoapprove;

  /**
   * logo地址
   */
  @TableField(value = "logo_url")
  private String logoUrl;

  /**
   * 路由地址
   */
  @TableField(value = "router")
  private String router;

  /**
   * 应用的访问地址
   */
  @TableField(value = "url")
  private String url;

  /**
   * 描述
   */
  @TableField(value = "description")
  private String description;

  /**
   * 应用级别，private:私有/public:公开 默认private
   */
  @TableField(value = "level")
  private String level;

  /**
   * 删除标识 0 未删 1已删
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
