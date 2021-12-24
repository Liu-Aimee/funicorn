package com.funicorn.cloud.task.center.controller;

import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.task.center.dto.BaseProcessDTO;
import com.funicorn.cloud.task.center.dto.StartProcessDTO;
import com.funicorn.cloud.task.center.exception.TaskErrorCode;
import com.funicorn.cloud.task.center.exception.TaskException;
import com.funicorn.cloud.task.center.service.ProcessInstanceService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 流程实例管理
 * @author Aimme
 * @since 2020/12/18 17:37
 */
@RestController
@RequestMapping("/ProcessInstance")
public class ProcessInstanceController {

    @Resource
    private ProcessInstanceService processInstanceService;

    /**
     * 启动流程
     * @param startProcessDTO 流程实例对象
     * @return Result
     */
    @PostMapping("/start")
    public Result<?> start(@RequestBody @Validated StartProcessDTO startProcessDTO) {
        ProcessInstance processInstance = processInstanceService.startProcessInstanceByKey(startProcessDTO);
        return Result.ok(processInstance.getId());
    }

    /**
     * 终止流程
     * @param baseProcessDTO 参数
     * @return Result
     */
    @PostMapping(value = "/stop")
    public Result<?> stop(@RequestBody @Validated BaseProcessDTO baseProcessDTO) {
        boolean flag = processInstanceService.isSuspended(baseProcessDTO.getProcessInstanceId());
        if (flag){
            baseProcessDTO.setMessage("后台执行终止");
            processInstanceService.stopProcessInstanceById(baseProcessDTO);
        }else{
            throw new TaskException(TaskErrorCode.PROCESS_DEFINITION_IS_SUSPENDED);
        }
        return Result.ok();
    }

    /**
     * 发起人撤回整个流程(开发中)
     * @param baseProcessDTO 参数
     */
    @PostMapping(value = "/revoke")
    public Result<?> revoke(@RequestBody BaseProcessDTO baseProcessDTO) {
        processInstanceService.revoke(baseProcessDTO);
        return Result.ok();
    }

    /**
     * 激活或者挂起流程
     * @param processInstanceId 流程实例id
     * @param state  1激活 2挂起
     * @return Result
     */
    @PostMapping(value = "/saProcessInstanceId")
    public Result<?> saDefinitionById(@RequestParam(value = "processInstanceId")String processInstanceId,@RequestParam(value = "state")int state) {
        processInstanceService.suspendOrActiveProcessInstanceById(processInstanceId,state);
        return Result.ok();
    }
}
