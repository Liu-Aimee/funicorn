package com.funicorn.cloud.task.center.exception;

/**
 * @author Aimme
 * @since 2020/12/14 9:28
 * -1000~-1999
 */
public enum TaskErrorCode {

    /**
     * 上传文件无效
     * */
    UPLOAD_FILE_IS_INVALID(-1000,"上传文件无效"),

    /**
     * 不支持的模板类型
     * */
    NOT_SUPPORT_MODEL_SUFFIX(-1001,"不支持的模板类型"),

    /**
     * 文件流异常
     * */
    FILE_STREAM_ERROR(-1002,"文件流异常"),

    /**
     * 流程未定义
     * */
    PROCESS_NOT_FOUND(-1003,"流程未定义"),

    /**
     * 模型未定义
     * */
    MODEL_NOT_FOUND(-1004,"模型未定义"),

    /**
     * 任务不存在
     * */
    TASK_NOT_FOUND(-1005,"任务不存在"),

    /**
     * 流程定义已挂起
     * */
    PROCESS_DEFINITION_IS_SUSPENDED(-1006,"流程定义已挂起，请联系管理员"),

    /**
     * 流程定义模板校验不通过
     * */
    PROCESS_MODEL_INVALID(-1007,"模型校验不通过，原因:[%s]"),

    /**
     * 流程定义中未定义流程
     * */
    DEFINITION_NOT_FOUND_PROCESS(-1008,"流程定义中未定义流程"),

    /**
     * 流程实例不存在
     * */
    NOT_FOUND_PROCESS_INSTANCE(-1009,"流程实例不存在"),

    /**
     * 流程结束节点不存在
     * */
    NOT_FOUND_END_NODE(-1010,"流程结束节点不存在"),

    /**
     * 提交人节点不存在
     * */
    NOT_FOUND_START_NODE(-1011,"流程开始节点不存在"),

    /**
     * 禁止撤回流程
     * */
    NOT_ALLOW_REVOKE(-1012,"%s,禁止撤回流程"),

    /**
     * 流程定义不合法
     * */
    PROCESS_DEFINITION_IS_INVALID(-1013,"流程定义不合法"),

    ;

    private final int status;
    private final String message;

    TaskErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

}
