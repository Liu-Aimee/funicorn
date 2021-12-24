package com.funicorn.basic.cloud.security.config;

import com.funicorn.basic.cloud.security.endpoint.*;
import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import com.funicorn.basic.common.base.util.PermitUrlUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;

/**
 * 认证服务器配置
 * @author Aimee
 * @since 2021/10/29 10:57
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private FunicornConfigProperties funicornConfigProperties;
    @Resource
    private CustomizeAuthFailureHandler customizeAuthFailureHandler;
    @Resource
    private CustomizeAuthSuccessHandler customizeAuthSuccessHandler;
    @Resource
    private CustomizeAuthorizeFilter customizeAuthorizeFilter;
    @Resource
    private CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    /**
     * 密码校验
     * @return BCryptPasswordEncoder
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 指定userDetailsService 和 密码校验方式
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     *
     * http安全配置
     *
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] urls;
        if (funicornConfigProperties.getWhiteUrls()!=null && !funicornConfigProperties.getWhiteUrls().isEmpty()){
            urls = funicornConfigProperties.getWhiteUrls().toArray(new String[0]);
        } else {
            urls = new String[0];
        }
        http.csrf().disable()
                .authorizeRequests()
                //放行的白名单
                .antMatchers(urls).permitAll()
                //放行的登录登出白名单
                .antMatchers(funicornConfigProperties.getSecurity().getLoginApi(), funicornConfigProperties.getSecurity().getLogoutApi()).permitAll()
                //除此之外其他全部需要校验令牌
                .anyRequest().authenticated()

                //登录
                .and().formLogin()
                //指定自定义表单登录时请求的url
                .loginProcessingUrl(funicornConfigProperties.getSecurity().getLoginApi()).permitAll()
                //登录成功处理器
                .successHandler(customizeAuthSuccessHandler)
                //登录失败处理器
                .failureHandler(customizeAuthFailureHandler)

                //登出
                .and().logout().permitAll()
                .logoutSuccessHandler(customizeLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and().addFilterBefore(customizeAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new CustomizeAuthEntryPoint());
    }

    /**
     * 静态资源访问
     * 只配置静态资源路径
     * warn:接口白名单不可写在这里，否则会报404
     * */
    @Override
    public void configure(WebSecurity web){
        List<String> ignoreStaticList = PermitUrlUtil.permitStaticUrl();
        web.ignoring().antMatchers(ignoreStaticList.toArray(new String[0]));
    }

}
