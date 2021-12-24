package com.funicorn.cloud.chart.center.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.basic.common.datasource.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据源配置表
 * @author Aimee
 */
@Data
@TableName("data_source")
@EqualsAndHashCode(callSuper = true)
public class DataSource extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * */
    @TableId(value = "id",type= IdType.ASSIGN_ID)
    private String id;

    /**
     * 数据库名称
     * */
    @TableField(value="title")
    private String title;

    /**
     * 数据库驱动
     * */
    @TableField(value="driver")
    private String driver;

    /**
     * 数据库url
     * */
    @TableField(value="url")
    private String url;

    /**
     * 用户名
     * */
    @TableField(value="username")
    private String username;

    /**
     * 密码
     * */
    @TableField(value="password")
    private String password;

    /**
     * 软删标识 0未删 1删除
     * */
    @TableField(value="is_delete")
    private String isDelete;
}
