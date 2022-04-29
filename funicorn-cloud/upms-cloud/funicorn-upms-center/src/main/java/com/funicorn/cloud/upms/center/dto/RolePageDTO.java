package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/10/20 10:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 角色名称
     * */
    private String name;

    /**
     * 角色编码
     * */
    private String code;

    /**
     * 租户id
     * */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
}
