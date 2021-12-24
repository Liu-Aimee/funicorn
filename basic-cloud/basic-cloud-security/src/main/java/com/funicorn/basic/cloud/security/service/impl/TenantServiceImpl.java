package com.funicorn.basic.cloud.security.service.impl;

import com.funicorn.basic.cloud.security.entity.Tenant;
import com.funicorn.basic.cloud.security.mapper.TenantMapper;
import com.funicorn.basic.cloud.security.service.TenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 租户信息表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

}
