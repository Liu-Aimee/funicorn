package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.center.dto.RoleMenuDTO;
import com.funicorn.cloud.upms.center.entity.RoleMenu;

/**
 * <p>
 * 角色与菜单关系 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 菜单绑定角色
     * @param roleMenuDTO 入参
     * */
    void bind(RoleMenuDTO roleMenuDTO);
}
