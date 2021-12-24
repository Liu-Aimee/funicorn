package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.model.RoleInfo;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.api.model.MenuTree;
import com.funicorn.cloud.upms.center.constant.LeveEnum;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.MenuDTO;
import com.funicorn.cloud.upms.center.entity.AppTenant;
import com.funicorn.cloud.upms.center.entity.Menu;
import com.funicorn.cloud.upms.center.entity.UserTenant;
import com.funicorn.cloud.upms.center.mapper.AppTenantMapper;
import com.funicorn.cloud.upms.center.mapper.MenuMapper;
import com.funicorn.cloud.upms.center.mapper.UserTenantMapper;
import com.funicorn.cloud.upms.center.service.MenuService;
import com.funicorn.cloud.upms.center.service.UserRoleService;
import com.funicorn.cloud.upms.center.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private AppTenantMapper appTenantMapper;
    @Resource
    private UserTenantMapper userTenantMapper;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public List<MenuTree> getCurrentMenusByAppId(String appId) {
        List<MenuTree> menuTreeList = new ArrayList<>();
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        if (currentUser.getRoles()==null || currentUser.getRoles().isEmpty()){
            return menuTreeList;
        }
        Map<String, Object> params = new HashMap<>(2);
        params.put("appId",appId);
        params.put("roleIds",currentUser.getRoles().stream().map(RoleInfo::getId).collect(Collectors.toList()));
        List<Menu> menus = baseMapper.selectCurrentMenus(params);
        if (menus==null || menus.isEmpty()){
            return menuTreeList;
        }
        menus = menus.stream().filter(f->UpmsConstant.TYPE_MENU.equals(f.getType())).collect(Collectors.toList());
        menus.forEach(menu->{
            MenuTree menuTree = JsonUtil.object2Object(menu, MenuTree.class);
            menuTreeList.add(menuTree);
        });
        return TreeUtil.buildMenuTree(menuTreeList);
    }

    @Override
    public List<MenuTree> getMenusByAppId(String appId) {
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        if (currentUser.getRoles()==null || currentUser.getRoles().isEmpty()){
            return new ArrayList<>();
        }
        Map<String, Object> params = new HashMap<>(2);
        params.put("appId",appId);
        params.put("roleIds",currentUser.getRoles().stream().map(RoleInfo::getId).collect(Collectors.toList()));
        List<Menu> menus = baseMapper.selectCurrentMenus(params);

        List<MenuTree> menuTreeList = new ArrayList<>();

        if (menus==null || menus.isEmpty()){
            return menuTreeList;
        }
        menus.forEach(menu->{
            MenuTree menuTree = JsonUtil.object2Object(menu, MenuTree.class);
            menuTreeList.add(menuTree);
        });
        return TreeUtil.buildMenuTree(menuTreeList);
    }

    @Override
    public void saveMenu(MenuDTO menuDTO) {
        //1、先查询一下之前是不是以前删掉的菜单，是的直接恢复
        Menu delMenu = baseMapper.selectOne(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getParentId, StringUtils.isNotBlank(menuDTO.getParentId()) ? menuDTO.getParentId() : "0")
                .eq(Menu::getName, menuDTO.getName())
                .eq(Menu::getAppId, menuDTO.getAppId())
                .eq(Menu::getIsDelete, UpmsConstant.IS_DELETED));
        if (delMenu!=null){
            Menu recoverMenu = JsonUtil.object2Object(menuDTO,Menu.class);
            recoverMenu.setLevel(menuDTO.isAutomatic() ? LeveEnum.PUBLIC.toString() : LeveEnum.PRIVATE.toString());
            recoverMenu.setId(delMenu.getId());
            recoverMenu.setIsDelete(UpmsConstant.NOT_DELETED);
            baseMapper.updateById(recoverMenu);
            return;
        }

        //2、先保存菜单
        Menu newMenu = JsonUtil.object2Object(menuDTO,Menu.class);
        newMenu.setLevel(menuDTO.isAutomatic() ? LeveEnum.PUBLIC.toString() : LeveEnum.PRIVATE.toString());
        baseMapper.insert(newMenu);

        //3、授权给自己
        if (UpmsConstant.TENANT_USER_SUPER.equals(SecurityUtil.getCurrentUser().getType())) {
            userRoleService.initRoleSuperAdmin(SecurityUtil.getCurrentUser().getId(),SecurityUtil.getCurrentUser().getTenantId());
        } else {
            userRoleService.initRoleTenantAdmin(SecurityUtil.getCurrentUser().getId(),SecurityUtil.getCurrentUser().getTenantId());
        }

        //4、是否自动绑定租户管理员角色 这个可能耗时很长
        if (menuDTO.isAutomatic()) {
            List<AppTenant> appTenants = appTenantMapper.selectList(Wrappers.<AppTenant>lambdaQuery()
                    .eq(AppTenant::getAppId,newMenu.getAppId()).in(AppTenant::getStatus, Arrays.asList(0,1)));
            appTenants.forEach(appTenant -> {
                List<UserTenant> userTenants = userTenantMapper.selectList(Wrappers.<UserTenant>lambdaQuery()
                        .eq(UserTenant::getTenantId,appTenant.getTenantId()).eq(UserTenant::getType, UpmsConstant.TENANT_USER_ADMIN)
                        .ne(UserTenant::getUserId,SecurityUtil.getCurrentUser().getId()));
                userTenants.forEach(userTenant -> userRoleService.initRoleTenantAdmin(userTenant.getUserId(),userTenant.getTenantId()));
            });
        }
    }

    @Override
    public List<MenuTree> getMenusByRoleId(String roleId, String appId) {

        List<Menu> menus = baseMapper.selectMenusByRoleId(roleId,appId);
        List<MenuTree> menuTreeList = new ArrayList<>();

        if (menus==null || menus.isEmpty()){
            return menuTreeList;
        }

        menus.forEach(menu->{
            MenuTree menuTree = JsonUtil.object2Object(menu, MenuTree.class);
            menuTreeList.add(menuTree);
        });
        return TreeUtil.buildMenuTree(menuTreeList);
    }
}
