package com.funicorn.cloud.upms.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.upms.center.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 用户信息管理 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户
     * @param page 分页信息
     * @param params 查询条件
     * @return IPage
     * */
    IPage<User> selectUserPage(Page<User> page, @Param("user") Map<String, Object> params);
}
