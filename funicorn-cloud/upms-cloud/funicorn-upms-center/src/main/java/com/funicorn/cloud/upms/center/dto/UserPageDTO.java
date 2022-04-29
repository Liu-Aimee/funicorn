package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/10/20 9:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 用户昵称
     * */
    private String nickName;

    /**
     * 用户类型
     * */
    private String type;

    /**
     * 租户id
     * */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
}
