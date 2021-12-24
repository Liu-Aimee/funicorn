package com.funicorn.basic.cloud.security.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/11/6 11:25
 * @deprecated
 */
public class AuthorizationEndpointConfig {

    private final AuthorizationServerEndpointsConfigurer endpoints = new AuthorizationServerEndpointsConfigurer();
    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private final List<AuthorizationServerConfigurer> configurers = Collections.emptyList();

    @PostConstruct
    public void init() {
        for (AuthorizationServerConfigurer configurer : configurers) {
            try {
                configurer.configure(endpoints);
            } catch (Exception e) {
                throw new IllegalStateException("Cannot configure enpdoints", e);
            }
        }
        endpoints.setClientDetailsService(clientDetailsService);
    }

    public AuthorizationServerEndpointsConfigurer getEndpointsConfigurer() {
        if (!endpoints.isTokenServicesOverride()) {
            try {
                endpoints.tokenServices(endpoints.getDefaultAuthorizationServerTokenServices());
            }
            catch (Exception e) {
                throw new BeanCreationException("Cannot create token services", e);
            }
        }
        return endpoints;
    }

    @Primary
    @Bean
    public AuthorizationEndpoint authorizationEndpoint() {
        AuthorizationEndpoint authorizationEndpoint = new AuthorizationEndpoint();
        FrameworkEndpointHandlerMapping mapping = getEndpointsConfigurer().getFrameworkEndpointHandlerMapping();
        authorizationEndpoint.setUserApprovalPage(extractPath(mapping, "/oauth/confirm_access"));
        authorizationEndpoint.setProviderExceptionHandler(getEndpointsConfigurer().getExceptionTranslator());
        authorizationEndpoint.setErrorPage(extractPath(mapping, "/oauth/error"));
        authorizationEndpoint.setTokenGranter(getEndpointsConfigurer().getTokenGranter());
        authorizationEndpoint.setClientDetailsService(clientDetailsService);
        authorizationEndpoint.setAuthorizationCodeServices(getEndpointsConfigurer().getAuthorizationCodeServices());
        authorizationEndpoint.setOAuth2RequestFactory(getEndpointsConfigurer().getOAuth2RequestFactory());
        authorizationEndpoint.setOAuth2RequestValidator(getEndpointsConfigurer().getOAuth2RequestValidator());
        authorizationEndpoint.setUserApprovalHandler(getEndpointsConfigurer().getUserApprovalHandler());
        authorizationEndpoint.setRedirectResolver(getEndpointsConfigurer().getRedirectResolver());
        return authorizationEndpoint;
    }

    private String extractPath(FrameworkEndpointHandlerMapping mapping, String page) {
        String path = mapping.getPath(page);
        if (path.contains(":")) {
            return path;
        }
        return "forward:" + path;
    }
}
