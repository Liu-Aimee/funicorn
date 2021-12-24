package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/8/23 11:02
 */
@Data
public class AppSecretDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 应用id
     * */
    @NotBlank(message = "应用id不能为空",groups = Update.class)
    private String appId;

    /**
     * 认证密钥
     * */
    @NotBlank(message = "密钥不能为空",groups = Update.class)
    private String clientSecret;
}
