package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入参参数表
 * @author Aimee
 */
@Data
@TableName("request_param")
@EqualsAndHashCode(callSuper = true)
public class RequestParam extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "ID",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 数据集id
     * */
    @TableField(value="FUNCTION_ID")
    private String functionId;

    /**
     * 字段名称
     * */
    @TableField(value="NAME")
    private String name;

    /**
     * 字段类型 STRING|NUMBER|BOOLEAN
     * */
    @TableField(value="TYPE")
    private String type;

    /**
     * 展示名称
     * */
    @TableField(value="LABEL")
    private String label;

    /**
     * 默认值
     * */
    @TableField(value = "DEFAULT_VALUE")
    private String defaultValue;

    /**
     * 软删标识 0未删 1删除
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;

}
