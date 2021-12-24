package com.funicorn.basic.common.base.exception;

import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aimee
 * @since 2021/9/22 10:30
 */
@Slf4j
@ControllerAdvice
@Order
public class BaseExceptionHandler {

    /**
     * 基础异常
     * */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    Result<?> exception(Exception e) {
        log.error("Exception", e);
        return Result.error(BaseErrorCode.SYSTEM_ERROR.getStatus(),BaseErrorCode.SYSTEM_ERROR.getMessage());
    }

    /**
     * 自定义MethodArgumentNotValidException异常捕捉
     * */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    Result<?> methodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        return Result.error(BaseErrorCode.PARAM_IS_INVALID.getStatus(),String.format(BaseErrorCode.PARAM_IS_INVALID.getMessage(),e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    /**
     * 自定义MissingServletRequestParameterException异常捕捉
     * */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    Result<?> missingServletRequestParameterException(MissingServletRequestParameterException e){
        log.error("MissingServletRequestParameterException", e);
        return Result.error(BaseErrorCode.PARAM_IS_INVALID.getStatus(),String.format(BaseErrorCode.PARAM_IS_INVALID.getMessage(),e.getMessage()));
    }

    /**
     * 自定义MissingServletRequestParameterException异常捕捉
     * */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    Result<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("HttpRequestMethodNotSupportedException", e);
        return Result.error(BaseErrorCode.REQUEST_METHOD_NOT_SUPPORTED.getStatus(),String.format(BaseErrorCode.REQUEST_METHOD_NOT_SUPPORTED.getMessage(),e.getMessage()));
    }

    /**
     * HttpMediaTypeNotSupportedException
     * */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    Result<?> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        log.error("HttpRequestMethodNotSupportedException", e);
        return Result.error(BaseErrorCode.CONTENT_TYPE_NOT_SUPPORTED.getStatus(),String.format(BaseErrorCode.CONTENT_TYPE_NOT_SUPPORTED.getMessage(),e.getContentType()));
    }
}
