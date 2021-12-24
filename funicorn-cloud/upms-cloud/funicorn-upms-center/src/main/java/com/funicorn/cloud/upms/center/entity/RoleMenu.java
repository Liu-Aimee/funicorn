package com.funicorn.cloud.upms.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色与菜单关系
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("role_menu")
public class RoleMenu extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 角色ID
   */
  @TableField(value = "role_id")
  private String roleId;

  /**
   * 菜单ID
   */
  @TableField(value = "menu_id")
  private String menuId;

  /**
   * 应用id
   */
  @TableField(value = "app_id")
  private String appId;

  /**
   * 租户ID
   */
  @TableField(value = "tenant_id")
  private String tenantId;
}
