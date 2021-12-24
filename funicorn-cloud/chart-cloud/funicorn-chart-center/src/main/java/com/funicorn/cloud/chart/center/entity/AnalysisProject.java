package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目管理表
 * @author Aimee
 */
@Data
@TableName("analysis_project")
@EqualsAndHashCode(callSuper = true)
public class AnalysisProject extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "ID",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 名称
     * */
    @TableField(value="NAME")
    private String name;

    /**
     * 描述
     * */
    @TableField(value="REMARK")
    private String remark;

    /**
     * 软删标识 0未删 1删除
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;
}
