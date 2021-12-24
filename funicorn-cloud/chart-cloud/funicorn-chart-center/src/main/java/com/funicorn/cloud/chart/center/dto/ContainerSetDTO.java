package com.funicorn.cloud.chart.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import com.funicorn.cloud.chart.center.entity.ContainerData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/9/6 16:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContainerSetDTO extends PageDTO {

    /**创建人*/
    private String createdBy;
    /**主键id*/
    private String id;
    /**容器名称*/
    private String name;
    /**分类id*/
    private String categoryId;
    /**类型id*/
    private String chartTypeId;
    /**是否发布 0已发布 1待发布*/
    private String isRelease;
    /**数据集数组*/
    private List<ContainerData> containerData;
}
