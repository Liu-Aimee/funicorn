package com.funicorn.cloud.chart.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.chart.center.dto.FunctionHttpDTO;
import com.funicorn.cloud.chart.center.entity.FunctionHttp;
import com.funicorn.cloud.chart.center.vo.FunctionHttpVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 接口函数表 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-09-06
 */
public interface FunctionHttpMapper extends BaseMapper<FunctionHttp> {

    /**
     * 分页查询
     * @param page 分页信息
     * @param functionHttpDTO 入参
     * @return IPage<FunctionHttpVO>
     * */
    IPage<FunctionHttpVO> queryPage(Page<FunctionHttpVO> page , @Param("functionHttpDTO") FunctionHttpDTO functionHttpDTO);
}
