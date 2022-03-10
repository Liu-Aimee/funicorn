package com.funicorn.cloud.system.center.entity;

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
 * 路由配置信息表
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("route_config")
public class RouteConfig extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;

  /**
   * 应用标识
   */
  @TableField(value = "app_id")
  private String appId;
    
  /**
   * 服务名称
   */
  @TableField(value = "name")
  private String name;

  /**
   * 转发地址
   */
  @TableField(value = "uri")
  private String uri;

  /**
   * 当前状态 默认0
   * 0未启用 1已启用
   */
  @TableField(value = "status")
  private String status;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 删除状态，0：未删，1：已删
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
