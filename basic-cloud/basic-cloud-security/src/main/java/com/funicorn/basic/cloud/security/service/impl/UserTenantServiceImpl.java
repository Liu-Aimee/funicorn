package com.funicorn.basic.cloud.security.service.impl;

import com.funicorn.basic.cloud.security.entity.UserTenant;
import com.funicorn.basic.cloud.security.mapper.UserTenantMapper;
import com.funicorn.basic.cloud.security.service.UserTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户与租户关联表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserTenantServiceImpl extends ServiceImpl<UserTenantMapper, UserTenant> implements UserTenantService {

}
