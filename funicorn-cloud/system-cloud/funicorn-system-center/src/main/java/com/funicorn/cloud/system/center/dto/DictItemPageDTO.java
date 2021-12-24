package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/10/15 9:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictItemPageDTO extends PageDTO {

    /**
     * 字典类型
     * */
    private String dictType;
}
