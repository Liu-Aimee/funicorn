package com.funicorn.cloud.chart.center.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.funicorn.cloud.chart.center.entity.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aimee
 * @date 2021/9/1 15:46
 */
@Slf4j
@SuppressWarnings("all")
public class DynamicDataSourceContextHolder {

    private static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    public static final Map<String, DruidDataSource> druidDataSourceMap = new HashMap<>();

    public static void buildDataSource(DataSource dataSource) {
        Class<? extends DruidDataSource> dataSourceType;
        try {
            dataSourceType = (Class<? extends DruidDataSource>) Class.forName(DATASOURCE_TYPE_DEFAULT);
            String driverClassName = dataSource.getDriver();
            String url = dataSource.getUrl();
            url=url.replaceAll("&amp;", "&");
            String username = dataSource.getUsername();
            String password = dataSource.getPassword();
            DataSourceBuilder factory =   DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
            DruidDataSource druidDataSource=(DruidDataSource) factory.build();
            //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
            druidDataSource.setInitialSize(5);
            //最大连接池数量
            druidDataSource.setMaxActive(20);
            //最小连接池数量
            druidDataSource.setMinIdle(5);
            /**
             * 获取连接时最大等待时间，单位毫秒。
             * 配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁
             */
            druidDataSource.setMaxWait(15000);
            /**
             * 用来检测连接是否有效的sql，要求是一个查询语句。
             * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用
             */
            druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
            //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
            druidDataSource.setTestOnBorrow(false);
            /**
             * 建议配置为true，不影响性能，并且保证安全性。
             * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
             */
            druidDataSource.setTestWhileIdle(true);

            druidDataSourceMap.put(dataSource.getId(),druidDataSource);
        } catch (ClassNotFoundException e) {
            log.error("创建连接池异常",e);
        }
    }
}
