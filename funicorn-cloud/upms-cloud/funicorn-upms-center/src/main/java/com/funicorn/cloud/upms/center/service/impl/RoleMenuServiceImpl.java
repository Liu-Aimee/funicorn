package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.center.dto.RoleMenuDTO;
import com.funicorn.cloud.upms.center.entity.RoleApp;
import com.funicorn.cloud.upms.center.entity.RoleMenu;
import com.funicorn.cloud.upms.center.mapper.RoleMenuMapper;
import com.funicorn.cloud.upms.center.service.RoleAppService;
import com.funicorn.cloud.upms.center.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色与菜单关系 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Resource
    private RoleAppService roleAppService;

    @Override
    public void bind(RoleMenuDTO roleMenuDTO) {
        CurrentUser loginUser = SecurityUtil.getCurrentUser();
        //1、删除角色原有的菜单
        baseMapper.delete(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId,roleMenuDTO.getRoleId()).eq(RoleMenu::getTenantId,loginUser.getTenantId()));
        //2、删除角色原有的应用
        roleAppService.remove(Wrappers.<RoleApp>lambdaQuery()
                .eq(RoleApp::getRoleId,roleMenuDTO.getRoleId())
                .eq(RoleApp::getTenantId,loginUser.getTenantId()));
        //新增
        List<RoleApp> roleApps = new ArrayList<>();
        List<RoleMenu> roleMenus = new ArrayList<>();
        roleMenuDTO.getAppMenus().forEach(appMenu -> {
            String appId = appMenu.getAppId();
            RoleApp roleApp = new RoleApp();
            roleApp.setAppId(appId);
            roleApp.setRoleId(roleMenuDTO.getRoleId());
            roleApp.setTenantId(loginUser.getTenantId());
            roleApps.add(roleApp);
            appMenu.getMenuIds().forEach(id->{
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(id);
                roleMenu.setRoleId(roleMenuDTO.getRoleId());
                roleMenu.setTenantId(loginUser.getTenantId());
                roleMenu.setAppId(appId);
                roleMenus.add(roleMenu);
            });
        });
        //3、角色绑定应用
        if (!roleApps.isEmpty()){
            roleAppService.saveBatch(roleApps);
        }
        //4、角色绑定菜单
        if (!roleMenus.isEmpty()){
            super.saveBatch(roleMenus);
        }
    }
}
