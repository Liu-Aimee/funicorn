package com.funicorn.cloud.chart.center.entity;

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
 * 接口函数表
 * </p>
 *
 * @author Aimee
 * @since 2021-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("function_http")
public class FunctionHttp extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   * */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;

  /**
   * 接口名称
   * */
  @TableField(value = "name")
  private String name;

  /**
   * 请求类型 GET/POST
   * */
  @TableField(value = "method")
  private String method;

  /**
   * 请求地址
   * */
  @TableField(value = "url")
  private String url;

  /**
   * 请求超时时间
   * */
  @TableField(value = "connect_time_out")
  private Integer connectTimeOut;

  /**
   * 读取超时时间
   * */
  @TableField(value = "read_time_out")
  private Integer readTimeOut;

  /**
   * 软件标识 0未删 1已删
   * */
  @TableField(value = "is_delete")
  private String isDelete;
}
