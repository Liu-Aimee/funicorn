package com.funicorn.cloud.chart.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/12/4 9:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContainerPageDTO extends PageDTO {

    /**
     * 容器名称
     * */
    private String name;

    /**
     * 图表大类型id
     * */
    private String categoryId;

    /**
     * 图表细分类型id
     **/
    private String typeId;

    /**
     * 是否发布 0已发布 1待发布
     * */
    private String isRelease;

    /**
     * 创建人
     * */
    private String createdBy;
}
