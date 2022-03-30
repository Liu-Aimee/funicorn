package com.funicorn.basic.common.base.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Aimee
 * @since 2022/3/30 11:37
 */
@Data
public class DateModel {

    /**
     * 开始时间
     * */
    private Date startDateTime;

    /**
     * 结束时间
     * */
    private Date endDateTime;

    /**
     * 当年的第多少周
     * */
    private Integer week;

    /**
     * 当年的第多少月
     * */
    private Integer month;
}
