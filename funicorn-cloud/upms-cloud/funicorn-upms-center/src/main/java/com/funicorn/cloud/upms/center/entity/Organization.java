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
 * 组织机构管理
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("organization")
public class Organization extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 父id
   */
  @TableField(value = "parent_id")
  private String parentId;

  /**
   * 机构名称·
   */
  @TableField(value = "name")
  private String name;

  /**
   * 备注
   */
  @TableField(value = "remark")
  private String remark;

  /**
   * 排序 升序
   */
  @TableField(value = "sort")
  private Integer sort;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 是否删除:1删除,0未删
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
