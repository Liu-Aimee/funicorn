package com.funicorn.basic.cloud.security.endpoint;

import com.funicorn.basic.cloud.security.util.SecurityContext;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import com.funicorn.basic.common.base.util.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Aimme
 * @since 2020/6/20 18:26
 * 登录成功处理器
 */
@Slf4j
@Component
public class CustomizeAuthSuccessHandler implements AuthenticationSuccessHandler {
    RequestCache requestCache = new HttpSessionRequestCache();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private FunicornConfigProperties funicornConfigProperties;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String requestRedirectUrl;
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null && StringUtils.isNotEmpty(savedRequest.getRedirectUrl())) {
            requestRedirectUrl = savedRequest.getRedirectUrl();
            response.setStatus(HttpStatus.FOUND.value());
            response.setHeader("Location", requestRedirectUrl);
        }else {
            String redirectUrl = request.getParameter("redirect_uri");
            String clientId = request.getParameter("client_id");
            String scope = request.getParameter("scope");
            String state = request.getParameter("state");
            String responseType = request.getParameter("response_type");

            if(StringUtils.isEmpty(redirectUrl)) {
                response.setStatus(HttpServletResponse.SC_OK);
                Result<?> result = Result.error("redirect_uri不能为空");
                HttpUtil.writeResponse(response, result);
                return;
            }

            if(StringUtils.isEmpty(clientId)) {
                response.setStatus(HttpServletResponse.SC_OK);
                Result<?> result = Result.error("client_id不能为空");
                HttpUtil.writeResponse(response, result);
                return;
            }

            requestRedirectUrl = buildRedirectUrl(clientId,redirectUrl,state,scope,responseType);
            log.info(requestRedirectUrl);
            response.setStatus(HttpServletResponse.SC_OK);
            Result<?> result = Result.ok(requestRedirectUrl,"login success");
            HttpUtil.writeResponse(response, result);
        }
    }

    /**
     * 重定向url
     * 这里重定向到登陆时访问的那台服务器，可以重定向到服务网关
     * */
    protected String buildRedirectUrl(String clientId, String redirectUrl, String state, String scope,String responseType) {

        String codeKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(codeKey, SecurityContext.getCurrentUser(),60, TimeUnit.SECONDS);

        StringBuilder sb = new StringBuilder();
        sb.append(funicornConfigProperties.getSecurity().getServerAddr());
        sb.append("/oauth/authorize?client_id=").append(clientId)
                .append("&redirect_uri=").append(redirectUrl)
                .append("&response_type=").append(responseType);

        if(StringUtils.isNotEmpty(scope)) {
            sb.append("&scope=").append(scope);
        }

        if(StringUtils.isNotEmpty(state)) {
            sb.append("&state=").append(state);
        }

        sb.append("&code=").append(codeKey);
        return sb.toString();
    }
}
