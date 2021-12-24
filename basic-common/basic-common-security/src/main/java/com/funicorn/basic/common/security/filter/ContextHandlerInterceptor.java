package com.funicorn.basic.common.security.filter;

import com.funicorn.basic.common.base.util.ContextHolder;
import com.funicorn.basic.common.security.model.ContextUser;
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 清除线程本地变量拦截器,防止内存泄露
 * @author Aimee
 * @since 2021/9/27 17:34
 */
@Slf4j
@Component
public class ContextHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @Nullable Object handler) throws Exception {
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        if (currentUser!=null){
            ContextUser contextUser = new ContextUser();
            contextUser.setUserId(currentUser.getId());
            contextUser.setUsername(currentUser.getUsername());
            contextUser.setTenantId(currentUser.getTenantId());
            ContextHolder.setCurrentUser(contextUser);
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) throws Exception {
        //清除线程本地变量，防止内存泄露
        ContextHolder.removeCurrentUser();
    }
}
