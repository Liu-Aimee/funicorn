package com.funicorn.basic.common.security.exception;

import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aimee
 * @since 2021/9/22 10:30
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    /**
     * 权限异常
     * @param e 异常
     * @return Result
     * */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    Result<?> accessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException", e);
        return Result.error(BaseErrorCode.NO_PERMISSION.getStatus(), BaseErrorCode.NO_PERMISSION.getMessage());
    }
}
