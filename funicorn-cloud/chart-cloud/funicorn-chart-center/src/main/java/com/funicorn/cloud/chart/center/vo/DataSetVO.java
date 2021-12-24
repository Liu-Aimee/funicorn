package com.funicorn.cloud.chart.center.vo;

import com.funicorn.cloud.chart.center.entity.DataSet;
import com.funicorn.cloud.chart.center.entity.FunctionSql;
import com.funicorn.cloud.chart.center.entity.RequestParam;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/9/1 18:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataSetVO extends DataSet {

    /**
     * 项目名称
     * */
    private String projectName;

    /**
     * sql类型保存信息
     * */
    private FunctionSql functionSql;

    /**
     * 参数集合
     * */
    private List<RequestParam> params;

    /**
     * 属性集合
     * */
    private List<ResponseProp> props;
}
