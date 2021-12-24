package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.center.dto.UserDTO;
import com.funicorn.cloud.upms.center.dto.UserPageDTO;
import com.funicorn.cloud.upms.center.entity.User;

/**
 * <p>
 * 用户信息管理 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface UserService extends IService<User> {

    /**
     * 用户列表分页查询
     * @param userPageDTO 分页入参
     * @return IPage
     * */
    IPage<UserInfo> getUserPage(UserPageDTO userPageDTO);

    /**
     * 查询用户信息
     * @param username 用户名
     * @return UserInfo
     * */
    UserInfo getUserInfo(String username);

    /**
     * 新建用户
     * @param userDTO 用户信息
     * @return User
     * */
    User saveUser(UserDTO userDTO);

    /**
     * 修改用户
     * @param userDTO 用户信息
     * */
    void updateUser(UserDTO userDTO);

    /**
     * 用户信息校验
     * @param userDTO 用户信息
     * */
    void validate(UserDTO userDTO);

}
