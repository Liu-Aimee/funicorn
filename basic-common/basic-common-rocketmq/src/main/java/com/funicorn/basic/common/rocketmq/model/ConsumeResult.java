package com.funicorn.basic.common.rocketmq.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aimme
 * @since 2020/10/28 18:15
 */
@Data
public class ConsumeResult implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 是否处理成功
     */
    private boolean isSuccess;
    /**
     * 如果处理失败，是否允许消息队列继续调用，直到处理成功，默认true
     */
    private boolean isReconsumeLater = true;
    /**
     * 是否需要记录消费日志，默认不记录
     */
    private boolean isSaveConsumeLog = false;
    /**
     * 错误Code
     */
    private String errCode;
    /**
     * 错误消息
     */
    private String errMsg;
    /**
     * 错误堆栈
     */
    private Throwable e;
}
