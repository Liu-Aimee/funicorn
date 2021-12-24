package com.funicorn.cloud.chart.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.chart.center.entity.DataSource;

/**
 * 数据源配置表业务类
 * @author Aimee
 */
public interface DataSourceService extends IService<DataSource> {

    /**
     * 测试连接
     * @param dataSource 入参
     * */
    void testConnect(DataSource dataSource);

    /**
     * 测试连接
     * @param dataSource 入参
     * @return DataSource
     * */
    DataSource saveDataSource(DataSource dataSource);
}
