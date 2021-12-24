package com.funicorn.basic.common.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/11/2 11:34
 */
@Data
public class BearerToken {

    /**
     * 用户信息
     * */
    private CurrentUser currentUser;

    /**
     * 作用域
     * */
    private List<String> scope;

    /**
     * 活跃状态
     * */
    private boolean active;

    /**
     * 过期时间
     * */
    @JsonFormat(locale = "zh",pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone="GMT+8")
    private LocalDateTime exp;

    /**
     * 权限列表
     * */
    private List<String> authorities;

    /**
     * 客户端标识
     * */
    private String client_id;
}
