package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.center.dto.AppPageDTO;
import com.funicorn.cloud.upms.center.entity.App;
import com.funicorn.cloud.upms.center.vo.AppVO;

import java.util.List;

/**
 * <p>
 * 应用管理 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface AppService extends IService<App> {

    /**
     * 查询未绑定过的应用
     * @param tenantId 租户id
     * @return List<OauthClient>
     * */
    List<App> getUnbindList(String tenantId);

    /**
     * 分页查询当前用户可操作的应用
     * @param appPageDTO 入参
     * @return App
     * */
    IPage<App> visitPage(AppPageDTO appPageDTO);

    /**
     * 分页查询当前用户可操作的应用
     * @param appPageDTO 入参
     * @return App
     * */
    IPage<AppVO> appPage(AppPageDTO appPageDTO);
}
