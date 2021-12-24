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
 * 数据字典
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dict_item")
public class DictItem extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 字典类型
   */
  @TableField(value = "dict_type")
  private String dictType;

  /**
   * 值
   */
  @TableField(value = "dict_value")
  private String dictValue;

  /**
   * 标签
   */
  @TableField(value = "dict_label")
  private String dictLabel;

  /**
   * 排序（升序）
   */
  @TableField(value = "sort")
  private Integer sort;

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
   * 删除状态 1是 0否
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
