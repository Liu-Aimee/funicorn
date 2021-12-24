package com.funicorn.cloud.system.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Aimee
 * @since 2021/10/15 15:29
 */
@Data
public class DictItemVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 值
     */
    private String dictValue;

    /**
     * 标签
     */
    private String dictLabel;

    /**
     * 排序（升序）
     */
    private Integer sort;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedTime;

    /** 创建人 */
    private String createdBy;

    /** 更新人 */
    private String updatedBy;
}
