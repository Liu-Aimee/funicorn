package com.funicorn.cloud.chart.center.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.funicorn.basic.common.base.util.AppContextUtil;
import com.funicorn.cloud.chart.center.entity.DataSource;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import com.funicorn.cloud.chart.center.mapper.DataSourceMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aimme
 * @create 2020/11/10 14:12
 */
@SuppressWarnings("all")
public class DBUtil {

    /**
     * 获取连接池
     * @return Connection
     * */
    public static Connection  getConnection(String driver,String url,String username,String password){
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从已加载的连接池获取数据库连接
     * @param dataSourceId 连接池key
     * @return Connection
     * */
    public static Connection getConnection(String dataSourceId){
        DruidDataSource druidDataSource = DynamicDataSourceContextHolder.druidDataSourceMap.get(dataSourceId);
        if (druidDataSource==null){
            DataSourceMapper dataSourceMapper = AppContextUtil.getBean(DataSourceMapper.class);
            DataSource dataSource = dataSourceMapper.selectById(dataSourceId);
            if (dataSource==null){
                return null;
            }
            DynamicDataSourceContextHolder.buildDataSource(dataSource);
            druidDataSource = DynamicDataSourceContextHolder.druidDataSourceMap.get(dataSourceId);
        }
        try {
            return druidDataSource.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    /*
    *
    * 执行select sql语句
    *
    * */
    public static List<Map<String,Object>> selectObj(Connection connection, String sql, List<ResponseProp> props){
        List<String> tables = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        List<Map<String,Object>> resultList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            // 查询此数据库有多少个表格
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                Map<String,Object> dataMap = new HashMap<>();
                for (int i = 0; i < props.size(); i++) {
                    String key = resultSet.getMetaData().getColumnName(i+1);
                    Object value = resultSet.getObject(props.get(i).getName());
                    dataMap.put(key,value);
                }
                resultList.add(dataMap);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                if (resultSet!=null){
                    resultSet.close();
                }

                if (statement!=null){
                    statement.close();
                }

                if (connection!=null){
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return resultList;
    }

    /*
    * 查询所有表名称
    * */
    public static List<String> getAllTables(Connection connection){

        List<String> tables = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Show tables");// 查询此数据库有多少个表格

            while (resultSet.next()){
                tables.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                if (resultSet!=null){
                    resultSet.close();
                }

                if (statement!=null){
                    statement.close();
                }

                if (connection!=null){
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return tables;
    }


    /*
     * 查询指定表所有字段名称
     * */
    public static List<String> getColumnByTableName(Connection connection,String tableName){

        List<String> columns = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("DESC " + tableName+ "");// 查询此数据库有多少个表格

            while (resultSet.next()){
                columns.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (resultSet!=null){
                    resultSet.close();
                }

                if (statement!=null){
                    statement.close();
                }

                if (connection!=null){
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return columns;
    }
}
