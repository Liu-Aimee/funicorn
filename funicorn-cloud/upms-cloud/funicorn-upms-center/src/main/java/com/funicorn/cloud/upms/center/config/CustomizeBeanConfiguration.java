package com.funicorn.cloud.upms.center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Aimee
 * @since 2021/5/21 9:37
 */
@Configuration
public class CustomizeBeanConfiguration {

    /**
     * 初始化密码加密器
     * @return PasswordEncoder
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
