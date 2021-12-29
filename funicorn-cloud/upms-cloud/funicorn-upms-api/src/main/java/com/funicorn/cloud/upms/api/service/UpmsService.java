package com.funicorn.cloud.upms.api.service;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.api.service.fallback.UpmsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Aimee
 * @since 2021/12/27 17:19
 */

@FeignClient(name = "funicorn-upms-center", fallbackFactory = UpmsServiceFallback.class)
public interface UpmsService {

    /**
     * 查询用户信息
     * @param username 用户名 默认当前登录用户名
     * @return Result
     * */
    @GetMapping("User/getUserInfo")
    Result<UserInfo> getUserInfo(@RequestParam(required = false) String username);
}
