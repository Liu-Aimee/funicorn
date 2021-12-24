package com.funicorn.basic.cloud.gateway.exception;

import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常捕捉，只作用于controller层异常
 * @author Aimme
 * @since 2020/12/14 9:31
 */
@ControllerAdvice
@Slf4j
public class GatewayExceptionHandler {

    /**
     * 自定义GatewayException异常捕捉
     * */
    @ExceptionHandler({GatewayException.class})
    @ResponseBody
    Result<?> feedException(GatewayException e) {
        log.error("GatewayException", e);
        return Result.error(e.getCode(),e.getMsg());
    }
}
