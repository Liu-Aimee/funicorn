package com.funicorn.basic.cloud.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.basic.cloud.security.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色id查询菜单信息
     * @param roleId 角色id
     * @param tenantId 租户id
     * @return List<Menu>
     * */
    List<Menu> selectMenuByUser(String roleId, String tenantId);
}
