package com.funicorn.basic.cloud.security.endpoint;

import com.funicorn.basic.cloud.security.constant.LoginErrorCode;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录失败处理器
 * @author Aimee
 * @since 2021/4/22
 */
@Slf4j
@Component
public class CustomizeAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        String errorMsg;
        if (e instanceof AccountExpiredException) {
            //账号过期
            errorMsg = LoginErrorCode.USER_ACCOUNT_EXPIRED.getMessage();
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            errorMsg = LoginErrorCode.USER_CREDENTIALS_ERROR.getMessage();
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            errorMsg = LoginErrorCode.USER_CREDENTIALS_EXPIRED.getMessage();
        } else if (e instanceof DisabledException) {
            //账号不可用
            errorMsg = LoginErrorCode.USER_ACCOUNT_DISABLE.getMessage();
        } else if (e instanceof LockedException) {
            //账号锁定
            errorMsg = LoginErrorCode.USER_ACCOUNT_LOCKED.getMessage();
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户查询异常
            errorMsg = e.getMessage();
        } else{
            //其他错误
            errorMsg = LoginErrorCode.COMMON_FAIL.getMessage();
        }

        response.setStatus(HttpServletResponse.SC_OK);
        Result<?> result = Result.error(BaseErrorCode.LOGIN_FAIL.getStatus(),String.format(BaseErrorCode.LOGIN_FAIL.getMessage(),errorMsg));
        HttpUtil.writeResponse(response, result);
    }
}
