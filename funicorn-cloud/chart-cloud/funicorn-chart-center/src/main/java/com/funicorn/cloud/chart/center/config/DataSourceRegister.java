package com.funicorn.cloud.chart.center.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.entity.DataSource;
import com.funicorn.cloud.chart.center.mapper.DataSourceMapper;
import com.funicorn.cloud.chart.center.util.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author Aimee
 * @date 2021/9/1 15:33
 *
 * 初始化数据库连接池
 */
@Slf4j
@Component
public class DataSourceRegister {

    @Resource
    private DataSourceMapper dataSourceMapper;

    @PostConstruct
    public void init(){
        log.info(">>>>初始化数据库连接池[begin...]<<<<");
        List<DataSource> dataSources = dataSourceMapper.selectList(Wrappers.<DataSource>lambdaQuery()
                .eq(DataSource::getIsDelete, ChartConstant.NOT_DELETED));

        if (dataSources==null || dataSources.isEmpty()){
            return;
        }

        for (DataSource dataSource:dataSources) {
            try {
                DynamicDataSourceContextHolder.buildDataSource(dataSource);
                log.info("[" + dataSource.getTitle() + "数据源]--已加载");
            } catch (Exception e) {
                log.warn("[" + dataSource.getTitle() + "数据源]--加载失败");
            }
        }

        log.info(">>>>初始化数据库连接池[end...]<<<<");
    }
}
