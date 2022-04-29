package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.center.entity.Role;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface RoleService extends IService<Role> {

    /**
     * 删除角色
     * @param roleId 角色id
     * */
    void removeRole(String roleId);

}
