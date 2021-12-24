package com.funicorn.cloud.chart.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/12/7 9:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataSourcePageDTO extends PageDTO {

    /**
     * 数据库名称
     * */
    private String title;
}
