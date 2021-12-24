package com.funicorn.basic.common.base.util;

import com.funicorn.basic.common.base.model.UserDetail;

/**
 * @author Aimee
 * @since 2021/10/27 10:28
 */
public class ContextHolder {

    private static final ThreadLocal<UserDetail> USER_DETAIL_CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置上下文用户信息
     * @param userDetail 操作人
     * */
    public static void setCurrentUser(UserDetail userDetail){

        USER_DETAIL_CONTEXT_HOLDER.set(userDetail);
    }

    /**
     * 获取上下文用户信息
     * @return LoginUser
     * */
    public static UserDetail getCurrentUser() {
        return USER_DETAIL_CONTEXT_HOLDER.get();
    }

    /**
     * 清除上下文用户信息
     */
    public static void removeCurrentUser() {
        USER_DETAIL_CONTEXT_HOLDER.remove();
    }
}
