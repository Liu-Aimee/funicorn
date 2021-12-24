package com.funicorn.basic.cloud.gateway.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 路由配置信息表
 * @author Aimee
 */
@Data
@TableName("route_config")
@EqualsAndHashCode(callSuper = true)
public class RouteConfig extends BaseModel {

    /**
     * 主键id
     * */
    @NotBlank(message = "服务id不能为空",groups = Update.class)
    @TableId(value = "id")
    private String id;

    /**
     * 服务名称
     * */
    @NotBlank(message = "服务名称不能为空",groups = Insert.class)
    @TableField(value="name")
    private String name;

    /**
     * 服务id
     * */
    @NotBlank(message = "服务id不能为空",groups = Insert.class)
    @TableField(value="app_id")
    private String appId;

    /**
     * 转发地址
     * */
    @NotBlank(message = "转发地址不能为空",groups = Insert.class)
    @TableField(value="uri")
    private String uri;

    /**
     * 转发规则
     * */
    @NotBlank(message = "转发规则",groups = Insert.class)
    @TableField(value="predicates")
    private String predicates;

    /**
     * 过滤器规则
     * */
    @TableField(value="filters")
    private String filters;

    /**
     * 执行等级 默认0
     * */
    @TableField(value="`order`")
    private Integer order;

    /**
     * 删除状态，0：未删，1：已删
     * */
    @TableField(value="is_delete")
    private String isDelete;
}
