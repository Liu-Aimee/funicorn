package com.funicorn.cloud.system.center.controller;

import com.funicorn.basic.common.base.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aimee
 * @since 2021/12/24 14:49
 */
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @GetMapping("/sendMessage")
    public Result<?> sendMessage(){
        //MqttUtil.sendMessage("发送测试消息到mqtt服务器");
        return Result.ok();
    }
}
