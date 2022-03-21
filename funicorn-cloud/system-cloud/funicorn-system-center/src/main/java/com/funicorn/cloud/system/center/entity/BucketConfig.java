package com.funicorn.cloud.system.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Aimee
 * @since 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bucket_config")
public class BucketConfig extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 桶id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 桶名
   */
  @TableField(value = "name")
  private String name;

  /**
   * 权限  public公开/private私有 默认public
   */
  @TableField(value = "level")
  private String level;

  /**
   * 桶内文件数量
   */
  @TableField(value = "count")
  private Integer count;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 是否删除 0 未删除 1已删除
   */
  @TableField(value = "is_delete")
  private String isDelete;

  /**
   * 删除日期
   * */
  @TableField(value = "delete_date")
  private Date deleteDate;

  /**
   * 可恢复状态 1可恢复 0无法恢复
   * */
  @TableField(value = "recovery")
  private String recovery;
}
