package com.funicorn.cloud.upms.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.cloud.upms.center.entity.Role;
import com.funicorn.cloud.upms.center.entity.UserRole;

import java.util.List;

/**
 * <p>
 * 用户与角色关系 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 查询用户的所有角色信息
     * @param tenantId 租户id
     * @param userId 用户id
     * @return List
     * */
    List<Role> selectRolesByUserId(String tenantId,String userId);
}
