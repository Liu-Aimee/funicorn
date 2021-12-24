package com.funicorn.basic.cloud.security.service.impl;

import com.funicorn.basic.cloud.security.entity.Role;
import com.funicorn.basic.cloud.security.mapper.RoleMapper;
import com.funicorn.basic.cloud.security.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
