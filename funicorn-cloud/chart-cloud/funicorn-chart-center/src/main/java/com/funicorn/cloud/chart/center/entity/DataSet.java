package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.*;

/**
 * 数据集信息表
 * @author Aimee
 */
@Data
@TableName("data_set")
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSet extends BaseModel {
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
     * 类型
     * */
    @TableField(value="TYPE")
    private String type;

    /**
     * 函数id
     * */
    @TableField(value="FUNCTION_ID")
    private String functionId;

    /**
     * 项目id
     * */
    @TableField(value="PROJECT_ID")
    private String projectId;

    /**
     * 软删标识 0未删 1删除
     * */
    @TableField(value="IS_DELETE")
    private String isDelete;
}
