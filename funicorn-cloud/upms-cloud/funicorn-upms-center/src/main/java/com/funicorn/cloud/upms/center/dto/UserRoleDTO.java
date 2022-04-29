package com.funicorn.cloud.upms.center.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Aimee
 * @since 2022/4/14 14:10
 */
@Data
public class UserRoleDTO {

    /**
     * 角色id
     * */
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    /**
     * 用户id集合
     * */
    @NotBlank(message = "用户id不能为空")
    @NotEmpty(message = "用户id不能为空")
    private List<String> userIds;
}
