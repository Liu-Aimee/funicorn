package com.funicorn.cloud.task.center.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.task.center.dto.TaskCompleteDTO;
import com.funicorn.cloud.task.center.dto.TaskPageDTO;
import com.funicorn.cloud.task.center.dto.TaskTurnDTO;
import com.funicorn.cloud.task.center.service.ActHiTaskinstService;
import com.funicorn.cloud.task.center.service.ActRuTaskService;
import com.funicorn.cloud.task.center.service.ProcessInstanceService;
import com.funicorn.cloud.task.center.vo.ProcessInstanceVO;
import com.funicorn.cloud.task.center.vo.TaskVO;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 任务管理
 * @author Aimee
 * @since 2021/12/9 17:03
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    private HistoryService historyService;
    @Resource
    private ActRuTaskService actRuTaskService;
    @Resource
    private ProcessInstanceService processInstanceService;
    @Resource
    private ActHiTaskinstService actHiTaskinstService;

    /**
     * 获取当前用户发起的流程
     * @return HistoricProcessInstance
     */
    @GetMapping("/getStartedTask")
    public Result<IPage<ProcessInstanceVO>> getStartedTask(TaskPageDTO taskPageDTO) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().startedBy(SecurityUtil.getCurrentUser().getUsername());
        if (taskPageDTO.getStartTime()!=null) {
            historicProcessInstanceQuery.startedBefore(taskPageDTO.getEndTime());
        }
        if (taskPageDTO.getEndTime()!=null) {
            historicProcessInstanceQuery.startedAfter(taskPageDTO.getStartTime());
        }
        historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        //总条数
        long total = historicProcessInstanceQuery.count();
        List<HistoricProcessInstance> tasks = historicProcessInstanceQuery.listPage((int)taskPageDTO.getCurrent() - 1,(int)taskPageDTO.getSize());
        IPage<HistoricProcessInstance> iPage = new Page<>(taskPageDTO.getCurrent(), taskPageDTO.getSize(),total);
        iPage.setRecords(tasks);
        return Result.ok(ConvertUtil.page2Page(iPage,ProcessInstanceVO.class));
    }

    /**
     * 获取当前用户待办任务
     * @return HistoricProcessInstance
     */
    @GetMapping("/getToDoTask")
    public Result<IPage<TaskVO>> getToDoTask(TaskPageDTO taskPageDTO) {
        return Result.ok(actRuTaskService.getTodoTaskPage(taskPageDTO));
    }

    /**
     * 获取当前用户已办任务
     * @return HistoricProcessInstance
     */
    @GetMapping("/getDoneTask")
    public Result<IPage<TaskVO>> getDoneTask(TaskPageDTO taskPageDTO) {
        return Result.ok(actHiTaskinstService.getDoneTaskPage(taskPageDTO));
    }

    /**
     * 审批任务
     * @param taskCompleteDTO 入参
     * @return Result
     * */
    @PostMapping("/complete")
    public Result<?> complete(@RequestBody TaskCompleteDTO taskCompleteDTO) {
        processInstanceService.completeTask(taskCompleteDTO);
        return Result.ok();
    }

    /**
     * 转办任务
     * @param taskTurnDTO 入参
     * @return Result
     * */
    @PostMapping("/turn")
    public Result<?> turn(@RequestBody TaskTurnDTO taskTurnDTO) {
        processInstanceService.turnTask(taskTurnDTO);
        return Result.ok();
    }

    /**
     * 委派任务
     * @param taskTurnDTO 入参
     * @return Result
     * */
    @PostMapping("/delegate")
    public Result<?> delegate(@RequestBody TaskTurnDTO taskTurnDTO) {
        processInstanceService.delegateTask(taskTurnDTO);
        return Result.ok();
    }
}
