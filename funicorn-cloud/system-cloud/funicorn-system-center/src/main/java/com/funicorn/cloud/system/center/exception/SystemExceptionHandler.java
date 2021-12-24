package com.funicorn.cloud.system.center.exception;

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
public class SystemExceptionHandler {

    /**
     * 自定义SystemException异常捕捉
     * */
    @ExceptionHandler({SystemException.class})
    @ResponseBody
    Result<?> systemException(SystemException e) {
        log.error("systemException", e);
        return Result.error(e.getCode(),e.getMsg());
    }
}
