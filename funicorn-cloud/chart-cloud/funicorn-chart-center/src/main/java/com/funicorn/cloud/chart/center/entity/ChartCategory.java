package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Aimee
 */
@Data
@TableName("chart_category")
@EqualsAndHashCode(callSuper = true)
public class ChartCategory extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "ID",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 分类名称
     * */
    @TableField(value="NAME")
    private String name;

    /**
     * 软删标识
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;

}
