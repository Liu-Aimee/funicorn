package com.funicorn.cloud.upms.center.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/11/17 16:40
 */
@Data
public class TenantAppDTO {

    /**
     * 应用id
     * */
    private String appId;

    /**
     * 租户id数组
     * */
    private List<String> tenantIds;
}
