package com.funicorn.cloud.upms.center.entity;

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
 * <p>
 * 用户信息管理
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User extends BaseModel implements Serializable{

  private static final long serialVersionUID=1L;

  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private String id;
    
  /**
   * 用户名账号
   */
  @TableField(value = "username")
  private String username;

  /**
   * 登录密码
   */
  @TableField(value = "password")
  private String password;

  /**
   * 邮箱
   */
  @TableField(value = "email")
  private String email;

  /**
   * 手机号
   */
  @TableField(value = "mobile")
  private String mobile;

  /**
   * 证件类型 0身份证 1港澳通行证 2学生证 3护照 4士官证 5驾驶证
   */
  @TableField(value = "id_type")
  private Integer idType;

  /**
   * 证件号码
   */
  @TableField(value = "id_card")
  private String idCard;

  /**
   * 联系地址
   */
  @TableField(value = "address")
  private String address;

  /**
   * 昵称
   */
  @TableField(value = "nick_name")
  private String nickName;

  /**
   * 用户头像
   */
  @TableField(value = "head_logo")
  private String headLogo;

  /**
   * 账号是否可用:0可用,1不可用
   */
  @TableField(value = "enabled")
  private Integer enabled;

  /**
   * 历史密码记录，保存最近3个
   */
  @TableField(value = "history_pwd")
  private String historyPwd;

  /**
   * 密码失效时间
   */
  @TableField(value = "expire_time")
  private LocalDateTime expireTime;

  /**
   * 账号是否被锁定:0正常,1锁定
   */
  @TableField(value = "locked")
  private Integer locked;

  /**
   * 软删标识 0未删 1已删
   */
  @TableField(value = "is_delete")
  private String isDelete;
}
