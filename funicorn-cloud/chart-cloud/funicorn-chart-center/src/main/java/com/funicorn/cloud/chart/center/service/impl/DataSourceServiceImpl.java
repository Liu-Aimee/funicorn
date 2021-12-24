package com.funicorn.cloud.chart.center.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.cloud.chart.center.entity.DataSource;
import com.funicorn.cloud.chart.center.exception.DatavException;
import com.funicorn.cloud.chart.center.exception.ErrorCode;
import com.funicorn.cloud.chart.center.mapper.DataSourceMapper;
import com.funicorn.cloud.chart.center.service.DataSourceService;
import com.funicorn.cloud.chart.center.util.DBUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据源配置表业务实现类
 * @author Aimee
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

    @Override
    public void testConnect(DataSource dataSource) {
        Connection connection = DBUtil.getConnection(dataSource.getDriver(),dataSource.getUrl(),dataSource.getUsername(),dataSource.getPassword());
        if (connection==null){
            throw new DatavException(ErrorCode.SQL_CONNECTION_FAIL);
        }
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public DataSource saveDataSource(DataSource dataSource) {
        baseMapper.insert(dataSource);
        return dataSource;
    }
}