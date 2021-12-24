package com.funicorn.cloud.upms.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树
 * @author Aimee
 * @since 2021/1/26 17:48
 */
@Data
public class MenuTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     * */
    private String appId;

    /**
     * 菜单id
     * */
    private String id;

    /**
     * 父菜单id
     * */
    private String parentId;

    /**
     * 类型（menu/button）
     * */
    private String type;

    /**
     * 名称
     * */
    private String name;

    /**
     * 权限
     * */
    private String permission;

    /**
     * 路由
     * */
    private String router;

    /**
     * status: hidden 隐藏,show 显示,disabled 禁用
     * */
    private String status;

    /**
     * 路径
     * */
    private String path;

    /**
     * 图标
     * */
    private String icon;

    /**
     * 排序 升序
     * */
    private Integer sort;

    /**
     * 子节点
     * */
    private List<MenuTree> children;
}
