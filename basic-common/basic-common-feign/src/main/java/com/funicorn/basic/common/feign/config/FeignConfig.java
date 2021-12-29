package com.funicorn.basic.common.feign.config;

import com.funicorn.basic.common.base.constant.BaseConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aimee
 * @since 2021/4/22 10:48
 */
@Configuration
@EnableFeignClients("com.funicorn.*")
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(BaseConstant.HEADER_WHERE_CALL_KEY, BaseConstant.INNER_CALL);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes!=null) {
            HttpServletRequest request = attributes.getRequest();
            //微服务之间调用时需要添加token，才能鉴权通过
            if (!StringUtils.isEmpty(request.getHeader(BaseConstant.ACCESS_TOKEN))){
                template.header(BaseConstant.ACCESS_TOKEN, request.getHeader(BaseConstant.ACCESS_TOKEN));
            }
        }
    }
}
