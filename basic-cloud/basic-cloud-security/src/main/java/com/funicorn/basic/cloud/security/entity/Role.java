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
 * 角色信息表
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("role")
public class Role extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 角色名称
   */
  @TableField(value = "name")
  private String name;

  /**
   * 角色编号
   */
  @TableField(value = "code")
  private String code;

  /**
   * 租户id 默认-1
   */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 是否删除 0 未删除 1已删除
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
