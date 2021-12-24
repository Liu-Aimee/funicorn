package com.funicorn.basic.common.datasource.dto;

import lombok.Data;

/**
 * @author Aimee
 * @since 2021/10/19 16:57
 */

@Data
public class PageDTO {

    /**
     * 当前页 默认 1
     */
    protected long current = 1;

    /**
     * 每页显示条数，默认 10
     */
    protected long size = 10;
}
