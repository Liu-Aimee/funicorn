package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.cloud.upms.center.constant.LeveEnum;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.entity.*;
import com.funicorn.cloud.upms.center.mapper.*;
import com.funicorn.cloud.upms.center.service.RoleAppService;
import com.funicorn.cloud.upms.center.service.RoleMenuService;
import com.funicorn.cloud.upms.center.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户与角色关系 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AppTenantMapper appTenantMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleAppService roleAppService;
    @Resource
    private RoleMenuService roleMenuService;

    @Override
    public void initRoleSuperAdmin(String userId, String tenantId) {
        //1、初始化租户管理员角色
        Role role = roleMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getCode, UpmsConstant.ROLE_SUPER_ADMIN).eq(Role::getTenantId,tenantId)
                .eq(Role::getIsDelete,UpmsConstant.NOT_DELETED));
        if (role==null){
            role = new Role();
            role.setCode(UpmsConstant.ROLE_SUPER_ADMIN);
            role.setName("超级管理员");
            role.setTenantId(tenantId);
            roleMapper.insert(role);
        }

        //2、绑定用户与角色关系
        UserRole userRole = baseMapper.selectOne(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getRoleId,role.getId())
                .eq(UserRole::getUserId,userId)
                .eq(UserRole::getTenantId,tenantId));
        if (userRole==null){
            userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            userRole.setTenantId(tenantId);
            baseMapper.insert(userRole);
        }

        //3、绑定角色与应用关系
        List<AppTenant> appTenants = appTenantMapper.selectList(Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,tenantId));
        if (appTenants!=null && !appTenants.isEmpty()){
            List<RoleApp> roleApps = new ArrayList<>();
            for (AppTenant appTenant:appTenants) {
                //已存在 执行continue
                int count = roleAppService.count(Wrappers.<RoleApp>lambdaQuery()
                        .eq(RoleApp::getRoleId,role.getId())
                        .eq(RoleApp::getAppId,appTenant.getAppId())
                        .eq(RoleApp::getTenantId,tenantId));
                if (count>0){
                    continue;
                }
                RoleApp roleApp = new RoleApp();
                roleApp.setRoleId(role.getId());
                roleApp.setTenantId(tenantId);
                roleApp.setAppId(appTenant.getAppId());
                roleApps.add(roleApp);
            }
            if(!roleApps.isEmpty()){
                roleAppService.saveBatch(roleApps);
            }

            //4、绑定角色与菜单关系
            List<Menu> menus = menuMapper.selectList(Wrappers.<Menu>lambdaQuery()
                    .in(Menu::getAppId,appTenants.stream().map(AppTenant::getAppId).collect(Collectors.toList()))
                    .eq(Menu::getIsDelete, UpmsConstant.NOT_DELETED));
            if (menus!=null && !menus.isEmpty()){
                List<RoleMenu> roleMenus = new ArrayList<>();
                for (Menu menu:menus) {
                    //已存在 执行continue
                    int count = roleMenuService.count(Wrappers.<RoleMenu>lambdaQuery()
                            .eq(RoleMenu::getRoleId,role.getId())
                            .eq(RoleMenu::getAppId,menu.getAppId())
                            .eq(RoleMenu::getMenuId,menu.getId())
                            .eq(RoleMenu::getTenantId,tenantId));
                    if (count>0){
                        continue;
                    }
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setTenantId(tenantId);
                    roleMenu.setMenuId(menu.getId());
                    roleMenu.setAppId(menu.getAppId());
                    roleMenus.add(roleMenu);
                }

                if (!roleMenus.isEmpty()){ roleMenuService.saveBatch(roleMenus); }
            }
        }
    }

    @Override
    public void initRoleTenantAdmin(String userId,String tenantId) {
        //1、初始化租户管理员角色
        Role role = roleMapper.selectOne(Wrappers.<Role>lambdaQuery()
                .eq(Role::getCode, UpmsConstant.ROLE_TENANT_ADMIN).eq(Role::getTenantId,tenantId)
                .eq(Role::getIsDelete,UpmsConstant.NOT_DELETED));
        if (role==null){
            role = new Role();
            role.setCode(UpmsConstant.ROLE_TENANT_ADMIN);
            role.setName("租户管理员");
            role.setTenantId(tenantId);
            roleMapper.insert(role);
        }

        //2、绑定用户与角色关系
        UserRole userRole = baseMapper.selectOne(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getRoleId,role.getId())
                .eq(UserRole::getUserId,userId)
                .eq(UserRole::getTenantId,tenantId));
        if (userRole==null){
            userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            userRole.setTenantId(tenantId);
            baseMapper.insert(userRole);
        }

        //3、绑定角色与应用关系
        List<AppTenant> appTenants = appTenantMapper.selectList(Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,tenantId));
        if (appTenants!=null && !appTenants.isEmpty()){
            List<RoleApp> roleApps = new ArrayList<>();
            for (AppTenant appTenant:appTenants) {
                //已存在 执行continue
                int count = roleAppService.count(Wrappers.<RoleApp>lambdaQuery()
                        .eq(RoleApp::getRoleId,role.getId())
                        .eq(RoleApp::getAppId,appTenant.getAppId())
                        .eq(RoleApp::getTenantId,tenantId));
                if (count>0){
                    continue;
                }
                RoleApp roleApp = new RoleApp();
                roleApp.setRoleId(role.getId());
                roleApp.setTenantId(tenantId);
                roleApp.setAppId(appTenant.getAppId());
                roleApps.add(roleApp);
            }
            if(!roleApps.isEmpty()){
                roleAppService.saveBatch(roleApps);
            }

            //4、绑定角色与菜单关系
            List<Menu> menus = menuMapper.selectList(Wrappers.<Menu>lambdaQuery()
                    .in(Menu::getAppId,appTenants.stream().map(AppTenant::getAppId).collect(Collectors.toList()))
                    .eq(Menu::getLevel, LeveEnum.PUBLIC.toString())
                    .eq(Menu::getIsDelete, UpmsConstant.NOT_DELETED));
            if (menus!=null && !menus.isEmpty()){
                List<RoleMenu> roleMenus = new ArrayList<>();
                for (Menu menu:menus) {
                    //已存在 执行continue
                    int count = roleMenuService.count(Wrappers.<RoleMenu>lambdaQuery()
                            .eq(RoleMenu::getRoleId,role.getId())
                            .eq(RoleMenu::getAppId,menu.getAppId())
                            .eq(RoleMenu::getMenuId,menu.getId())
                            .eq(RoleMenu::getTenantId,tenantId));
                    if (count>0){
                        continue;
                    }
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setTenantId(tenantId);
                    roleMenu.setMenuId(menu.getId());
                    roleMenu.setAppId(menu.getAppId());
                    roleMenus.add(roleMenu);
                }

                if (!roleMenus.isEmpty()){ roleMenuService.saveBatch(roleMenus); }
            }
        }
    }

    @Override
    public List<Role> getRolesByUserId(String userId) {
        return baseMapper.selectRolesByUserId(userId);
    }
}
