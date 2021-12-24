package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 容器数据集表
 * @author Aimee
 */
@Data
@TableName("container_data")
@EqualsAndHashCode(callSuper = true)
public class ContainerData extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "ID",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 容器id
     * */
    @TableField(value="CONTAINER_ID")
    private String containerId;

    /**
     * 数据集id
     * */
    @TableField(value="DATA_ID")
    private String dataId;

    /**
     * 数据集别名
     * */
    @TableField(value="DATA_ALIAS")
    private String dataAlias;

    /**
     * 属性标记
     * */
    @TableField(value="DATA_PROPERTY_SIGNS")
    private String dataPropertySigns;

    /**
     * 参数值
     * */
    @TableField(value="DATA_PARAM_VALUES")
    private String dataParamValues;

    /**
     * 软删标识
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;

}
