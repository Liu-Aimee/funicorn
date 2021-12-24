package com.funicorn.basic.cloud.security.vo;

import com.funicorn.basic.cloud.security.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/4/29 9:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {

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
     * 租户用户类型  -1超级管理员 0普通用户 1管理员
     * */
    private Integer type;
}
