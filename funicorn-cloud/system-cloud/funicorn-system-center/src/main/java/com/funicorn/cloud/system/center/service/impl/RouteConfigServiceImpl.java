package com.funicorn.cloud.system.center.service.impl;

import com.funicorn.cloud.system.center.entity.RouteConfig;
import com.funicorn.cloud.system.center.mapper.RouteConfigMapper;
import com.funicorn.cloud.system.center.service.RouteConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 路由配置信息表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RouteConfigServiceImpl extends ServiceImpl<RouteConfigMapper, RouteConfig> implements RouteConfigService {

}
