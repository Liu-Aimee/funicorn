package com.funicorn.cloud.task.center.service.impl;

import com.funicorn.basic.common.base.util.DateUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.task.center.constant.CommentTypeEnum;
import com.funicorn.cloud.task.center.constant.TaskConstant;
import com.funicorn.cloud.task.center.dto.BaseProcessDTO;
import com.funicorn.cloud.task.center.dto.StartProcessDTO;
import com.funicorn.cloud.task.center.dto.TaskCompleteDTO;
import com.funicorn.cloud.task.center.dto.TaskTurnDTO;
import com.funicorn.cloud.task.center.exception.TaskErrorCode;
import com.funicorn.cloud.task.center.exception.TaskException;
import com.funicorn.cloud.task.center.service.BpmnModelService;
import com.funicorn.cloud.task.center.service.CommentService;
import com.funicorn.cloud.task.center.service.ProcessInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Aimee
 * @since 2021/12/9 8:49
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private CommentService commentService;
    @Resource
    private TaskService taskService;
    @Resource
    private BpmnModelService bpmnModelService;

    @Override
    public ProcessInstance startProcessInstanceByKey(StartProcessDTO startProcessDTO) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(startProcessDTO.getProcessDefinitionKey())
                .latestVersion().singleResult();

        if (processDefinition==null) {
            throw new TaskException(TaskErrorCode.PROCESS_NOT_FOUND);
        }

        if (processDefinition.isSuspended()){
            throw new TaskException(TaskErrorCode.PROCESS_DEFINITION_IS_SUSPENDED);
        }

        //加载流程参数
        this.loadStartVariables(startProcessDTO);
        //设置流程发起人
        Authentication.setAuthenticatedUserId(SecurityUtil.getCurrentUser().getUsername());
        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(startProcessDTO.getProcessDefinitionKey().trim())
                .tenantId(SecurityUtil.getCurrentUser().getTenantId().trim());
        //设置流程实例名称
        String dateStr = DateUtil.dateToStr(new Date(),DateUtil.YYYY_MM_DD);
        String processInstanceName = processDefinition.getName() + "-" + SecurityUtil.getCurrentUser().getNickName() + "-" + dateStr;
        processInstanceBuilder.name(processInstanceName);
        //设置表单id
        if (StringUtils.isNotBlank(startProcessDTO.getFormId())){
            processInstanceBuilder.variable(TaskConstant.FORM_ID_KEY,startProcessDTO.getFormId());
        }
        //设置流程参数
        if (startProcessDTO.getVariables()!=null && !startProcessDTO.getVariables().isEmpty()){
            processInstanceBuilder.variables(startProcessDTO.getVariables());
        }
        //设置业务id
        if (StringUtils.isNotBlank(startProcessDTO.getBusinessKey())) {
            processInstanceBuilder.businessKey(startProcessDTO.getBusinessKey());
        }
        ProcessInstance processInstance = processInstanceBuilder.start();
        //添加提交记录
        commentService.addComment(null,SecurityUtil.getCurrentUser().getUsername(),processInstance.getProcessInstanceId(), CommentTypeEnum.TJ,"提交");

        /*
         * 保存流程变量为任务变量
         * 注释：
         * variable都是针对processInstance的。
         * local可能是针对某个execution分支的，也可能针对task的
         * */
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().singleResult();
        if(null != task) {
            Map<String, Object> localVariables = runtimeService.getVariables(processInstance.getProcessInstanceId());
            taskService.setVariablesLocal(task.getId(), localVariables);
            //更新流程启动人
            taskService.setAssignee(task.getId(),SecurityUtil.getCurrentUser().getUsername());
        }

        //TODO 推送消息 邮件或短信

        //这个方法最终使用一个ThreadLocal类型的变量进行存储，启动后需要移除变量，防止内存溢出
        Authentication.setAuthenticatedUserId(null);
        return processInstance;
    }

    @Override
    public void revoke(BaseProcessDTO baseProcessDTO) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(baseProcessDTO.getProcessInstanceId()).singleResult();
        if (processInstance==null){
            throw new TaskException(TaskErrorCode.NOT_FOUND_PROCESS_INSTANCE);
        }
        //1.添加撤回意见
        commentService.addComment(null,SecurityUtil.getCurrentUser().getUsername(), baseProcessDTO.getProcessInstanceId(), CommentTypeEnum.CH, baseProcessDTO.getMessage());
        //2.设置提交人
        runtimeService.setVariable(baseProcessDTO.getProcessInstanceId(), TaskConstant.FLOW_SUBMITTER_VAR, SecurityUtil.getCurrentUser().getUsername());

        //TODO 3.执行撤回
    }

    @Override
    public void stopProcessInstanceById(BaseProcessDTO baseProcessDTO) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(baseProcessDTO.getProcessInstanceId()).singleResult();
        if (processInstance==null){
            throw new TaskException(TaskErrorCode.NOT_FOUND_PROCESS_INSTANCE);
        }
        EndEvent endEvent = bpmnModelService.findEndFlowElement(baseProcessDTO.getProcessInstanceId());
        if (endEvent==null){
            throw new TaskException(TaskErrorCode.NOT_FOUND_END_NODE);
        }
        //2、执行终止
        List<Execution> executions = runtimeService.createExecutionQuery().parentId(baseProcessDTO.getProcessInstanceId()).list();
        List<String> executionIds = new ArrayList<>();
        executions.forEach(execution -> executionIds.add(execution.getId()));
        //移动到结束节点
        runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, endEvent.getId()).changeState();
    }

    @Override
    public void suspendOrActiveProcessInstanceById(String processInstanceId, Integer suspensionState) {
        if (suspensionState == 2) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            log.info("===========>processInstanceId:["+processInstanceId+"]挂起成功<===========");
        } else {
            runtimeService.activateProcessInstanceById(processInstanceId);
            log.info("===========>processInstanceId:["+processInstanceId+"]激活成功<===========");
        }
    }

    @Override
    public boolean isSuspended(String processInstanceId) {
        boolean flag = false;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance != null){
            flag = processInstance.isSuspended();
        }
        return flag;
    }

    @Override
    public void completeTask(TaskCompleteDTO taskCompleteDTO) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskCompleteDTO.getTaskId()).singleResult();
        if (task==null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        String taskId = taskCompleteDTO.getTaskId();
        if (DelegationState.PENDING.equals(task.getDelegationState())){
            Task subTask = this.createSubTask(task,SecurityUtil.getCurrentUser().getUsername());
            taskService.complete(subTask.getId());
            taskId = subTask.getId();
            //审批的是委派任务时，执行resolveTask
            taskService.resolveTask(taskCompleteDTO.getTaskId(), taskCompleteDTO.getVariables());
        } else {
            //修改审批人
            taskService.setAssignee(taskCompleteDTO.getTaskId(),SecurityUtil.getCurrentUser().getUsername());
            if (MapUtils.isNotEmpty(taskCompleteDTO.getVariables())){
                taskService.complete(taskCompleteDTO.getTaskId(), taskCompleteDTO.getVariables());
            } else {
                taskService.complete(taskCompleteDTO.getTaskId());
            }
        }

        //添加审批意见
        commentService.addComment(taskId,SecurityUtil.getCurrentUser().getUsername(),task.getProcessInstanceId(),CommentTypeEnum.SP,taskCompleteDTO.getMessage());

    }

    @Override
    public void turnTask(TaskTurnDTO taskTurnDTO) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskTurnDTO.getTaskId()).singleResult();
        if (task==null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        //设置转办人
        taskService.setAssignee(taskTurnDTO.getTaskId(),taskTurnDTO.getTurnUsername());
        //设置委派人
        taskService.setOwner(taskTurnDTO.getTaskId(),SecurityUtil.getCurrentUser().getUsername());
        //添加转办记录
        commentService.addComment(task.getId(),SecurityUtil.getCurrentUser().getUsername(),task.getProcessInstanceId(),CommentTypeEnum.ZB,taskTurnDTO.getMessage());
    }

    @Override
    public void delegateTask(TaskTurnDTO taskTurnDTO) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskTurnDTO.getTaskId()).singleResult();
        if (task==null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        //将任务进行委派
        taskService.delegateTask(taskTurnDTO.getTaskId(),taskTurnDTO.getTurnUsername());
        //设置委派人
        taskService.setOwner(taskTurnDTO.getTaskId(),SecurityUtil.getCurrentUser().getUsername());
        //添加委派记录
        commentService.addComment(task.getId(),SecurityUtil.getCurrentUser().getUsername(),task.getProcessInstanceId(),CommentTypeEnum.WP,taskTurnDTO.getMessage());
    }

    /**
     * 创建任务
     * @param assignee       任务的执行者
     * @param parentTask    父亲任务实体
     * @return Task
     */
    private Task createSubTask(TaskEntity parentTask, String assignee) {
        TaskEntity task = null;
        if (parentTask != null){
            task = (TaskEntity) taskService.newTask(UUID.randomUUID().toString().replaceAll("-",""));
            task.setAssignee(StringUtils.isNotBlank(assignee) ? assignee : SecurityUtil.getCurrentUser().getUsername());
            task.setProcessInstanceId(parentTask.getProcessInstanceId());
            task.setProcessDefinitionId(parentTask.getProcessDefinitionId());
            task.setParentTaskId(parentTask.getParentTaskId());
            task.setCategory(parentTask.getCategory());
            task.setDescription(parentTask.getDescription());
            task.setName(parentTask.getName());
            task.setTaskDefinitionKey(parentTask.getTaskDefinitionKey());
            task.setTaskDefinitionId(parentTask.getTaskDefinitionId());
            task.setTenantId(parentTask.getTenantId());
            task.setCreateTime(new Date());
            task.setPriority(parentTask.getPriority());
            taskService.saveTask(task);
        }
        return task;
    }

    /**
     * 加载流程参数
     * @param startProcessDTO 原始的流程参数
     * TODO 需要完善
     * */
    private void loadStartVariables(StartProcessDTO startProcessDTO){
        //设置提交人字段为空字符串让其自动跳过审批步骤
        startProcessDTO.getVariables().put(TaskConstant.FLOW_SUBMITTER_VAR, "");
        //设置允许自动跳过
        startProcessDTO.getVariables().put(TaskConstant.FLOWABLE_SKIP_EXPRESSION_ENABLED, true);
    }
}
