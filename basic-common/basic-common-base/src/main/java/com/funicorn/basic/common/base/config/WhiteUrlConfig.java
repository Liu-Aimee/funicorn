package com.funicorn.basic.common.base.config;

import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import com.funicorn.basic.common.base.util.PermitUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/4/23 14:11
 * 白名单加载配置
 */
@Configuration
@Slf4j
public class WhiteUrlConfig {

    private final FunicornConfigProperties funicornConfigProperties;

    public WhiteUrlConfig (FunicornConfigProperties funicornConfigProperties){
        this.funicornConfigProperties = funicornConfigProperties;
    }

    @PostConstruct
    void init(){
        List<String> whiteList = PermitUrlUtil.permitAllUrl();
        if (funicornConfigProperties!=null && funicornConfigProperties.getWhiteUrls()!=null && !this.funicornConfigProperties.getWhiteUrls().isEmpty()){
            whiteList = PermitUrlUtil.permitAllUrl(funicornConfigProperties.getWhiteUrls());
        }
        whiteList.forEach(log::info);
    }
}
