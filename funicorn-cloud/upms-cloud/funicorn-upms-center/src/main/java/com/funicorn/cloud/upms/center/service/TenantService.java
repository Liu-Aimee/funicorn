package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.center.dto.TenantDTO;
import com.funicorn.cloud.upms.center.entity.Tenant;

/**
 * <p>
 * 租户管理 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface TenantService extends IService<Tenant> {

    /**
     * 新增
     * @param tenantDTO 入参
     * @return Tenant
     * */
    Tenant addTenant(TenantDTO tenantDTO);

    /**
     * 修改租户
     * @param tenantDTO 入参
     * */
    void updateTenant(TenantDTO tenantDTO);
}
