package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/31 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppPageDTO extends PageDTO {

    /**
     * 应用标识
     * */
    private String clientId;

    /**
     * 应用名称
     * */
    private String name;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 角色id数组
     * */
    private List<String> roleIds;

    /**
     * 租户id 默认当前登陆人选择的租户
     * */
    private String tenantId;
}
