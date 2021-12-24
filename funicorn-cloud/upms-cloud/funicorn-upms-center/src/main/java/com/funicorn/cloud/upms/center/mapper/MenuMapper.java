package com.funicorn.cloud.upms.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.cloud.upms.center.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据应用id查看菜单
     * @param appId 应用id
     * @return List<Menu>
     * */
    List<Menu> selectMenuByAppId(@Param("appId") String appId);

    /**
     * 根据应用id查看当前用户可用菜单
     * @param params 查询条件
     * @return List<Menu>
     * */
    List<Menu> selectCurrentMenus(Map<String, Object> params);

    /**
     * 根据角色查看菜单
     * @param roleId 角色id
     * @param appId 应用id
     * @return List<Menu>
     * */
    List<Menu> selectMenusByRoleId(@Param("roleId") String roleId, @Param("appId") String appId);

}
