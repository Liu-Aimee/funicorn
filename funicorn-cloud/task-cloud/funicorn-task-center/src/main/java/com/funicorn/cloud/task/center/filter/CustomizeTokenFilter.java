package com.funicorn.cloud.task.center.filter;

import com.alibaba.fastjson.JSONObject;
import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.security.endpoint.CustomizeOpaqueTokenIntrospector;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * TODO 暂时不知道为什么不自己走security配置，只能自己写过滤器做token校验
 * @author Aimee
 * @since 2021/12/13 9:51
 */
@Component
@Slf4j
@Order(0)
public class CustomizeTokenFilter extends OncePerRequestFilter {

    @Resource
    private CustomizeOpaqueTokenIntrospector customizeOpaqueTokenIntrospector;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,  @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            checkToken(request);
        } catch (Exception e) {
            Result<?> result = Result.error(BaseErrorCode.INVALID_TOKEN.getStatus(),BaseErrorCode.INVALID_TOKEN.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().write(JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8));
            log.error("token解析失败",e);
            return;
        }
        filterChain.doFilter(request,response);
    }

    @SneakyThrows
    private void checkToken(HttpServletRequest httpServletRequest){
        String jsonToken = httpServletRequest.getHeader(BaseConstant.ACCESS_TOKEN);
        if (StringUtils.isBlank(jsonToken)){
            throw new InvalidTokenException("Invalid access_token");
        }
        String token = jsonToken.replace("Bearer ", "").replace("bearer ", "");
        OAuth2AuthenticatedPrincipal principal = customizeOpaqueTokenIntrospector.introspect(token);
        Instant iat = principal.getAttribute(OAuth2IntrospectionClaimNames.ISSUED_AT);
        Instant exp = principal.getAttribute(OAuth2IntrospectionClaimNames.EXPIRES_AT);
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, token, iat, exp);
        Authentication authentication = new BearerTokenAuthentication(principal,accessToken,principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
