package com.funicorn.cloud.system.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.system.center.entity.RoutePredicate;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aimee
 * @since 2022-03-09
 */
public interface RoutePredicateService extends IService<RoutePredicate> {

    /**
     * 修改启用状态
     * @param predicateId 断言id
     * */
    void changeStatus(String predicateId);
}
