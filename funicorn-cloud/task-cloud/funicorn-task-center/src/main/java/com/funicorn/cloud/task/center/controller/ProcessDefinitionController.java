package com.funicorn.cloud.task.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.task.center.dto.DeploymentDTO;
import com.funicorn.cloud.task.center.dto.ImageDTO;
import com.funicorn.cloud.task.center.entity.ActReDeployment;
import com.funicorn.cloud.task.center.service.DeploymentService;
import com.funicorn.cloud.task.center.service.ProcessDefinitionService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程定义管理
 * @author Aimee
 * @since 2021/12/10 11:27
 */
@RestController
@RequestMapping("/ProcessDefinition")
public class ProcessDefinitionController {

    @Resource
    private ProcessDefinitionService processDefinitionService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private DeploymentService deploymentService;
    @Resource
    private HistoryService historyService;

    /**
     * 查询已部署的流程
     * @return Result
     * */
    @GetMapping("/deploymentList")
    public Result<List<ActReDeployment>> deploymentList(DeploymentDTO deploymentDTO){
        LambdaQueryWrapper<ActReDeployment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActReDeployment::getTenantId, SecurityUtil.getCurrentUser().getTenantId());
        if (StringUtils.isNotBlank(deploymentDTO.getDeploymentId())) {
            queryWrapper.eq(ActReDeployment::getId,deploymentDTO.getDeploymentId());
        }
        if (StringUtils.isNotBlank(deploymentDTO.getDeploymentName())) {
            queryWrapper.like(ActReDeployment::getName,deploymentDTO.getDeploymentName());
        }
        if (StringUtils.isNotBlank(deploymentDTO.getDeploymentKey())) {
            queryWrapper.eq(ActReDeployment::getKey,deploymentDTO.getDeploymentKey());
        }
        if (StringUtils.isNotBlank(deploymentDTO.getDeploymentCategory())) {
            queryWrapper.eq(ActReDeployment::getCategory,deploymentDTO.getDeploymentCategory());
        }
        queryWrapper.orderByDesc(ActReDeployment::getDeployTime);
        return Result.ok(deploymentService.list(queryWrapper));
    }

    /**
     *
     * 流程部署
     * @param deploymentDTO 入参
     * @return Result<?>
     * */
    @PostMapping("/deploy")
    public Result<?> deploy(@RequestBody @Validated(Insert.class) DeploymentDTO deploymentDTO){
        processDefinitionService.deploy(deploymentDTO);
        return Result.ok();
    }

    /**
     * 删除流程定义
     * @param deploymentId 部署id
     * @return Result
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return Result.ok();
    }

    /**
     * 获取流程实例图片
     * @param deploymentId 流程部署id
     * */
    @GetMapping("/processImage")
    public void processImage(@RequestParam String deploymentId, HttpServletResponse response) throws IOException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        if (processDefinition==null) {
            return;
        }
        response.setHeader("Content-Type", "image/png");
        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId,processDefinition.getDiagramResourceName());
        response.getOutputStream().write(IoUtil.readInputStream(inputStream, "image inputStream name"));
    }

    /**
     * 获取高亮节点流程图
     * @param imageDTO 入参
     * */
    @GetMapping("/processHighlightImage")
    public void taskImage(ImageDTO imageDTO, HttpServletResponse response) throws IOException {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(imageDTO.getProcessDefinitionId());
        // 创建历史活动实例查询
        // 执行流程实例id
        List<HistoricActivityInstance> historyProcess = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(imageDTO.getProcessInstanceId()).finished().list();

        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        //获取流程图
        for (HistoricActivityInstance hi : historyProcess) {
            String activityType = hi.getActivityType();
            if ("sequenceFlow".equals(activityType) || "exclusiveGateway".equals(activityType)) {
                flows.add(hi.getActivityId());
            } else if ("userTask".equals(activityType) || "startEvent".equals(activityType)) {
                activityIds.add(hi.getActivityId());
            }
        }

        activityIds.add(imageDTO.getProcessDefinitionKey());
        ProcessEngineConfiguration engConf = ProcessEngines.getDefaultProcessEngine().getProcessEngineConfiguration();
        //定义流程画布生成器
        ProcessDiagramGenerator processDiagramGenerator = engConf.getProcessDiagramGenerator();
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engConf.getActivityFontName(), engConf.getLabelFontName(), engConf.getAnnotationFontName(), engConf.getClassLoader(), 1.0, true);
        response.setHeader("Content-Type", "image/png");
        response.getOutputStream().write(IoUtil.readInputStream(inputStream, "image inputStream name"));
    }
}
