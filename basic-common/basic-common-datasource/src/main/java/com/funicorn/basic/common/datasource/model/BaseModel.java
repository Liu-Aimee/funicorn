package com.funicorn.basic.common.datasource.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础model父类
 * @author Aimee
 * @since 2020/8/4 11:02
 */
@Data
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 创建时间 */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /** 更新时间 */
    @TableField(value = "updated_time", fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    /** 创建人 */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /** 更新人 */
    @TableField(value = "updated_by", fill = FieldFill.UPDATE)
    private String updatedBy;
}
