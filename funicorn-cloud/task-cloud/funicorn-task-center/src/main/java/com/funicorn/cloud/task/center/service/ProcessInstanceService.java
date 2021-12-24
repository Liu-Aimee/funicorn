package com.funicorn.cloud.task.center.service;

import com.funicorn.cloud.task.center.dto.BaseProcessDTO;
import com.funicorn.cloud.task.center.dto.StartProcessDTO;
import com.funicorn.cloud.task.center.dto.TaskCompleteDTO;
import com.funicorn.cloud.task.center.dto.TaskTurnDTO;
import org.flowable.engine.runtime.ProcessInstance;

/**
 * @author Aimme
 * @date 2020/12/18 17:38
 */
public interface ProcessInstanceService {

    /**
     * 启动流程
     *
     * @param startProcessDTO 实例参数
     * @return ProcessInstance
     */
    ProcessInstance startProcessInstanceByKey(StartProcessDTO startProcessDTO);

    /**
     * 撤回流程
     * @param baseProcessDTO 参数
     */
    void revoke(BaseProcessDTO baseProcessDTO);

    /**
     * 终止流程
     * @param baseProcessDTO 入参
     * */
    void stopProcessInstanceById(BaseProcessDTO baseProcessDTO);

    /**
     * 激活或挂起流程实例
     *
     * @param processInstanceId 流程实例id
     * @param suspensionState   1激活 2挂起
     */
    void suspendOrActiveProcessInstanceById(String processInstanceId, Integer suspensionState);

    /**
     * 校验流程是否挂起状态
     * @param processInstanceId 流程实例id
     * @return boolean
     * */
    boolean isSuspended(String processInstanceId);

    /**
     * 审批
     * @param taskCompleteDTO 入参
     * */
    void completeTask(TaskCompleteDTO taskCompleteDTO);

    /**
     * 转办
     * @param taskTurnDTO 入参
     * */
    void turnTask(TaskTurnDTO taskTurnDTO);

    /**
     * 委派
     * @param taskTurnDTO 入参
     * */
    void delegateTask(TaskTurnDTO taskTurnDTO);
}
