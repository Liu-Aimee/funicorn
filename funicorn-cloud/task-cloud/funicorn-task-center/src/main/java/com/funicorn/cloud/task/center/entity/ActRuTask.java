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
 * 任务运行时
 * @author Aimee
 * @since 2021-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_ru_task")
public class ActRuTask extends Model<ActRuTask> implements Serializable{

  private static final long serialVersionUID=1L;

  @TableId(value = "ID_", type = IdType.ASSIGN_ID)
  private String id;
    
  @TableField(value = "REV_")
  private Integer rev;

  @TableField(value = "EXECUTION_ID_")
  private String executionId;

  @TableField(value = "PROC_INST_ID_")
  private String procInstId;

  @TableField(value = "PROC_DEF_ID_")
  private String procDefId;

  @TableField(value = "TASK_DEF_ID_")
  private String taskDefId;

  @TableField(value = "SCOPE_ID_")
  private String scopeId;

  @TableField(value = "SUB_SCOPE_ID_")
  private String subScopeId;

  @TableField(value = "SCOPE_TYPE_")
  private String scopeType;

  @TableField(value = "SCOPE_DEFINITION_ID_")
  private String scopeDefinitionId;

  @TableField(value = "PROPAGATED_STAGE_INST_ID_")
  private String propagatedStageInstId;

  @TableField(value = "NAME_")
  private String name;

  @TableField(value = "PARENT_TASK_ID_")
  private String parentTaskId;

  @TableField(value = "DESCRIPTION_")
  private String description;

  @TableField(value = "TASK_DEF_KEY_")
  private String taskDefKey;

  @TableField(value = "OWNER_")
  private String owner;

  @TableField(value = "ASSIGNEE_")
  private String assignee;

  @TableField(value = "DELEGATION_")
  private String delegation;

  @TableField(value = "PRIORITY_")
  private Integer priority;

  @TableField(value = "CREATE_TIME_")
  private LocalDateTime createTime;

  @TableField(value = "DUE_DATE_")
  private LocalDateTime dueDate;

  @TableField(value = "CATEGORY_")
  private String category;

  @TableField(value = "SUSPENSION_STATE_")
  private Integer suspensionState;

  @TableField(value = "TENANT_ID_")
  private String tenantId;

  @TableField(value = "FORM_KEY_")
  private String formKey;

  @TableField(value = "CLAIM_TIME_")
  private LocalDateTime claimTime;

  @TableField(value = "IS_COUNT_ENABLED_")
  private Integer isCountEnabled;

  @TableField(value = "VAR_COUNT_")
  private Integer varCount;

  @TableField(value = "ID_LINK_COUNT_")
  private Integer idLinkCount;

  @TableField(value = "SUB_TASK_COUNT_")
  private Integer subTaskCount;

  /**
   * 流程名称
   * */
  @TableField(exist = false)
  private String procInstName;
}
