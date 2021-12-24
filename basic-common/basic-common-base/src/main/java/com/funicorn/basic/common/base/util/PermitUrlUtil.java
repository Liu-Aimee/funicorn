package com.funicorn.basic.common.base.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aimee
 * @since 2020/7/6 14:14
 */

public class PermitUrlUtil {

    /**
     * 静态文件白名单
     * */
    private static final List<String> ENDPOINTS = Lists.newArrayList("/favicon.ico", "/v2/api-docs/**","/v3/api-docs/**",
            "/doc/index.html","/doc.html", "/swagger-resources/**","/**/swagger-ui.html",
            "/swagger-ui/**", "/webjars/**", "/druid/**","/**/*.js","/**/*.css");

    /**
     * 所有的白名单 包括接口和静态文件
     * */
    private static final List<String> ALL_ENDPOINTS = new ArrayList<>();

    /**
     * 获取静态文件白名单
     * @return List 白名单数组
     * */
    public static List<String> permitStaticUrl(){
        return ENDPOINTS;
    }

    /**
     * 获取非静态文件白名单
     * @return List
     * */
    public static List<String> permitInterfaceUrl(){
        return ALL_ENDPOINTS.stream().filter(k->!ENDPOINTS.contains(k)).collect(Collectors.toList());
    }

    /**
     * 获取所有的白名单 包括接口和静态文件
     * @param urls 新增白名单数组
     * @return List 白名单数组
     * */
    public static List<String> permitAllUrl(String... urls) {
        if (ALL_ENDPOINTS.isEmpty()){
            ALL_ENDPOINTS.addAll(ENDPOINTS);
        }
        if (urls != null && urls.length != 0) {
            List<String> list = Arrays.asList(urls);
            ALL_ENDPOINTS.addAll(list);
        }
        return ALL_ENDPOINTS;
    }

    /**
     * 获取所有的白名单 包括接口和静态文件
     * @param list 新增白名单数组
     * @return List 白名单数组
     * */
    public static List<String> permitAllUrl(List<String> list) {
        if (ALL_ENDPOINTS.isEmpty()){
            ALL_ENDPOINTS.addAll(ENDPOINTS);
        }
        if (list!=null && !list.isEmpty()){
            ALL_ENDPOINTS.addAll(list);
        }

        return ALL_ENDPOINTS;
    }
}
