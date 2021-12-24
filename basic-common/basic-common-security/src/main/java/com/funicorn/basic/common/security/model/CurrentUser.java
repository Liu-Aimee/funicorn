package com.funicorn.basic.common.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.funicorn.basic.common.base.util.JsonUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Aimee
 * CurrentUser
 */
@JsonIgnoreProperties({"authorities","accountNonExpired", "accountNonLocked", "credentialsNonExpired","credentialsNonExpired"})
@Data
public class CurrentUser implements UserDetails {

    private static final long serialVersionUID = -1L;

    /**
     * 用户id
     * */
    private String id;
    /**
     * 用户名账号
     * */
    private String username;
    /**
     * 登录密码
     * */
    private String password;
    /**
     * 邮箱
     * */
    private String email;
    /**
     * 手机号
     * */
    private String mobile;
    /**
     * 证件类型
     * 0身份证 1港澳通行证 2学生证 3护照 4士官证 5驾驶证
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
     * 昵称
     * */
    private String nickName;
    /**
     * 用户头像id
     * */
    private String imgId;
    /**
     * 账号是否可用 0：不可用 ；1：可用
     * */
    private Integer enabled;
    /**
     * 密码失效时间
     * */
    @JsonFormat(pattern = JsonUtil.PATTERN, timezone = "GMT+8")
    private LocalDateTime expireTime;
    /**
     * 账号是否被锁定 0：锁定；1：正常
     * */
    private Integer locked;
    /**
     * 租户id
     * */
    private String tenantId;
    /**
     * 租户名称
     * */
    private String tenantName;
    /**
     * 机构id
     * */
    private String orgId;

    /**
     * 机构名称
     * */
    private String orgName;
    /**
     * 用户类别
     * -1:超级管理员 0:普通用户 1:租户管理员
     * */
    private Integer type;
    /**
     * 用户角色信息
     * */
    private List<RoleInfo> roles;
    /**
     * 角色权限列表
     * */
    private LinkedList<String> permissions;

    /**
     * 用户权限
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> authList = new ArrayList<>();

        if (this.permissions!=null && !this.permissions.isEmpty()){
            authList.addAll(this.permissions);
        }

        if (this.roles!=null && !roles.isEmpty()){
            for (RoleInfo roleInfo:this.roles) {
                if (roleInfo.getCode().startsWith("ROLE_")){
                    authList.add(roleInfo.getCode());
                }else {
                    authList.add("ROLE_" + roleInfo.getCode());
                }
            }
        }

        if (authList.isEmpty()){
            return null;
        }
        authList = authList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (authList.isEmpty()){
            return null;
        }
        return AuthorityUtils.createAuthorityList(authList.toArray(new String[0]));
    }

    /**
     * 账号是否未过期
     * 如果过期时间是空，则永久不过期
     * */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未锁定
     * */
    @Override
    public boolean isAccountNonLocked() {
        return this.locked == 1;
    }

    /**
     * 账号凭证是否未过期
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return (this.expireTime==null || this.expireTime.compareTo(LocalDateTime.now())>0);
    }

    /**
     * 账号是否可用
     * */
    @Override
    public boolean isEnabled() {
        return this.enabled==null || this.enabled==1;
    }
}
