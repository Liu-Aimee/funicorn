package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图表类型表
 * @author Aimee
 */
@Data
@TableName("chart_type")
@EqualsAndHashCode(callSuper = true)
public class ChartType extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "id",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 图表类型名称
     * */
    @TableField(value="name")
    private String name;

    /**
     * 图表类型id
     * */
    @TableField(value="category_id")
    private Long categoryId;

    /**
     * 类型对应bean名称
     * */
    @TableField(value="bean_type")
    private String beanType;

    /**
     * 前端路由
     * */
    @TableField(value="router")
    private String router;

    /**
     * 示例图片
     * */
    @TableField(value="example")
    private byte[] example;

    /**
     * 示例图片路径
     * */
    @TableField(value="example_url")
    private String exampleUrl;

    /**
     * 软删标识
     * */
    @TableField(value="is_delete")
    private String isDelete;
}
