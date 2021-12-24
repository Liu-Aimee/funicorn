package com.funicorn.cloud.chart.center.vo;

import com.funicorn.cloud.chart.center.entity.FunctionSql;
import com.funicorn.cloud.chart.center.entity.RequestParam;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/9/3 14:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionSqlVO extends FunctionSql {

    /**
     * 数据源名称
     * */
    private String title;

    /**
     * 入参
     * */
    private List<RequestParam> params;

    /**
     * 出参
     * */
    private List<ResponseProp> props;
}
