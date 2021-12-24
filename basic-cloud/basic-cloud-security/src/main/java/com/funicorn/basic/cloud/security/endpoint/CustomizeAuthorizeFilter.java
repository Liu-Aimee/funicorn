package com.funicorn.basic.cloud.security.endpoint;

import com.funicorn.basic.cloud.security.model.CurrentUser;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Aimme
 * @since 2021/1/11 16:31
 */
@Slf4j
@Component
public class CustomizeAuthorizeFilter extends OncePerRequestFilter {

    private static final String FILTER_URL = "/oauth/authorize";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @SneakyThrows
    @SuppressWarnings("all")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        if (!request.getRequestURI().contains(FILTER_URL)){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            CurrentUser loginUser = (CurrentUser) redisTemplate.opsForValue().get(request.getParameter("code"));
            if (loginUser==null){
                response.setStatus(HttpServletResponse.SC_OK);
                Result<?> result = Result.error(BaseErrorCode.NO_PERMISSION.getStatus(),BaseErrorCode.NO_PERMISSION.getMessage());
                HttpUtil.writeResponse(response, result);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            redisTemplate.delete(request.getParameter("code"));
        }
    }
}
