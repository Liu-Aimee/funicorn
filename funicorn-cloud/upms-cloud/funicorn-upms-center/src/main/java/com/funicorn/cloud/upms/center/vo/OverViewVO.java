package com.funicorn.cloud.upms.center.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aimee
 * @since 2021/1/26 17:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverViewVO {

    /**
     * 用户总数
     * */
    private Integer userCount;

    /**
     * 租户总数
     * */
    private Integer tenantCount;

    /**
     * 应用总数
     * */
    private Integer clientCount;

    /**
     * 角色总数
     * */
    private Integer roleCount;
}
