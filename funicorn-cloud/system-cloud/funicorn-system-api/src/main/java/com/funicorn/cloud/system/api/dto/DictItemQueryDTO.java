package com.funicorn.cloud.system.api.dto;

import com.funicorn.basic.common.base.valid.Query;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Aimee
 * @since 2021/10/15 15:22
 */
@Data
public class DictItemQueryDTO {

    /**
     * 字典类型
     * */
    @NotBlank(message = "字典类型不能为空",groups = Query.class)
    private String dictType;

    /**
     * 值
     * */
    private String dictValue;

    /**
     * 标签
     * */
    private String dictLabel;
}
