package com.funicorn.cloud.system.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.system.center.entity.RouteFilter;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aimee
 * @since 2022-03-09
 */
public interface RouteFilterService extends IService<RouteFilter> {

    /**
     * 修改启用状态
     * @param filterId 过滤器id
     * */
    void changeStatus(String filterId);
}
