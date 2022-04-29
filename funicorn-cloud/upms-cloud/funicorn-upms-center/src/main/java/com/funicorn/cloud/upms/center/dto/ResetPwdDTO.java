package com.funicorn.cloud.upms.center.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Aimee
 * @since 2020/8/10 11:55
 */
@Data
public class ResetPwdDTO {

    /**
     * 用户id
     * */
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 密码
     * */
    @NotBlank(message = "密码不能为空")
    private String password;
}
