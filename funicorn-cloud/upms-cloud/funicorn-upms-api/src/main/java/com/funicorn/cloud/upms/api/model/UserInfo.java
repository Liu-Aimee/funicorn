package com.funicorn.cloud.upms.api.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息
 * @author Aimee
 * @since 2021/1/26 18:12
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * */
    private String id;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 昵称
     * */
    private String nickName;

    /**
     * 用户类型
     * */
    private Integer type;

    /**
     * 邮箱
     * */
    private String email;

    /**
     * 手机号
     * */
    private String mobile;

    /**
     * 证件类型 0：身份证
     * */
    private Integer idType;

    /**
     * 证件号码
     * */
    private String idCard;

    /**
     * 联系地址
     * */
    private String address;

    /**
     * 租户id
     * */
    private String tenantId;

    /**
     * 用户头像id
     * */
    private String imgId;

    /**
     * 密码失效时间
     * */
    private LocalDateTime expireTime;

    /**
     * 角色信息
     * */
    private List<RoleInfo> roleInfos;

    /**
     * 组织机构id
     * */
    private String orgId;

    /**
     * 组织机构名称
     * */
    private String orgName;

    /**
     * 职务信息
     * */
    private List<PositionInfo> positions;

    /**
     * 账号是否可用 1：不可用 ；0：可用
     * */
    private Integer enabled;

    /**
     * 账号是否被锁定 1：锁定；0：正常
     * */
    private Integer locked;

    /**
     * 用户与租户关系
     * */
    private List<UserTenInfo> userTenInfos;
}
