package com.funicorn.basic.cloud.security;

import com.funicorn.basic.common.datasource.util.CodeGenerator;

/**
 * @author Aimee
 * @since 2021/10/29 10:01
 */
public class CodeGenUtil {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.setAuthor("Aimee");
        generator.setJdbcUrl("jdbc:mysql://192.168.140.140:3306/funicorn_upms?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        generator.setUsername("root");
        generator.setPassword("123456");
        generator.setProjectPath(System.getProperty("user.dir") + "/basic-cloud/basic-cloud-security");
        generator.setDriverClassName("com.mysql.cj.jdbc.Driver");
        generator.setPackageName("com.funicorn.basic.cloud.security");
        generator.generateCode("role","tenant","menu","user_tenant");
    }
}
