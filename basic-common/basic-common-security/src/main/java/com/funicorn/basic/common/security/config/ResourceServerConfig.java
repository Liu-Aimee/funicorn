package com.funicorn.basic.common.security.config;

import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import com.funicorn.basic.common.base.util.PermitUrlUtil;
import com.funicorn.basic.common.security.endpoint.CustomizeOpaqueTokenIntrospector;
import com.funicorn.basic.common.security.handler.CustomizeAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源服务器配置
 * @author Aimee
 * @since 2021/10/28 14:53
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private FunicornConfigProperties funicornConfigProperties;

    @Bean
    public CustomizeOpaqueTokenIntrospector customizeOpaqueTokenIntrospector () {
        return new CustomizeOpaqueTokenIntrospector()
                .introspectionUri(funicornConfigProperties.getSecurity().getServerAddr() + funicornConfigProperties.getSecurity().getCheckTokenApi())
                .introspectionClientCredentials(funicornConfigProperties.getSecurity().getClientId(), funicornConfigProperties.getSecurity().getClientSecret());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //令牌字符串解析器 去掉令牌的Bearer前缀
        DefaultBearerTokenResolver defaultBearerTokenResolver = new DefaultBearerTokenResolver();
        defaultBearerTokenResolver.setBearerTokenHeaderName(BaseConstant.ACCESS_TOKEN);
        //白名单数组
        String[] urls = PermitUrlUtil.permitInterfaceUrl().toArray(new String[0]);
        //http配置
        http.csrf().disable()
                .authorizeRequests().antMatchers(urls).permitAll().anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomizeAuthEntryPoint())
                .and()
                .oauth2ResourceServer()
                .bearerTokenResolver(defaultBearerTokenResolver)
                .opaqueToken()
                .introspector(customizeOpaqueTokenIntrospector());
    }

    @Override
    public void configure(WebSecurity web) {
        List<String> ignoreStaticList = PermitUrlUtil.permitStaticUrl();
        web.ignoring().antMatchers(ignoreStaticList.toArray(new String[0]));
    }
}
