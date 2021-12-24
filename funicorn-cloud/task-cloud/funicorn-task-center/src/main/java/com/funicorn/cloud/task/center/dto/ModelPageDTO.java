package com.funicorn.cloud.task.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/12/10 14:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelPageDTO extends PageDTO {

    private String modelId;

    private String modelKey;

    private String modelName;
}
