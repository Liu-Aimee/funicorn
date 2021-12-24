package com.funicorn.basic.common.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.model.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 匿名用户访问无权限资源时的异常
 * @author Aimee
 * @since 2021/1/12 14:45
 */
@Slf4j
public class CustomizeAuthEntryPoint implements AuthenticationEntryPoint {

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Result<?> result = Result.error(BaseErrorCode.NO_PERMISSION.getStatus(),BaseErrorCode.NO_PERMISSION.getMessage());
        write(response,result);
    }

    @SneakyThrows
    private void write(HttpServletResponse response, Object obj){
        response.setContentType("application/json;charset=utf-8");
        if (obj instanceof String){
            String msg = (String)obj;
            response.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
        }else {
            response.getOutputStream().write(JSONObject.toJSONString(obj).getBytes(StandardCharsets.UTF_8));
        }
    }
}
