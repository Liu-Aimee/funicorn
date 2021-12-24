package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/5/25 16:28
 */
@Data
public class RoleMenuDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 角色id
     * */
    @NotBlank(message = "角色id不能为空",groups = Insert.class)
    private String roleId;

    /**
     * 菜单信息数组
     * */
    @NotEmpty(message = "绑定菜单不能为空",groups = Insert.class)
    private List<AppMenu> appMenus;

    @Data
    public static class AppMenu{

        /**
         * 应用id
         * */
        @NotBlank(message = "应用id不能为空",groups = Insert.class)
        private String appId;

        /**
         * 菜单id数组
         * */
        @NotEmpty(message = "菜单id数组不能为空",groups = Insert.class)
        private List<String> menuIds;
    }
}
