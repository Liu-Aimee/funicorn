package com.funicorn.cloud.task.center.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.flowable.ui.common.tenant.TenantProvider;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.flowable.ui.modeler.service.ModelImageService;
import org.flowable.ui.modeler.service.ModelServiceImpl;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aimee
 * @since 2021/12/10 16:56
 */
@ComponentScan(value = {
        "org.flowable.ui.modeler.repository",
        "org.flowable.ui.modeler.service",
        "org.flowable.ui.common.tenant",
        "org.flowable.ui.common.repository"})
@Configuration
public class FlowableConfiguration {

    @Bean
    @Qualifier("flowableModeler")
    public SqlSessionTemplate modelerSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public ModelService createModelService() {
        return new ModelServiceImpl();
    }

    @Bean
    public ModelImageService createModelImageService() {
        return new ModelImageService();
    }

    @Bean
    public FlowableModelQueryService createFlowableModelQueryService() {
        return new FlowableModelQueryService();
    }

    @Bean
    public TenantProvider tenantProvider () {
        return new CustomizeTenantProvider();
    }
}
