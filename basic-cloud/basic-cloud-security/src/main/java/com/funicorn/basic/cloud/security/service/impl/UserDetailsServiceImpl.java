package com.funicorn.basic.cloud.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.cloud.security.entity.Menu;
import com.funicorn.basic.cloud.security.entity.Role;
import com.funicorn.basic.cloud.security.entity.User;
import com.funicorn.basic.cloud.security.entity.UserTenant;
import com.funicorn.basic.cloud.security.mapper.MenuMapper;
import com.funicorn.basic.cloud.security.mapper.RoleMapper;
import com.funicorn.basic.cloud.security.mapper.UserMapper;
import com.funicorn.basic.cloud.security.mapper.UserTenantMapper;
import com.funicorn.basic.cloud.security.model.CurrentUser;
import com.funicorn.basic.cloud.security.model.RoleInfo;
import com.funicorn.basic.cloud.security.vo.UserVO;
import com.funicorn.basic.common.base.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/10/29 11:45
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserTenantMapper userTenantMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String loginName;
        String[] loginInfo = username.split(":");
        if (loginInfo.length<=0 || StringUtils.isBlank(loginInfo[0])){
            throw new InternalAuthenticationServiceException("用户不存在");
        }else {
            loginName = loginInfo[0];
            int count = userMapper.selectCount(Wrappers.<User>lambdaQuery().eq(User::getUsername,loginName));
            if (count<=0){
                throw new InternalAuthenticationServiceException("用户不存在");
            }
        }

        String tenantId;
        if (loginInfo.length< 2 ){
            //如果用户没有选择登录租户，默认选择数据库查询出来的第一个
            List<UserTenant> userTenants = userTenantMapper.selectList(Wrappers.<UserTenant>lambdaQuery()
                    .eq(UserTenant::getUsername,loginInfo[0]).orderByAsc(UserTenant::getCreatedTime));
            if (userTenants!=null && !userTenants.isEmpty()){
                tenantId = userTenants.get(0).getTenantId();
            } else {
                throw new InternalAuthenticationServiceException("未绑定租户");
            }
        }else {
            tenantId = loginInfo[1];
        }

        UserVO userVO = userMapper.selectUserById(loginName,tenantId);
        if (userVO==null){
            throw new InternalAuthenticationServiceException("用户未绑定此租户或此租户不存在[" + tenantId + "]");
        }

        CurrentUser currentUser =  JsonUtil.object2Object(userVO, CurrentUser.class);

        //加载用户角色信息
        List<Role> roleList = roleMapper.selectRoleByUser(currentUser.getId(), currentUser.getTenantId());
        if (roleList!=null && !roleList.isEmpty()){

            //用户权限列表
            LinkedList<String> permissions = new LinkedList<>();

            //用户角色列表
            List<RoleInfo> roles = new ArrayList<>();
            currentUser.setRoles(roles);

            roleList.forEach(role -> {
                //组装角色
                RoleInfo roleInfo = JsonUtil.object2Object(role,RoleInfo.class);
                roles.add(roleInfo);

                //组装权限
                List<Menu> menuList = menuMapper.selectMenuByUser(role.getId(),currentUser.getTenantId());
                if (menuList!=null && !menuList.isEmpty()){
                    menuList.forEach(menu -> {
                        if (StringUtils.isNotBlank(menu.getPermission())) {
                            permissions.add(menu.getPermission());
                        }});
                }
            });

            if (!permissions.isEmpty()){
                currentUser.setPermissions(permissions);
            }
        }

        return currentUser;
    }
}
