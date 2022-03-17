package com.funicorn.basic.gateway.api.util;

import com.funicorn.basic.gateway.api.constant.RouteConstant;
import com.funicorn.basic.gateway.api.enums.FilterType;
import com.funicorn.basic.gateway.api.enums.PredicateType;
import com.funicorn.basic.gateway.api.model.Filter;
import com.funicorn.basic.gateway.api.model.Predicate;
import org.springframework.util.Assert;

/**
 * 路由参数校验
 * @author Aimee
 * @since 2022/3/16 10:05
 */
public class RouteValidator {

    /**
     * 参数校验
     * @param predicate predicate
     * */
    public static void validatePredicate(Predicate predicate) {
        Assert.isTrue(RouteConstant.PREDICATE_SUPPORT_TYPE.contains(predicate.getType()), "不支持的断言类型[" + predicate.getType() + "]");
        if (PredicateType.hasType(predicate.getType())) {
            PredicateType.valueOf(predicate.getType()).validate(predicate);
        }
    }

    /**
     * 参数校验
     * @param filter predicate
     * */
    public static void validateFilter(Filter filter) {
        Assert.isTrue(RouteConstant.PREDICATE_SUPPORT_TYPE.contains(filter.getType()), "不支持的过滤器类型[" + filter.getType() + "]");
        if (FilterType.hasType(filter.getType())) {
            FilterType.valueOf(filter.getType()).validate(filter);
        }
    }
}