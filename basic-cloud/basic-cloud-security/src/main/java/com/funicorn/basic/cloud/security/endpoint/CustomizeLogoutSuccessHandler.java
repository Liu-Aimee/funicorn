package com.funicorn.basic.cloud.security.endpoint;

import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出处理器
 * @author Aimee
 * @since 2021/11/2 15:22
 */
@Slf4j
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private TokenStore tokenStore;

    @SneakyThrows
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authorization = request.getHeader(BaseConstant.ACCESS_TOKEN);
        if(StringUtils.isNotBlank(authorization)){
            authorization = authorization.replace("Bearer ", "").replace("bearer ", "");
            OAuth2AccessToken token = tokenStore.readAccessToken(authorization);
            if (token!=null){
                tokenStore.removeAccessToken(token);
                OAuth2RefreshToken refreshToken = token.getRefreshToken();
                if (refreshToken!=null) {
                    tokenStore.removeRefreshToken(token.getRefreshToken());
                }
            }
        }
        Result<?> result = Result.ok("登出成功");
        response.setStatus(HttpServletResponse.SC_OK);
        HttpUtil.writeResponse(response,result);
    }
}
