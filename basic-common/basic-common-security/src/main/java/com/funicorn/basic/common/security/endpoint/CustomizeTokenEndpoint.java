package com.funicorn.basic.common.security.endpoint;

import com.funicorn.basic.common.base.constant.BaseConstant;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.property.FunicornConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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
     * @return Result AccessToken
     * */
    @RequestMapping(value = "/auth/exchangeToken", method= RequestMethod.GET)
    public Result<Object> exchangeToken(@RequestParam String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = funicornConfigProperties.getSecurity().getServerAddr() + funicornConfigProperties.getSecurity().getTokenApi() + "?";
        String clientId = funicornConfigProperties.getSecurity().getClientId();
        String redirectUri = funicornConfigProperties.getSecurity().getRedirectUri();
        String grantType = funicornConfigProperties.getSecurity().getGrantType();
        String clientSecret = funicornConfigProperties.getSecurity().getClientSecret();
        url = url + "code=" + code + "&client_id=" + clientId +"&redirect_uri="
                + redirectUri + "&client_secret=" + clientSecret + "&grant_type=" +grantType;
        HttpEntity<String> requestEntity = new HttpEntity<>(null, null);
        log.debug("code exchange token url is : " + url);
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
