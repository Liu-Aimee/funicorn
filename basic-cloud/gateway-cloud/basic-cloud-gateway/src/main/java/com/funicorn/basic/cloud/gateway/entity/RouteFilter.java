package com.funicorn.basic.cloud.gateway.entity;

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
 * 
 * </p>
 *
 * @author Aimee
 * @since 2022-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("route_filter")
public class RouteFilter extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;

  /**
   * 路由id
   */
  @TableField(value = "route_id")
  private String routeId;
    
  /**
   * 类型 AddRequestHeader/AddRequestParameter/AddResponseHeader/Hystrix/PrefixPath/PreserveHostHeader/name/RedirectTo/RemoveRequestHeader/RemoveResponseHeader/RewritePath/RewriteResponseHeader/SaveSession/SetPath/SetResponseHeader/SetStatus/StripPrefix
   */
  @TableField(value = "type")
  private String type;

  /**
   * 值
   */
  @TableField(value = "value")
  private String value;

  /**
   * 启用状态 0未启用 1已启用 默认1
   */
  @TableField(value = "status")
  private String status;
}
