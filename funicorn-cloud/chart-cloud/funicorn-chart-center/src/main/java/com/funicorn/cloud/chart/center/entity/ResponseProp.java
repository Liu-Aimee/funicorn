package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出参属性表
 * @author Aimee
 */
@Data
@TableName("response_prop")
@EqualsAndHashCode(callSuper = true)
public class ResponseProp extends BaseModel {
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
     * 属性名
     * */
    @TableField(value="NAME")
    private String name;

    /**
     * 属性类型
     * */
    @TableField(value="TYPE")
    private String type;

    /**
     * 展示名称
     * */
    @TableField(value="LABEL")
    private String label;

    /**
     * 软删标识 0未删 1删除
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;

}
