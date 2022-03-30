package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/10/15 9:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeQueryPageDTO extends PageDTO {

    /**
     * 类型名称
     * */
    private String name;

    /**
     * 类型
     * */
    private String type;

    /**
     * 租户id
     * */
    private String tenantId;
}
