package com.funicorn.cloud.system.api.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2021/10/15 15:22
 */
@Data
public class DictItemQueryDTO {

    /**
     * 字典类型
     * */
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
