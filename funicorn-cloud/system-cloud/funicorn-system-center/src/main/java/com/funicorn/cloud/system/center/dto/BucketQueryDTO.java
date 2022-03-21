package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2022/3/19 9:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BucketQueryDTO extends PageDTO {

    /**
     * 租户id
     * */
    private String tenantId;
}
