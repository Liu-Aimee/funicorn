package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 容器表
 * @author Aimee
 */
@Data
@TableName("container")
@EqualsAndHashCode(callSuper = true)
public class Container extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "ID",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 容器名称
     * */
    @TableField(value="NAME")
    private String name;

    /**
     * 图表类型id
     * */
    @TableField(value="TYPE_ID")
    private String typeId;

    /**
     * 是否发布 0已发布 1待发布
     * */
    @TableField(value="IS_RELEASE")
    private String isRelease;

    /**
     * 软删标识
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;

}
