package com.funicorn.cloud.task.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史任务
 * @author Aimee
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_hi_taskinst")
public class ActHiTaskinst extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  @TableId(value = "ID_", type = IdType.ASSIGN_ID)
  private String id;
    
  @TableField(value = "REV_")
  private Integer rev;

  @TableField(value = "PROC_DEF_ID_")
  private String procDefId;

  @TableField(value = "TASK_DEF_ID_")
  private String taskDefId;

  @TableField(value = "TASK_DEF_KEY_")
  private String taskDefKey;

  @TableField(value = "PROC_INST_ID_")
  private String procInstId;

  @TableField(value = "EXECUTION_ID_")
  private String executionId;

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

  @TableField(value = "OWNER_")
  private String owner;

  @TableField(value = "ASSIGNEE_")
  private String assignee;

  @TableField(value = "START_TIME_")
  private LocalDateTime startTime;

  @TableField(value = "CLAIM_TIME_")
  private LocalDateTime claimTime;

  @TableField(value = "END_TIME_")
  private LocalDateTime endTime;

  @TableField(value = "DURATION_")
  private Long duration;

  @TableField(value = "DELETE_REASON_")
  private String deleteReason;

  @TableField(value = "PRIORITY_")
  private Integer priority;

  @TableField(value = "DUE_DATE_")
  private LocalDateTime dueDate;

  @TableField(value = "FORM_KEY_")
  private String formKey;

  @TableField(value = "CATEGORY_")
  private String category;

  @TableField(value = "TENANT_ID_")
  private String tenantId;

  @TableField(value = "LAST_UPDATED_TIME_")
  private LocalDateTime lastUpdatedTime;
}
