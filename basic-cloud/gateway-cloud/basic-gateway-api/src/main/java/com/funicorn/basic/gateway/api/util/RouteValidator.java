package com.funicorn.basic.gateway.api.util;

import com.funicorn.basic.gateway.api.constant.RouteConstant;
import com.funicorn.basic.gateway.api.enums.PredicateType;
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
        PredicateType.valueOf(predicate.getType()).validate(predicate);
    }
}