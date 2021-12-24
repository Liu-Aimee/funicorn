package com.funicorn.cloud.task.center.exception;

import com.funicorn.basic.common.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Aimme
 * @date 2020/12/14 9:31
 */
@ControllerAdvice
@Slf4j
public class TaskExceptionHandler {

    /**
     * 自定义工作流异常
     * */
    @ExceptionHandler({TaskException.class})
    @ResponseBody
    Result<?> taskException(TaskException e) {
        log.error("taskException", e);
        return Result.error(e.getCode(),e.getMsg());
    }
}
