package com.funicorn.basic.cloud.security.service.impl;

import com.funicorn.basic.cloud.security.entity.User;
import com.funicorn.basic.cloud.security.mapper.UserMapper;
import com.funicorn.basic.cloud.security.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
