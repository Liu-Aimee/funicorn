package com.funicorn.cloud.chart.center.vo;

import com.funicorn.cloud.chart.center.entity.FunctionHttp;
import com.funicorn.cloud.chart.center.entity.RequestParam;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Aimee
 * @since 2021/9/6 14:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionHttpVO extends FunctionHttp {

    /**
     * ε₯ε
     * */
    private List<RequestParam> params;

    /**
     * εΊε
     * */
    private List<ResponseProp> props;
}
