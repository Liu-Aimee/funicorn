package com.funicorn.basic.cloud.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.basic.cloud.security.entity.User;
import com.funicorn.basic.cloud.security.vo.UserVO;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户信息
     * @param username 用户名
     * @param tenantId 租户id
     * @return UserPO
     * */
    UserVO selectUserById(String username, String tenantId);
}
