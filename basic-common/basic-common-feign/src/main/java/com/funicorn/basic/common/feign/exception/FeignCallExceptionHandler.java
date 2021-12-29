package com.funicorn.basic.common.feign.exception;

import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aimee
 * @since 2021/12/29 9:46
 */
@Slf4j
@ControllerAdvice
@Order(999)
public class FeignCallExceptionHandler {

    /**
     * FeignCallException异常
     * */
    @ExceptionHandler({FeignCallException.class})
    @ResponseBody
    Result<?> feignCallException(FeignCallException e) {
        log.error("FeignCallException", e);
        return Result.error(e.getCode(),e.getMsg());
    }
}
