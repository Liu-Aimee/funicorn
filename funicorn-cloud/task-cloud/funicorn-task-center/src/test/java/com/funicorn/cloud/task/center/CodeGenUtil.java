package com.funicorn.cloud.task.center;

import com.funicorn.basic.common.datasource.util.CodeGenerator;

/**
 * @author Aimee
 * @since 2021/10/28 17:20
 */
public class CodeGenUtil {

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.setAuthor("Aimee");
        generator.setJdbcUrl("jdbc:mysql://192.168.140.140:13306/funicorn_task?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        generator.setUsername("root");
        generator.setPassword("123456");
        generator.setProjectPath(System.getProperty("user.dir") + "/funicorn-cloud/task-cloud/funicorn-task-center");
        generator.setDriverClassName("com.mysql.cj.jdbc.Driver");
        generator.setPackageName("com.funicorn.cloud.task.center");
        generator.generateCode("act_hi_taskinst");
    }
}
