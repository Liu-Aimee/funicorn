package com.funicorn.cloud.task.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * 动态模型
 * @author Aimee
 * @since 2021-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_de_model")
public class ActDeModel extends Model<ActDeModel> implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   * */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;

  /**
   * 流程模型名称
   * */
  @TableField(value = "name")
  private String name;

  /**
   * 流程模型key
   * */
  @TableField(value = "model_key")
  private String modelKey;

  /**
   * 流程模型描述
   * */
  @TableField(value = "description")
  private String description;

  /**
   * 备注
   * */
  @TableField(value = "model_comment")
  private String modelComment;

  /**
   * 创建时间
   * */
  @TableField(value = "created")
  private LocalDateTime created;

  /**
   * 创建人
   * */
  @TableField(value = "created_by")
  private String createdBy;

  /**
   * 最后更新时间
   * */
  @TableField(value = "last_updated")
  private LocalDateTime lastUpdated;

  /**
   * 最后更新人
   * */
  @TableField(value = "last_updated_by")
  private String lastUpdatedBy;

  /**
   * 版本号
   * */
  @TableField(value = "version")
  private Integer version;

  /**
   * 模型采用json格式
   * */
  @TableField(value = "model_editor_json")
  private String modelEditorJson;

  /**
   * 图片流
   * */
  @TableField(value = "thumbnail")
  private Blob thumbnail;

  /**
   * 流程引擎类型
   * */
  @TableField(value = "model_type")
  private Integer modelType;

  /**
   * 租户id
   * */
  @TableField(value = "tenant_id")
  private String tenantId;
}
