package com.funicorn.cloud.upms.center.entity;

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
 * 菜单管理
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("menu")
public class Menu extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 父ID
   */
  @TableField(value = "parent_id")
  private String parentId;

  /**
   * 应用id
   */
  @TableField(value = "app_id")
  private String appId;

  /**
   * 类型（menu/button）
   */
  @TableField(value = "type")
  private String type;

  /**
   * 名称
   */
  @TableField(value = "name")
  private String name;

  /**
   * 权限
   */
  @TableField(value = "permission")
  private String permission;

  /**
   * 路由
   */
  @TableField(value = "router")
  private String router;

  /**
   * status: hidden->隐藏,  show->显示
   */
  @TableField(value = "status")
  private String status;

  /**
   * 级别 public/private
   */
  @TableField(value = "level")
  private String level;

  /**
   * 是否删除 0未删 1已删
   */
  @TableField(value = "is_delete")
  private String isDelete;

  /**
   * 图标
   */
  @TableField(value = "icon")
  private String icon;

  /**
   * 排序 升序
   */
  @TableField(value = "sort")
  private Integer sort;
}
