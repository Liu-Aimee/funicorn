package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.api.model.RoleInfo;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.api.model.UserTenInfo;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.UserDTO;
import com.funicorn.cloud.upms.center.dto.UserPageDTO;
import com.funicorn.cloud.upms.center.entity.*;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.mapper.*;
import com.funicorn.cloud.upms.center.service.UserRoleService;
import com.funicorn.cloud.upms.center.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private UserTenantMapper userTenantMapper;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserOrgMapper userOrgMapper;

    @SuppressWarnings("unchecked")
    @Override
    public IPage<UserInfo> getUserPage(UserPageDTO userPageDTO) {
        Map<String, Object> params = (Map<String, Object>) JsonUtil.object2Map(userPageDTO);
        params.put("type",UpmsConstant.TENANT_USER_SUPER.equals(SecurityUtil.getCurrentUser().getType()) ? UpmsConstant.TENANT_USER_ADMIN : UpmsConstant.TENANT_USER_NORMAL);
        IPage<User> userPage = baseMapper.selectUserPage(new Page<>(userPageDTO.getCurrent(),userPageDTO.getSize()),params);
        IPage<UserInfo> userInfoPage = ConvertUtil.page2Page(userPage, UserInfo.class);

        userInfoPage.getRecords().forEach(userInfo -> {
            //角色信息
            List<Role> roleList = userRoleService.getRolesByUserId(userInfo.getId());
            if (roleList!=null && !roleList.isEmpty()){
                userInfo.setRoleInfos(ConvertUtil.list2List(roleList,RoleInfo.class));
            }
            //组织机构信息
            UserOrg userOrg = userOrgMapper.selectOne(Wrappers.<UserOrg>lambdaQuery().eq(UserOrg::getUserId,userInfo.getId()));
            if (userOrg!=null){
                userInfo.setOrgId(userOrg.getOrgId());
                userInfo.setOrgName(userOrg.getOrgName());
            }
            //租户信息
            List<UserTenant> userTenants = userTenantMapper.selectList(Wrappers.<UserTenant>lambdaQuery().eq(UserTenant::getUserId,userInfo.getId()));
            if (userTenants!=null && !userTenants.isEmpty()){
                List<UserTenInfo> userTenInfoList = new ArrayList<>();
                userTenants.forEach(userTenant -> {
                    UserTenInfo userTenInfo = JsonUtil.object2Object(userTenant,UserTenInfo.class);
                    userTenInfoList.add(userTenInfo);
                });
                userInfo.setUserTenInfos(userTenInfoList);
            }
        });
        return userInfoPage;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        if (StringUtils.isBlank(username)){
            username = SecurityUtil.getCurrentUser().getUsername();
        }
        User user = baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,username));
        if (user==null){
            throw new UpmsException(ErrorCode.USER_NOT_EXISTS);
        }
        UserInfo userInfo = JsonUtil.object2Object(user,UserInfo.class);
        //角色信息
        List<Role> roleList = userRoleService.getRolesByUserId(userInfo.getId());
        if (roleList!=null && !roleList.isEmpty()){
            userInfo.setRoleInfos(ConvertUtil.list2List(roleList,RoleInfo.class));
        }
        //组织机构信息
        UserOrg userOrg = userOrgMapper.selectOne(Wrappers.<UserOrg>lambdaQuery().eq(UserOrg::getUserId,userInfo.getId()));
        if (userOrg!=null){
            userInfo.setOrgId(userOrg.getOrgId());
            userInfo.setOrgName(userOrg.getOrgName());
        }
        //租户信息
        List<UserTenant> userTenants = userTenantMapper.selectList(Wrappers.<UserTenant>lambdaQuery().eq(UserTenant::getUserId,userInfo.getId()));
        if (userTenants!=null && !userTenants.isEmpty()){
            List<UserTenInfo> userTenInfoList = new ArrayList<>();
            userTenants.forEach(userTenant -> {
                UserTenInfo userTenInfo = JsonUtil.object2Object(userTenant,UserTenInfo.class);
                userTenInfoList.add(userTenInfo);
            });
            userInfo.setUserTenInfos(userTenInfoList);
        }
        return userInfo;
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        User user = JsonUtil.object2Object(userDTO,User.class);
        if (StringUtils.isBlank(userDTO.getPassword())) {
            userDTO.setPassword(UpmsConstant.USER_INIT_PASSWORD);
        }
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(),BCrypt.gensalt()));
        //密码过期时间为当前时间的3个月后
        user.setExpireTime(LocalDateTime.now().plusMonths(3));
        baseMapper.insert(user);

        //保存用户与租户关系
        if (userDTO.getTenantIds()!=null && !userDTO.getTenantIds().isEmpty()){
            userDTO.getTenantIds().forEach(tenantId->{
                Tenant tenant = tenantMapper.selectById(tenantId);
                if (tenant==null){
                    throw new UpmsException(ErrorCode.TENANT_NOT_EXISTS,tenantId);
                }
                UserTenant userTenant = new UserTenant();
                userTenant.setTenantName(tenant.getTenantName());
                userTenant.setTenantId(tenant.getId());
                userTenant.setUserId(user.getId());
                userTenant.setUsername(user.getUsername());
                userTenant.setType(UpmsConstant.TENANT_USER_SUPER.equals(currentUser.getType()) ? UpmsConstant.TENANT_USER_ADMIN : UpmsConstant.TENANT_USER_NORMAL);
                userTenantMapper.insert(userTenant);

                //租户管理员需要设置管理员角色
                if (UpmsConstant.TENANT_USER_SUPER.equals(currentUser.getType())){
                    userRoleService.initRoleTenantAdmin(user.getId(),tenant.getId());
                }
            });
        }
        return user;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        User user = JsonUtil.object2Object(userDTO,User.class);
        //不能修改密码
        user.setPassword(null);
        baseMapper.updateById(user);

        //用户与租户信息
        if (userDTO.getTenantIds()!=null && !userDTO.getTenantIds().isEmpty()){

            //删除用户取消绑定的租户
            //删除用户租户信息
            userTenantMapper.delete(Wrappers.<UserTenant>lambdaQuery()
                    .eq(UserTenant::getUserId,userDTO.getId())
                    .notIn(UserTenant::getTenantId,userDTO.getTenantIds()));
            //删除用户角色信息
            userRoleService.remove(Wrappers.<UserRole>lambdaQuery()
                    .eq(UserRole::getUserId,userDTO.getId())
                    .notIn(UserRole::getTenantId,userDTO.getTenantIds()));

            //重新绑定新的租户
            userDTO.getTenantIds().forEach(tenantId->{
                UserTenant oldUserTenant = userTenantMapper.selectOne(Wrappers.<UserTenant>lambdaQuery()
                        .eq(UserTenant::getUserId,userDTO.getId())
                        .eq(UserTenant::getTenantId,tenantId));

                if (oldUserTenant==null){
                    //保存用户与租户关系
                    Tenant tenant = tenantMapper.selectById(tenantId);
                    if (tenant==null){
                        throw new UpmsException(ErrorCode.TENANT_NOT_EXISTS,tenantId);
                    }
                    UserTenant userTenant = new UserTenant();
                    userTenant.setTenantName(tenant.getTenantName());
                    userTenant.setTenantId(tenant.getId());
                    userTenant.setUserId(user.getId());
                    userTenant.setUsername(user.getUsername());
                    userTenant.setType(UpmsConstant.TENANT_USER_SUPER.equals(currentUser.getType()) ? UpmsConstant.TENANT_USER_ADMIN : UpmsConstant.TENANT_USER_NORMAL);
                    userTenantMapper.insert(userTenant);

                    //租户管理员需要设置管理员角色
                    if (UpmsConstant.TENANT_USER_SUPER.equals(currentUser.getType())){
                        userRoleService.initRoleTenantAdmin(user.getId(),tenant.getId());
                    }
                }
            });
        }
    }

    @Override
    public void validate(UserDTO user) {
        User vUser;
        //用户名
        if (StringUtils.isNotBlank(user.getUsername())){
            LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(User::getUsername,user.getUsername());
            if (StringUtils.isNotBlank(user.getId())){
                queryWrapper1.ne(User::getId,user.getId());
            }
            vUser = baseMapper.selectOne(queryWrapper1);
            if (vUser!=null){
                throw new UpmsException(ErrorCode.USER_IS_EXISTS,user.getUsername());
            }
        }

        //邮箱
        if (StringUtils.isNotBlank(user.getEmail())){
            LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(User::getEmail,user.getEmail());
            if (StringUtils.isNotBlank(user.getId())){
                queryWrapper2.ne(User::getId,user.getId());
            }
            vUser = baseMapper.selectOne(queryWrapper2);
            if (vUser!=null){
                throw new UpmsException(ErrorCode.EMAIL_IS_EXISTS,user.getEmail());
            }
        }

        //手机号
        if (StringUtils.isNotBlank(user.getMobile())){
            LambdaQueryWrapper<User> queryWrapper3 = new LambdaQueryWrapper<>();
            queryWrapper3.eq(User::getMobile,user.getMobile());
            if (StringUtils.isNotBlank(user.getId())){
                queryWrapper3.ne(User::getId,user.getId());
            }
            vUser = baseMapper.selectOne(queryWrapper3);
            if (vUser!=null){
                throw new UpmsException(ErrorCode.PHONE_IS_EXISTS,user.getMobile());
            }
        }

        //身份证
        if (StringUtils.isNotBlank(user.getIdCard())){
            LambdaQueryWrapper<User> queryWrapper4 = new LambdaQueryWrapper<>();
            queryWrapper4.eq(User::getIdCard,user.getIdCard());
            if (StringUtils.isNotBlank(user.getId())){
                queryWrapper4.ne(User::getId,user.getId());
            }
            vUser = baseMapper.selectOne(queryWrapper4);
            if (vUser!=null){
                throw new UpmsException(ErrorCode.CARD_IS_EXISTS,user.getIdCard());
            }
        }
    }
}
