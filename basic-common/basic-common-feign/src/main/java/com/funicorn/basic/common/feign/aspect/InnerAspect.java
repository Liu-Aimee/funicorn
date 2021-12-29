package com.funicorn.basic.common.feign.aspect;

import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.feign.annotation.Inner;
import com.funicorn.basic.common.feign.exception.FeignCallException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aimee
 * @since 2021/12/27 14:39
 */

@Slf4j
@Aspect
@AllArgsConstructor
public class InnerAspect {

    private final HttpServletRequest request;

    /**
     * feign接口拦截器
     * @param point point
     * @param inner inner
     * @return Object
     * */
    @SneakyThrows
    @Around("@annotation(inner)")
    public Object around(ProceedingJoinPoint point, Inner inner) {
        String header = request.getHeader(BaseConstant.HEADER_WHERE_CALL_KEY);
        if (inner.value()) {
            if (StringUtils.isBlank(header) || !BaseConstant.INNER_CALL.equals(header)) {
                throw new FeignCallException(BaseErrorCode.NO_PERMISSION);
            }
        }else {
            if (StringUtils.isBlank(header) || !BaseConstant.INNER_CALL.equals(header)) {
                String token = request.getHeader(BaseConstant.ACCESS_TOKEN);
                if (StringUtils.isBlank(token)) {
                    throw new FeignCallException(BaseErrorCode.NO_PERMISSION);
                }
            }
        }
        return point.proceed();
    }
}
