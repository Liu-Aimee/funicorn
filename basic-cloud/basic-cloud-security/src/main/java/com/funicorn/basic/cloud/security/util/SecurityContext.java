package com.funicorn.basic.cloud.security.util;

import com.funicorn.basic.cloud.security.model.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Aimee
 * @since 2020/7/2 11:11
 */
@SuppressWarnings("unused")
public class SecurityContext {

    /**
     * 获取上下文Authentication
     * @return Authentication
     * */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取上下文用户信息
     * @return CurrentUser
     * */
    public static CurrentUser getCurrentUser() {
        Authentication authentication = getAuthentication();
        return getCurrentUser(authentication);
    }

    /**
     * 获取上下文用户信息
     * @param authentication authentication
     * @return CurrentUser
     * */
    private static CurrentUser getCurrentUser(Authentication authentication) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CurrentUser) {
                return (CurrentUser)principal;
            }
        }
        return null;
    }
}
