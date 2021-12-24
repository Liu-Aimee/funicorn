package com.funicorn.basic.cloud.security.endpoint;

import com.funicorn.basic.common.base.constant.BaseConstant;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.endpoint.DefaultRedirectResolver;

import java.net.URLEncoder;

/**
 * @author Aimee
 * @since 2021/11/6 10:54
 */
public class CustomizeRedirectResolver extends DefaultRedirectResolver {

    @SneakyThrows
    @Override
    public String resolveRedirect(String requestedRedirect, ClientDetails client) throws OAuth2Exception {
        return URLEncoder.encode(super.resolveRedirect(requestedRedirect,client), BaseConstant.CHARSET_UTF8);
    }
}
