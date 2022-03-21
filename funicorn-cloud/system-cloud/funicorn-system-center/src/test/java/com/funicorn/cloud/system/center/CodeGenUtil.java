package com.funicorn.cloud.system.center;

import com.funicorn.basic.common.datasource.util.CodeGenerator;

/**
 * @author Aimee
 * @since 2021/10/28 17:20
 */
public class CodeGenUtil {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.setAuthor("Aimee");
        generator.setJdbcUrl("jdbc:mysql://192.168.140.140:3306/funicorn_system?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        generator.setUsername("root");
        generator.setPassword("123456");
        generator.setProjectPath(System.getProperty("user.dir") + "/funicorn-cloud/system-cloud/funicorn-system-center");
        generator.setDriverClassName("com.mysql.cj.jdbc.Driver");
        generator.setPackageName("com.funicorn.cloud.system.center");
        generator.generateCode("bucket_config");
    }
}
