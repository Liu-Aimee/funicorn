package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.UserTenantDTO;
import com.funicorn.cloud.upms.center.entity.Tenant;
import com.funicorn.cloud.upms.center.entity.User;
import com.funicorn.cloud.upms.center.entity.UserTenant;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.mapper.TenantMapper;
import com.funicorn.cloud.upms.center.mapper.UserMapper;
import com.funicorn.cloud.upms.center.mapper.UserTenantMapper;
import com.funicorn.cloud.upms.center.service.UserTenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户与租户关系 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserTenantServiceImpl extends ServiceImpl<UserTenantMapper, UserTenant> implements UserTenantService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private TenantMapper tenantMapper;

    @Override
    public void bind(UserTenantDTO userTenantDTO) {

        User user = userMapper.selectById(userTenantDTO.getUserId());
        if (user==null){
            throw new UpmsException(ErrorCode.USER_NOT_EXISTS);
        }

        Tenant tenant = tenantMapper.selectById(userTenantDTO.getTenantId());
        if (tenant==null){
            throw new UpmsException(ErrorCode.TENANT_NOT_EXISTS);
        }

        UserTenant userTenant = new UserTenant();
        userTenant.setUserId(user.getId());
        userTenant.setType(UpmsConstant.TENANT_USER_SUPER.equals(SecurityUtil.getCurrentUser().getType()) ? UpmsConstant.TENANT_USER_ADMIN : UpmsConstant.TENANT_USER_NORMAL);
        userTenant.setUsername(user.getUsername());
        userTenant.setTenantId(tenant.getId());
        userTenant.setTenantName(tenant.getTenantName());
        baseMapper.insert(userTenant);
    }

    @Override
    public void unbind(UserTenantDTO userTenantDTO) {
        baseMapper.delete(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getTenantId,userTenantDTO.getTenantId())
                .eq(UserTenant::getUserId,userTenantDTO.getUserId()));
    }
}
