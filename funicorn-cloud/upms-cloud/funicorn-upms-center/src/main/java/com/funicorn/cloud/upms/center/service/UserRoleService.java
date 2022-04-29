package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.center.dto.UserRoleDTO;
import com.funicorn.cloud.upms.center.entity.Role;
import com.funicorn.cloud.upms.center.entity.UserRole;

import java.util.List;

/**
 * <p>
 * 用户与角色关系 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 初始化超级管理员角色
     * @param tenantId 租户id
     * @param userId 用户id
     * */
    void initRoleSuperAdmin(String userId,String tenantId);

    /**
     * 初始化租户管理员角色
     * @param tenantId 租户id
     * @param userId 用户id
     * */
    void initRoleTenantAdmin(String userId,String tenantId);

    /**
     * 查询用户的所有角色信息
     * @param tenantId 租户id
     * @param userId 用户id
     * @return List
     * */
    List<Role> getRolesByUserId(String tenantId,String userId);

    /**
     * 角色关联用户
     * @param userRoleDTO 入参
     * */
    void userBindRole(UserRoleDTO userRoleDTO);
}
