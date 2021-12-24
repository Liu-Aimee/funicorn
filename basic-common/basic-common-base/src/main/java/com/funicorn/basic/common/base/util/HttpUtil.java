package com.funicorn.basic.common.base.util;

import com.alibaba.fastjson.JSONObject;
import com.funicorn.basic.common.base.model.HttpReqData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aimee
 * @since  2021/4/9 17:53
 */
@SuppressWarnings("unused")
@Slf4j
public class HttpUtil {

    /**
     * 发起http请求
     * @param requestData 请求参数
     * @param responseType 出来类型
     * @param <T> 泛型
     * @throws Exception 异常
     * @return T 出参
     * */
    public static  <T> T doRequest(HttpReqData requestData, Class<T> responseType) throws Exception{
        if (RequestMethod.POST.toString().equals(requestData.getRequestMethod())){
            return doPost(requestData,responseType);
        }else if (RequestMethod.GET.toString().equals(requestData.getRequestMethod())){
            return doGet(requestData,responseType);
        }

        return null;
    }

    /**
     * 发起get请求
     * @param requestData 请求参数
     * @param <T> 泛型
     * @param responseType 出参实体类
     * @throws Exception 异常
     * @return T 实体对象
     * */
    public static <T> T doGet(HttpReqData requestData, Class<T> responseType) throws Exception{
        RestTemplate restTemplate = getRestTemplate(requestData.getConnectTimeout(),requestData.getReadTimeout());
        HttpHeaders headers  = new HttpHeaders();

        if (requestData.getHeaderMap()!=null && !requestData.getHeaderMap().isEmpty()){

            requestData.getHeaderMap().forEach(headers::set);
        }

        StringBuilder params = new StringBuilder();

        if (requestData.getParam()!=null && !requestData.getParam().isEmpty()){

            requestData.getParam().forEach((k,v)-> params.append(k).append("=").append(v).append("&"));
        }

        if (StringUtils.isNotBlank(params)){
            params.deleteCharAt(params.length()-1);
            requestData.setUrl(requestData.getUrl() + "?" + params.toString());
        }

        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(requestData.getParam(), headers);
        ResponseEntity<T> res = restTemplate.exchange(requestData.getUrl(), HttpMethod.GET, requestEntity, responseType);
        return res.getBody();
    }

    /**
     * 发起post请求
     * @param requestData 请求参数
     *  @param <T> 泛型
     * @param responseType 出参实体类
     * @throws Exception 异常
     * @return T 实体对象
     * */
    public static <T> T doPost(HttpReqData requestData,Class<T> responseType) throws Exception{
        RestTemplate restTemplate = getRestTemplate(requestData.getConnectTimeout(),requestData.getReadTimeout());
        HttpHeaders headers  = new HttpHeaders();

        if (requestData.getHeaderMap()!=null && !requestData.getHeaderMap().isEmpty()){

            requestData.getHeaderMap().forEach(headers::set);
        }

        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(requestData.getParam(), headers);
        ResponseEntity<T> res = restTemplate.postForEntity(requestData.getUrl(), requestEntity, responseType);
        return res.getBody();
    }

    /**
     * 发起form表单请求
     * @param <T> 泛型
     * @param requestData 请求参数
     * @param responseType 出参实体类
     * @throws Exception 异常
     * @return T 实体对象
     * */
    public static <T> T doRequestMultiForm(HttpReqData requestData,Class<T> responseType) throws Exception{
        RestTemplate restTemplate = getRestTemplate(requestData.getConnectTimeout(),requestData.getReadTimeout());
        HttpHeaders headers  = new HttpHeaders();

        if (requestData.getHeaderMap()!=null && !requestData.getHeaderMap().isEmpty()){

            requestData.getHeaderMap().forEach(headers::set);
        }

        HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(requestData.getFormParam(), headers);
        ResponseEntity<T> res = restTemplate.postForEntity(requestData.getUrl(), requestEntity, responseType);
        return res.getBody();
    }

    /**
     * 获取请求实例
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取超时时间
     * @return RestTemplate
     * */
    protected static RestTemplate getRestTemplate(Integer connectTimeout, Integer readTimeout){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout!=null ? connectTimeout : 5000);
        requestFactory.setReadTimeout(readTimeout!=null ? readTimeout : 5000);
        return new RestTemplate(requestFactory);
    }

    /**
     * 写入response流
     * @param response 返回流
     * @param obj 对象
     * @throws Exception 异常
     * */
    public static void writeResponse(HttpServletResponse response, Object obj) throws Exception{
        response.setContentType("application/json;charset=utf-8");
        if (obj instanceof String){
            String msg = (String)obj;
            response.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
        }else {
            response.getOutputStream().write(JSONObject.toJSONString(obj).getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * 获取公共请求头
     * @return Map
     * */
    public static Map<String, String> getFireFoxHeader() {
        Map<String, String> fireFoxHeader = new HashMap<>(8);
        fireFoxHeader.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
        fireFoxHeader.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        fireFoxHeader.put("Pragma", "no-cache");
        fireFoxHeader.put("Upgrade-Insecure-Requests", "1");
        fireFoxHeader.put("Connection", "keep-alive");
        fireFoxHeader.put("Cache-Control", "no-cache");
        fireFoxHeader.put("Accept-Encoding", "gzip, deflate");
        return fireFoxHeader;
    }
}
