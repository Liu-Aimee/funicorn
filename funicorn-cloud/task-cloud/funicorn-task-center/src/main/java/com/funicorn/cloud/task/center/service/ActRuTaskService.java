package com.funicorn.cloud.task.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.task.center.dto.TaskPageDTO;
import com.funicorn.cloud.task.center.entity.ActRuTask;
import com.funicorn.cloud.task.center.vo.TaskVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-12-13
 */
public interface ActRuTaskService extends IService<ActRuTask> {

    /**
     * 查询用户待办任务
     * @param taskPageDTO 查询条件
     * @return TaskVO
     * */
    IPage<TaskVO> getTodoTaskPage(TaskPageDTO taskPageDTO);
}
