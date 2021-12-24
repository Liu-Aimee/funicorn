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
 * 用户操作日志管理
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log")
public class SysLog extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 中文描述
   */
  @TableField(value = "content")
  private String content;

  /**
   * 操作类型  update/delete/insert/query
   */
  @TableField(value = "operation_type")
  private String operationType;

  /**
   * 日志类型 操作日志/operation 错误日志/error 登录登出日志/login
   */
  @TableField(value = "log_type")
  private String logType;

  /**
   * 错误日志内容
   */
  @TableField(value = "message")
  private String message;

  /**
   * 操作用户账号
   */
  @TableField(value = "user_id")
  private String userId;

  /**
   * 操作用户账号
   */
  @TableField(value = "username")
  private String username;

  /**
   * 客户端标识
   */
  @TableField(value = "service_name")
  private String serviceName;

  /**
   * 客户端ip地址
   */
  @TableField(value = "remote_addr")
  private String remoteAddr;

  /**
   * 后台函数路径
   */
  @TableField(value = "method")
  private String method;

  /**
   * 请求路径
   */
  @TableField(value = "request_url")
  private String requestUrl;

  /**
   * 请求参数
   */
  @TableField(value = "request_param")
  private String requestParam;

  /**
   * 请求类型 post/get/patch/delete
   */
  @TableField(value = "request_type")
  private String requestType;

  /**
   * 耗时 单位ms
   */
  @TableField(value = "cost_time")
  private Long costTime;

  /**
   * 租户id
   * */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 删除状态
   */
  @TableField(value = "is_delete")
  private Integer isDelete;
}
