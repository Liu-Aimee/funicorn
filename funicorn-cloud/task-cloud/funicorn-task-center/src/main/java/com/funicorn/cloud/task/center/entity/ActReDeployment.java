package com.funicorn.cloud.task.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程部署
 * @author Aimee
 * @since 2021-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_re_deployment")
public class ActReDeployment extends Model<ActReDeployment> implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 流程id
   * */
  @TableId(value = "ID_", type = IdType.ASSIGN_ID)
  private String id;

  /**
   * 流程名称
   * */
  @TableField(value = "NAME_")
  private String name;

  /**
   * 流程分类
   * */
  @TableField(value = "CATEGORY_")
  private String category;

  /**
   * 流程key
   * */
  @TableField(value = "KEY_")
  private String key;

  /**
   * 租户id
   * */
  @TableField(value = "TENANT_ID_")
  private String tenantId;

  /**
   * 部署时间
   * */
  @TableField(value = "DEPLOY_TIME_")
  private LocalDateTime deployTime;

  /**
   * 表单相关
   * */
  @TableField(value = "DERIVED_FROM_")
  private String derivedFrom;

  /**
   * 表单相关
   * */
  @TableField(value = "DERIVED_FROM_ROOT_")
  private String derivedFromRoot;

  /**
   * 父id
   * */
  @TableField(value = "PARENT_DEPLOYMENT_ID_")
  private String parentDeploymentId;

  /**
   * 引擎版本
   * */
  @TableField(value = "ENGINE_VERSION_")
  private String engineVersion;
}
