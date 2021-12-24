package com.funicorn.cloud.chart.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.chart.center.dto.FunctionHttpDTO;
import com.funicorn.cloud.chart.center.entity.FunctionHttp;
import com.funicorn.cloud.chart.center.vo.FunctionHttpVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口函数表 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-09-06
 */
public interface FunctionHttpService extends IService<FunctionHttp> {

    /**
     * 分页查询
     * @param functionHttpDTO 分页信息
     * @return IPage<FunctionHttpVO>
     * */
    IPage<FunctionHttpVO> page(FunctionHttpDTO functionHttpDTO);

    /**
     * HTTP接口测试
     * @param functionHttpDTO 入参
     * @return List<Map<String,Object>>
     * */
    List<Map<String,Object>> testHttp(FunctionHttpDTO functionHttpDTO);

    /**
     * 保存sql
     * @param functionHttpDTO 入参
     * */
    void addFunctionHttp(FunctionHttpDTO functionHttpDTO);

    /**
     * 修改sql
     * @param functionHttpDTO 入参
     * */
    void updateFunctionHttp(FunctionHttpDTO functionHttpDTO);
}
