package com.funicorn.basic.cloud.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.basic.cloud.security.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色信息
     * @param userId 用户id
     * @param tenantId 租户id
     * @return List<Role>
     * */
    List<Role> selectRoleByUser(String userId, String tenantId);
}
