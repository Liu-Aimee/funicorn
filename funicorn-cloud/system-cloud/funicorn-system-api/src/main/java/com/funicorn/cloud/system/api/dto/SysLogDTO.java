package com.funicorn.cloud.system.api.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2021/10/14 8:38
 */
@Data
public class SysLogDTO {

    /**
     * 主键id
     * */
    private String id;
    /**
     * 中文描述
     */
    private String content;

    /**
     * 操作类型  update/delete/insert/query
     */
    private String operationType;

    /**
     * 日志类型 操作日志/operation 错误日志/error
     * */
    private String logType;

    /**
     * 错误描述
     * */
    private String message;

    /**
     * 操作用户账号
     */
    private String userId;

    /**
     * 操作用户账号
     */
    private String username;

    /**
     * 客户端标识
     */
    private String serviceName;

    /**
     * 客户端ip地址
     */
    private String remoteAddr;

    /**
     * 后台函数路径
     */
    private String method;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型 post/get/patch/delete
     */
    private String requestType;

    /**
     * 耗时 单位ms
     */
    private Long costTime;
}
