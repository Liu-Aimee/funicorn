package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.entity.Role;
import com.funicorn.cloud.upms.center.entity.RoleApp;
import com.funicorn.cloud.upms.center.entity.RoleMenu;
import com.funicorn.cloud.upms.center.entity.UserRole;
import com.funicorn.cloud.upms.center.mapper.RoleAppMapper;
import com.funicorn.cloud.upms.center.mapper.RoleMapper;
import com.funicorn.cloud.upms.center.mapper.RoleMenuMapper;
import com.funicorn.cloud.upms.center.mapper.UserRoleMapper;
import com.funicorn.cloud.upms.center.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private RoleAppMapper roleAppMapper;

    @Override
    public void removeRole(String roleId) {
        //1、删除角色与应用关系表
        roleAppMapper.delete(Wrappers.<RoleApp>lambdaQuery().eq(RoleApp::getRoleId,roleId));
        //2、删除角色与菜单关系表
        roleMenuMapper.delete(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId,roleId));
        //3、删除角色与用户关系
        userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getRoleId,roleId));
        //4、删除角色
        Role role = new Role();
        role.setId(roleId);
        role.setIsDelete(UpmsConstant.IS_DELETED);
        baseMapper.updateById(role);
    }
}
