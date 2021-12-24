package com.funicorn.cloud.upms.center.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/11/17 15:29
 */
@Data
public class TenantBindVO {

    /**
     * 已绑定数组
     * */
    private List<TenantInfo> bind;

    /**
     * 未绑定数组
     * */
    private List<TenantInfo> unbind;

    @Data
    public static class TenantInfo{
        /**
         * 租户id
         * */
        private String tenantId;

        /**
         * 租户名称
         * */
        private String tenantName;
    }
}
