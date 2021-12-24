package com.funicorn.basic.common.base.model;

import lombok.Data;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * http请求类
 * @author Aimee
 * @since 2021/4/9 15:03
 */
@Data
@SuppressWarnings("unused")
public class HttpReqData {

    /**
     * 请求地址 ip:port
     * */
    private String url;

    /**
     * 自定义请求头
     * */
    private Map<String,String> headerMap;

    /**
     * 请求方法
     * */
    private String requestMethod;

    /**
     * 自定义请求参数
     * */
    private Map<String,Object> param;

    /**
     * form表单请求参数
     * */
    private MultiValueMap<String,Object> formParam;

    /**
     * 连接超时时间
     * */
    private Integer connectTimeout;

    /**
     * 读取超时时间
     * */
    private Integer readTimeout;
}
