package com.funicorn.basic.cloud.security.entity;

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
 * 用户与租户关联表
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_tenant")
public class UserTenant extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id 雪花算法生成
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 用户id
   */
  @TableField(value = "user_id")
  private String userId;

  /**
   * 用户名账号
   */
  @TableField(value = "username")
  private String username;

  /**
   * 用户类别,-1超级管理员 0:普通用户,1:租户管理员
   */
  @TableField(value = "type")
  private Integer type;

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
}
