package com.funicorn.cloud.system.center.service.impl;

import com.funicorn.cloud.system.center.entity.RoutePredicate;
import com.funicorn.cloud.system.center.mapper.RoutePredicateMapper;
import com.funicorn.cloud.system.center.service.RoutePredicateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RoutePredicateServiceImpl extends ServiceImpl<RoutePredicateMapper, RoutePredicate> implements RoutePredicateService {

}
