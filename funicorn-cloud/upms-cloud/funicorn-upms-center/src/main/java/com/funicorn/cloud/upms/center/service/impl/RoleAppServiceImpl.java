package com.funicorn.cloud.upms.center.service.impl;

import com.funicorn.cloud.upms.center.entity.RoleApp;
import com.funicorn.cloud.upms.center.mapper.RoleAppMapper;
import com.funicorn.cloud.upms.center.service.RoleAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色与应用关系 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleAppServiceImpl extends ServiceImpl<RoleAppMapper, RoleApp> implements RoleAppService {

}
