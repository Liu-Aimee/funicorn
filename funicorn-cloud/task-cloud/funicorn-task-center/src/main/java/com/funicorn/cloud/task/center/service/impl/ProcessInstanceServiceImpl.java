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

        //??????????????????
        this.loadStartVariables(startProcessDTO);
        //?????????????????????
        Authentication.setAuthenticatedUserId(SecurityUtil.getCurrentUser().getUsername());
        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(startProcessDTO.getProcessDefinitionKey().trim())
                .tenantId(SecurityUtil.getCurrentUser().getTenantId().trim());
        //????????????????????????
        String dateStr = DateUtil.dateToStr(new Date(),DateUtil.YYYY_MM_DD);
        String processInstanceName = processDefinition.getName() + "-" + SecurityUtil.getCurrentUser().getNickName() + "-" + dateStr;
        processInstanceBuilder.name(processInstanceName);
        //????????????id
        if (StringUtils.isNotBlank(startProcessDTO.getFormId())){
            processInstanceBuilder.variable(TaskConstant.FORM_ID_KEY,startProcessDTO.getFormId());
        }
        //??????????????????
        if (startProcessDTO.getVariables()!=null && !startProcessDTO.getVariables().isEmpty()){
            processInstanceBuilder.variables(startProcessDTO.getVariables());
        }
        //????????????id
        if (StringUtils.isNotBlank(startProcessDTO.getBusinessKey())) {
            processInstanceBuilder.businessKey(startProcessDTO.getBusinessKey());
        }
        ProcessInstance processInstance = processInstanceBuilder.start();
        //??????????????????
        commentService.addComment(null,SecurityUtil.getCurrentUser().getUsername(),processInstance.getProcessInstanceId(), CommentTypeEnum.TJ,"??????");

        /*
         * ?????????????????????????????????
         * ?????????
         * variable????????????processInstance??????
         * local?????????????????????execution???????????????????????????task???
         * */
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).active().singleResult();
        if(null != task) {
            Map<String, Object> localVariables = runtimeService.getVariables(processInstance.getProcessInstanceId());
            taskService.setVariablesLocal(task.getId(), localVariables);
            //?????????????????????
            taskService.setAssignee(task.getId(),SecurityUtil.getCurrentUser().getUsername());
        }

        //TODO ???????????? ???????????????

        //??????????????????????????????ThreadLocal??????????????????????????????????????????????????????????????????????????????
        Authentication.setAuthenticatedUserId(null);
        return processInstance;
    }

    @Override
    public void revoke(BaseProcessDTO baseProcessDTO) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(baseProcessDTO.getProcessInstanceId()).singleResult();
        if (processInstance==null){
            throw new TaskException(TaskErrorCode.NOT_FOUND_PROCESS_INSTANCE);
        }
        //1.??????????????????
        commentService.addComment(null,SecurityUtil.getCurrentUser().getUsername(), baseProcessDTO.getProcessInstanceId(), CommentTypeEnum.CH, baseProcessDTO.getMessage());
        //2.???????????????
        runtimeService.setVariable(baseProcessDTO.getProcessInstanceId(), TaskConstant.FLOW_SUBMITTER_VAR, SecurityUtil.getCurrentUser().getUsername());

        //TODO 3.????????????
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
        //2???????????????
        List<Execution> executions = runtimeService.createExecutionQuery().parentId(baseProcessDTO.getProcessInstanceId()).list();
        List<String> executionIds = new ArrayList<>();
        executions.forEach(execution -> executionIds.add(execution.getId()));
        //?????????????????????
        runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, endEvent.getId()).changeState();
    }

    @Override
    public void suspendOrActiveProcessInstanceById(String processInstanceId, Integer suspensionState) {
        if (suspensionState == 2) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            log.info("===========>processInstanceId:["+processInstanceId+"]????????????<===========");
        } else {
            runtimeService.activateProcessInstanceById(processInstanceId);
            log.info("===========>processInstanceId:["+processInstanceId+"]????????????<===========");
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
            //????????????????????????????????????resolveTask
            taskService.resolveTask(taskCompleteDTO.getTaskId(), taskCompleteDTO.getVariables());
        } else {
            //???????????????
            taskService.setAssignee(taskCompleteDTO.getTaskId(),SecurityUtil.getCurrentUser().getUsername());
            if (MapUtils.isNotEmpty(taskCompleteDTO.getVariables())){
                taskService.complete(taskCompleteDTO.getTaskId(), taskCompleteDTO.getVariables());
            } else {
                taskService.complete(taskCompleteDTO.getTaskId());
            }
        }

        //??????????????????
        commentService.addComment(taskId,SecurityUtil.getCurrentUser().getUsername(),task.getProcessInstanceId(),CommentTypeEnum.SP,taskCompleteDTO.getMessage());

    }

    @Override
    public void turnTask(TaskTurnDTO taskTurnDTO) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskTurnDTO.getTaskId()).singleResult();
        if (task==null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        //???????????????
        taskService.setAssignee(taskTurnDTO.getTaskId(),taskTurnDTO.getTurnUsername());
        //???????????????
        taskService.setOwner(taskTurnDTO.getTaskId(),SecurityUtil.getCurrentUser().getUsername());
        //??????????????????
        commentService.addComment(task.getId(),SecurityUtil.getCurrentUser().getUsername(),task.getProcessInstanceId(),CommentTypeEnum.ZB,taskTurnDTO.getMessage());
    }

    @Override
    public void delegateTask(TaskTurnDTO taskTurnDTO) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskTurnDTO.getTaskId()).singleResult();
        if (task==null) {
            throw new TaskException(TaskErrorCode.TASK_NOT_FOUND);
        }
        //?????????????????????
        taskService.delegateTask(taskTurnDTO.getTaskId(),taskTurnDTO.getTurnUsername());
        //???????????????
        taskService.setOwner(taskTurnDTO.getTaskId(),SecurityUtil.getCurrentUser().getUsername());
        //??????????????????
        commentService.addComment(task.getId(),SecurityUtil.getCurrentUser().getUsername(),task.getProcessInstanceId(),CommentTypeEnum.WP,taskTurnDTO.getMessage());
    }

    /**
     * ????????????
     * @param assignee       ??????????????????
     * @param parentTask    ??????????????????
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
     * ??????????????????
     * @param startProcessDTO ?????????????????????
     * TODO ????????????
     * */
    private void loadStartVariables(StartProcessDTO startProcessDTO){
        //??????????????????????????????????????????????????????????????????
        startProcessDTO.getVariables().put(TaskConstant.FLOW_SUBMITTER_VAR, "");
        //????????????????????????
        startProcessDTO.getVariables().put(TaskConstant.FLOWABLE_SKIP_EXPRESSION_ENABLED, true);
    }
}
