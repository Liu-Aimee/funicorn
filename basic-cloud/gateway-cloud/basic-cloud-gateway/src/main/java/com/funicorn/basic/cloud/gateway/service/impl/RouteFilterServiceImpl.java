package com.funicorn.basic.cloud.gateway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.cloud.gateway.entity.RouteFilter;
import com.funicorn.basic.cloud.gateway.mapper.RouteFilterMapper;
import com.funicorn.basic.cloud.gateway.service.RouteFilterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2022-03-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RouteFilterServiceImpl extends ServiceImpl<RouteFilterMapper, RouteFilter> implements RouteFilterService {

}
