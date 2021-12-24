package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.api.model.MenuTree;
import com.funicorn.cloud.upms.center.dto.MenuDTO;
import com.funicorn.cloud.upms.center.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询当前登录人选择的应用的左侧菜单树
     * @param appId 应用id
     * @return TreeMenuVo
     * */
    List<MenuTree> getCurrentMenusByAppId(String appId);

    /**
     * 根据应用id查询菜单树
     * @param appId 应用id
     * @return TreeMenuVo
     * */
    List<MenuTree> getMenusByAppId(String appId);

    /**
     * 新增菜单
     * @param menuDTO 入参
     * */
    void saveMenu(MenuDTO menuDTO);

    /**
     * 根据角色查看菜单
     * @param roleId 角色id集合
     * @param appId 应用id
     * @return MenuTree
     * */
    List<MenuTree> getMenusByRoleId(String roleId, String appId);
}
