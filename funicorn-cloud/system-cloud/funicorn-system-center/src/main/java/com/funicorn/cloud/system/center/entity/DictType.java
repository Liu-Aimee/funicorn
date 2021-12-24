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
 * 字典类型
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dict_type")
public class DictType extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 字典类型
   */
  @TableField(value = "type")
  private String type;

  /**
   * 类型名称
   */
  @TableField(value = "name")
  private String name;

  /**
   * 描述
   */
  @TableField(value = "remark")
  private String remark;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 软删标识 0 未删 1已删
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
