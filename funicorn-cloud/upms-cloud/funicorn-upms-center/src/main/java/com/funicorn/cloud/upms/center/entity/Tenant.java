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
 * 租户管理
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tenant")
public class Tenant extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键 租户id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 租户名称
   */
  @TableField(value = "tenant_name")
  private String tenantName;

  /**
   * logo地址
   */
  @TableField(value = "logo_url")
  private String logoUrl;

  /**
   * 描述
   */
  @TableField(value = "description")
  private String description;

  /**
   * 是否删除，1删除
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
