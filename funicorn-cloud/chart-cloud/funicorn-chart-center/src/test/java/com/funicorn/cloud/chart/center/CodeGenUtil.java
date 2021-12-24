package com.funicorn.cloud.chart.center;

import com.funicorn.basic.common.datasource.util.CodeGenerator;

/**
 * @author Aimee
 * @since 2021/10/28 17:20
 */
public class CodeGenUtil {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.setAuthor("Aimee");
        generator.setJdbcUrl("jdbc:mysql://192.168.140.140:3306/funicorn_chart?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        generator.setUsername("root");
        generator.setPassword("123456");
        generator.setProjectPath(System.getProperty("user.dir") + "/funicorn-cloud/task-cloud/funicorn-chart-center");
        generator.setDriverClassName("com.mysql.cj.jdbc.Driver");
        generator.setPackageName("com.funicorn.cloud.chart.center");
        generator.generateCode("dict_type","dict_item","route_config");
    }
}
