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
 * 用户与角色关系
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_role")
public class UserRole extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 用户id
   */
  @TableField(value = "user_id")
  private String userId;

  /**
   * 角色id
   */
  @TableField(value = "role_id")
  private String roleId;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;
}
