package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.center.dto.UserTenantDTO;
import com.funicorn.cloud.upms.center.entity.UserTenant;

/**
 * <p>
 * 用户与租户关系 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface UserTenantService extends IService<UserTenant> {

    /**
     * 用户与租户绑定
     * @param userTenantDTO 入参
     * */
    void bind(UserTenantDTO userTenantDTO);

    /**
     * 用户与租户解除绑定
     * @param userTenantDTO 入参
     * */
    void unbind(UserTenantDTO userTenantDTO);
}
