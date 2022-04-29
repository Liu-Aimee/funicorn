package com.funicorn.basic.common.security.endpoint;

import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * code换取令牌端点
 * @author Aimee
 * @since 2021/11/1 17:19
 */
@RestController
@Slf4j
public class CustomizeTokenEndpoint {

    @Resource
    private FunicornConfigProperties funicornConfigProperties;

    /**
     * code换取令牌
     * @param code code
     * @param redirectUri 回调地址
     * @return Result AccessToken
     * */
    @PostMapping(value = "/auth/exchangeToken")
    public Result<Object> exchangeToken(@RequestParam String code,@RequestParam String redirectUri) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = funicornConfigProperties.getSecurity().getServerAddr() + funicornConfigProperties.getSecurity().getTokenApi() + "?";
        String clientId = funicornConfigProperties.getSecurity().getClientId();
        String grantType = funicornConfigProperties.getSecurity().getGrantType();
        String clientSecret = funicornConfigProperties.getSecurity().getClientSecret();
        // 表单形式提交，可以传递包含特殊符号的redirect_uri
        MultiValueMap<String,Object> formData = new LinkedMultiValueMap<>();
        formData.set("redirect_uri", URLDecoder.decode(redirectUri,"UTF-8"));
        formData.set("code", code);
        formData.set("client_id", clientId);
        formData.set("client_secret", clientSecret);
        formData.set("grant_type", grantType);
        HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(formData, null);
        try {
            ResponseEntity<Object> res  = restTemplate.postForEntity(url, requestEntity, Object.class);
            return Result.ok(res.getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登出
     * @param authorization 令牌
     * @return Result
     * */
    @SuppressWarnings("all")
    @GetMapping("/auth/logout")
    public Result<Boolean> logout(@RequestHeader(BaseConstant.ACCESS_TOKEN) String authorization) {
        String url = funicornConfigProperties.getSecurity().getServerAddr() + funicornConfigProperties.getSecurity().getLogoutApi();
        HttpHeaders headers  = new HttpHeaders();
        headers.set(BaseConstant.ACCESS_TOKEN,authorization);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result> res = restTemplate.postForEntity(url, requestEntity, Result.class);
        Result<?> result = res.getBody();
        if (result!=null && result.getCode()==200 && result.isSuccess()){
            return Result.ok(true);
        }else {
            return Result.error("登出失败!");
        }
    }
}
