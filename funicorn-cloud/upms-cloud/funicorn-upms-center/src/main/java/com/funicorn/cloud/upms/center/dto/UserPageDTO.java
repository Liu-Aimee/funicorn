package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 租户id
     * */
    private String tenantId;
}
