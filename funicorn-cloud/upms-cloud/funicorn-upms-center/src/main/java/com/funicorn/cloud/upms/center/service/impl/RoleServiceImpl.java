package com.funicorn.cloud.upms.center.service.impl;

import com.funicorn.cloud.upms.center.entity.Role;
import com.funicorn.cloud.upms.center.mapper.RoleMapper;
import com.funicorn.cloud.upms.center.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
