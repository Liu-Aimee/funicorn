package com.funicorn.cloud.chart.center.exception;

import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aimme
 */
@ControllerAdvice
@Slf4j
public class DatavExceptionHandler {

    /**
     * 业务异常
     * */
    @ExceptionHandler({DatavException.class})
    @ResponseBody
    Result<?> dataViewException(DatavException e) {
        log.error("DatavException", e);
        return Result.error(e.getCode(),e.getMsg());
    }
}
