package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/11/1 10:09
 */
@Data
public class MenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @NotBlank(message = "菜单id不能为空",groups = Update.class)
    private String id;

    /**
     * 父id 默认0
     */
    private String parentId;

    /**
     * 应用id
     */
    @NotBlank(message = "应用id不能为空",groups = Insert.class)
    private String appId;

    /**
     * 类型（menu/button）
     */
    @NotBlank(message = "菜单类型不能为空",groups = Insert.class)
    private String type;

    /**
     * 名称
     */
    @NotBlank(message = "菜单名称不能为空",groups = Insert.class)
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 路由
     */
    private String router;

    /**
     * status: hidden->隐藏,  show->显示,  disabled -> 禁用 默认show
     */
    private String status;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序 升序 默认 0
     */
    private Integer sort;

    /**
     * 是否自动授权给租户管理员 true/false 默认true
     * */
    private boolean automatic = true;
}
