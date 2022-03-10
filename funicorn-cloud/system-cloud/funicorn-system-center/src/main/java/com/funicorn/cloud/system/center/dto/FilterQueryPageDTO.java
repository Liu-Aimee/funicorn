package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2022/3/9 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FilterQueryPageDTO extends PageDTO {

    /**
     * 路由id
     * */
    private String routeId;
}
