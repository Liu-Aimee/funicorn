package com.funicorn.cloud.chart.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.chart.center.dto.FunctionSqlDTO;
import com.funicorn.cloud.chart.center.entity.FunctionSql;
import com.funicorn.cloud.chart.center.vo.FunctionSqlVO;
import org.apache.ibatis.annotations.Param;

/**
 * 映射类
 * @author Aimee
 */
public interface FunctionSqlMapper extends BaseMapper<FunctionSql> {

    /**
     * 分页查询
     * @param page 分页信息
     * @param functionSqlDTO 入参
     * @return IPage<FunctionSqlVO>
     * */
    IPage<FunctionSqlVO> queryPage(Page<FunctionSqlVO> page, @Param("functionSqlDTO") FunctionSqlDTO functionSqlDTO);
}
