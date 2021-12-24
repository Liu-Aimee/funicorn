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
 * 用户与机构关系
 * </p>
 *
 * @author Aimee
 * @since 2021-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_org")
public class UserOrg extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 组织机构id
   */
  @TableField(value = "org_id")
  private String orgId;

  /**
   * 组织机构名称
   */
  @TableField(value = "org_name")
  private String orgName;

  /**
   * 用户id
   */
  @TableField(value = "user_id")
  private String userId;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;
}
