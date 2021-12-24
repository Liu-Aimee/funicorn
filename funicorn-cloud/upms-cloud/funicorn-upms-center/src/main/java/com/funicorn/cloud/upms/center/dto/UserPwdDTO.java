package com.funicorn.cloud.upms.center.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2020/8/10 11:55
 */
@Data
public class UserPwdDTO {

    /**
     * 用户id
     * */
    private String userId;

    /**
     * 用户登录密码
     * */
    private String oldPassword;

    /**
     * 密码
     * */
    private String password;
}
