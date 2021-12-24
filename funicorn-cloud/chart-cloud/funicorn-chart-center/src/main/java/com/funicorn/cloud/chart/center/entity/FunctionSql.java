package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sql类型函数表
 * @author Aimee
 */
@Data
@TableName("function_sql")
@EqualsAndHashCode(callSuper = true)
public class FunctionSql extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "ID",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * SQL名称
     * */
    @TableField(value="NAME")
    private String name;

    /**
     * 数据源id
     * */
    @TableField(value="DATASOURCE_ID")
    private String datasourceId;

    /**
     * sql语句
     * */
    @TableField(value="SQL_CONTENT")
    private String sqlContent;

    /**
     * 软删标识 0未删 1删除
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;

}
