package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/11/2 14:41
 */
@Data
public class RoleDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 角色id 新增时自动生成
     * */
    @NotBlank(message = "角色id不能为空",groups = Update.class)
    private String id;

    /**
     * 角色名称
     * */
    @NotBlank(message = "角色名称不能为空",groups = Insert.class)
    private String name;

    /**
     * 角色编号
     * */
    @NotBlank(message = "角色编号不能为空",groups = Insert.class)
    private String code;

    /**
     * 租户id
     * */
    @NotBlank(message = "租户id不能为空",groups = Insert.class)
    private String tenantId;
}
