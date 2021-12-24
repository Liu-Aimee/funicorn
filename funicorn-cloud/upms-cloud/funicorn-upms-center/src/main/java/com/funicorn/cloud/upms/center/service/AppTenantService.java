package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.center.dto.AppTenantDTO;
import com.funicorn.cloud.upms.center.dto.ApprovalAppDTO;
import com.funicorn.cloud.upms.center.dto.TenantAppDTO;
import com.funicorn.cloud.upms.center.entity.AppTenant;
import com.funicorn.cloud.upms.center.vo.TenantBindVO;

/**
 * <p>
 * 应用与租户关系 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface AppTenantService extends IService<AppTenant> {

    /**
     * 应用绑定租户
     * @param tenantAppDTO 入参
     * */
    void bindTenant(TenantAppDTO tenantAppDTO);

    /**
     * 租户与应用绑定
     * @param appTenantDTO 入参
     * */
    void addAppTenants(AppTenantDTO appTenantDTO);

    /**
     * 租户申请开通应用
     * @param appTenantDTO 入参
     * */
    void apply(AppTenantDTO appTenantDTO);

    /**
     * 审批 approval
     * @param approvalAppDTO 0 同意 1拒绝
     */
    void approval(ApprovalAppDTO approvalAppDTO);

    /**
     * 查询应用已绑定和未绑定的租户列表
     * @param appId 应用id
     * @return TenantBindVO
     */
    TenantBindVO bindTenantList(String appId);
}
