package com.funicorn.cloud.task.center.service;

import com.funicorn.cloud.task.center.dto.DeploymentDTO;
import org.flowable.engine.repository.Deployment;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aimme
 * @date 2020/12/14 15:53
 */
public interface ProcessDefinitionService {

    /**
     * 导入流程定义模板
     * @param file 模板文件
     * @throws Exception 异常
     * */
    void importBpmnModel(MultipartFile file) throws Exception;

    /**
     * 流程部署
     * @param deploymentDTO 入参
     * @return Deployment
     * */
    Deployment deploy(DeploymentDTO deploymentDTO);

    /**
     * 流程发布
     * @param modelId 模型id
     * @return Deployment
     * */
    Deployment publish(String modelId);

    /**
     * 挂起流程定义
     *
     * @param processDefinitionId 流程定义id
     * @param suspensionState     状态 1激活 2挂起
     */
    void suspendOrActiveProcessDefinitionById(String processDefinitionId, int suspensionState);
}
