package com.funicorn.basic.common.security.util;

import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.security.model.BearerToken;
import com.funicorn.basic.common.security.model.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

/**
 * @author Aimee
 * @since 2020/7/2 11:11
 */
@SuppressWarnings("unused")
public class SecurityUtil {

    /**
     * 获取上下文Authentication
     * @return Authentication
     * */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取令牌保存的属性
     * @return BearerToken
     * */
    public static BearerToken getAttributes() {
        Authentication authentication = getAuthentication();
        if (authentication instanceof BearerTokenAuthentication) {
            BearerTokenAuthentication bearerTokenAuthentication = (BearerTokenAuthentication)authentication;
            return JsonUtil.object2Object(bearerTokenAuthentication.getTokenAttributes(),BearerToken.class);
        }
        return null;
    }

    /**
     * 获取上下文用户信息
     * @return CurrentUser
     * */
    public static CurrentUser getCurrentUser() {
       return getUserFromAttributes();
    }

    /**
     * 加个这个的目的：消除其他地方调用getCurrentUser出现警告
     * @return CurrentUser
     * */
    private static CurrentUser getUserFromAttributes(){
        BearerToken bearerToken = getAttributes();
        return bearerToken==null ? null : bearerToken.getCurrentUser();
    }

}
