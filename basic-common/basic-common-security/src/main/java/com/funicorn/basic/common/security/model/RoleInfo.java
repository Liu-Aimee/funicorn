package com.funicorn.basic.common.security.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/4/29 10:37
 */
@Data
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     * */
    private String id;

    /**
     * 角色编码
     * */
    private String code;

    /**
     * 角色名称
     * */
    private String name;
}
