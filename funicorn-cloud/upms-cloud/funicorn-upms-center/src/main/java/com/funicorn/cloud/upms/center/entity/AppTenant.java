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
 * 应用与租户关系
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("app_tenant")
public class AppTenant extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 应用id
   */
  @TableField(value = "app_id")
  private String appId;

  /**
   * 应用名称
   */
  @TableField(value = "app_name")
  private String appName;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 租户名称
   */
  @TableField(value = "tenant_name")
  private String tenantName;

  /**
   * 0:已开通 1:已禁用 2:申请开通中 3:拒绝开通
   */
  @TableField(value = "status")
  private Integer status;
}
