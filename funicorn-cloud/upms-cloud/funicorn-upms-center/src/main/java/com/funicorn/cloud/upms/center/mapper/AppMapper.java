package com.funicorn.cloud.upms.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.upms.center.dto.AppPageDTO;
import com.funicorn.cloud.upms.center.entity.App;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 应用管理 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface AppMapper extends BaseMapper<App> {

    /**
     * 查询未绑定过的应用
     * @param tenantId 租户id
     * @return List<OauthClient>
     * */
    List<App> selectUnbindList(String tenantId);

    /**
     * 分页查询当前用户可操作的应用
     * @param appPageDTO 入参
     * @param page 分页条件
     * @return App
     * */
    IPage<App> queryPage(Page<App> page, @Param("model") AppPageDTO appPageDTO);
}
