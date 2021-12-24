package com.funicorn.cloud.chart.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.chart.center.dto.FunctionSqlDTO;
import com.funicorn.cloud.chart.center.entity.FunctionSql;
import com.funicorn.cloud.chart.center.vo.FunctionSqlVO;

import java.util.List;
import java.util.Map;

/**
 * 业务类
 * @author Aimee
 */
public interface FunctionSqlService extends IService<FunctionSql> {

    /**
     * 分页查询
     * @param functionSqlDTO 分页信息
     * @return IPage<FunctionSqlVO>
     * */
    IPage<FunctionSqlVO> page(FunctionSqlDTO functionSqlDTO);

    /**
     * sql语句测试
     * @param functionSqlDTO 入参
     * @return List<Map<String,Object>>
     * */
    List<Map<String,Object>> testSql(FunctionSqlDTO functionSqlDTO);

    /**
     * 保存sql
     * @param functionSqlDTO 入参
     * */
    FunctionSqlDTO add(FunctionSqlDTO functionSqlDTO);

    /**
     * 修改sql
     * @param functionSqlDTO 入参
     * */
    void update(FunctionSqlDTO functionSqlDTO);
}
