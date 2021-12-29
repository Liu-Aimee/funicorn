package com.funicorn.cloud.upms.api.service.fallback;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.api.service.UpmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Aimee
 * @since 2021/12/27 17:19
 */
@Component
@Slf4j
public class UpmsServiceFallback implements FallbackFactory<UpmsService> {
    @Override
    public UpmsService create(Throwable cause) {
        return new UpmsService() {
            @Override
            public Result<UserInfo> getUserInfo(String username) {
                log.error("查询用户信息信息失败",cause);
                return Result.error("查询用户信息信息失败");
            }
        };
    }
}
