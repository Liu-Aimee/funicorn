package com.funicorn.cloud.task.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Aimee
 * @since 2021-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_hi_actinst")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActHiActinst extends Model<ActHiActinst> implements Serializable{

  private static final long serialVersionUID=1L;

  @TableId(value = "ID_", type = IdType.ASSIGN_ID)
  private String id;
    
  @TableField(value = "REV_")
  private Integer rev;

  @TableField(value = "PROC_DEF_ID_")
  private String procDefId;

  @TableField(value = "PROC_INST_ID_")
  private String procInstId;

  @TableField(value = "EXECUTION_ID_")
  private String executionId;

  @TableField(value = "ACT_ID_")
  private String actId;

  @TableField(value = "TASK_ID_")
  private String taskId;

  @TableField(value = "CALL_PROC_INST_ID_")
  private String callProcInstId;

  @TableField(value = "ACT_NAME_")
  private String actName;

  @TableField(value = "ACT_TYPE_")
  private String actType;

  @TableField(value = "ASSIGNEE_")
  private String assignee;

  @TableField(value = "START_TIME_")
  private LocalDateTime startTime;

  @TableField(value = "END_TIME_")
  private LocalDateTime endTime;

  @TableField(value = "TRANSACTION_ORDER_")
  private Integer transactionOrder;

  @TableField(value = "DURATION_")
  private Long duration;

  @TableField(value = "DELETE_REASON_")
  private String deleteReason;

  @TableField(value = "TENANT_ID_")
  private String tenantId;
}
