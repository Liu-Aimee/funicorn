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
 * 文件上传信息
 * </p>
 *
 * @author Aimee
 * @since 2021-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("upload_file")
public class UploadFile extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 文件id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 文件名
   */
  @TableField(value = "file_name")
  private String fileName;

  /**
   * 文件名后缀名
   */
  @TableField(value = "suffix")
  private String suffix;

  /**
   * 文件大小 单位字节
   */
  @TableField(value = "size")
  private Long size;

  /**
   * 桶名称
   * */
  @TableField(value = "bucket_name")
  private String bucketName;

  /**
   * 文件级别 PRIVATE/PUBLIC
   * */
  @TableField(value = "file_level")
  private String fileLevel;

  /**
   * 租户id
   */
  @TableField(value = "tenant_id")
  private String tenantId;

  /**
   * 是否提供下载
   */
  @TableField(value = "down_flag")
  private Boolean downFlag;

  /**
   * 下载次数
   */
  @TableField(value = "down_count")
  private Integer downCount;

  /**
   * 软删标识 0未删 1已删
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
