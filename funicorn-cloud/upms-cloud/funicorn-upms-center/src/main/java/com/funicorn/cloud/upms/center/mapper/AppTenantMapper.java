package com.funicorn.cloud.upms.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.cloud.upms.center.entity.AppTenant;

import java.util.List;

/**
 * <p>
 * 应用与租户关系 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface AppTenantMapper extends BaseMapper<AppTenant> {

    /**
     * 查询已绑定的租户列表
     * @param appId 应用id
     * @return list
     * */
    List<AppTenant> selectBindTenantList(String appId);

    /**
     * 查询未绑定的租户列表
     * @param userId 用户id
     * @param appId 应用id
     * @return list
     * */
    List<AppTenant> selectUnbindTenantList(String userId,String appId);
}
