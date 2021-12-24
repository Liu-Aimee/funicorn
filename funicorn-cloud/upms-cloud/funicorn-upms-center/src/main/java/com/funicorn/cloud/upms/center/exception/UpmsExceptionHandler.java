package com.funicorn.cloud.upms.center.exception;

import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aimme
 * @since 2020/12/14 9:31
 */
@ControllerAdvice
@Slf4j
public class UpmsExceptionHandler {

    /**
     * 自定义异常
     * */
    @ExceptionHandler({UpmsException.class})
    @ResponseBody
    Result<?> accessDeniedException(UpmsException e) {
        log.error("UpmsException", e);
        return Result.error(e.getCode(),e.getMsg());
    }
}
