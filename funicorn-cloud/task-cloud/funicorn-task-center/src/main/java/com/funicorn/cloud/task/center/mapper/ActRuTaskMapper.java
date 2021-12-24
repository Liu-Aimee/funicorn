package com.funicorn.cloud.task.center.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.task.center.entity.ActRuTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.cloud.task.center.vo.TaskVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-12-13
 */
public interface ActRuTaskMapper extends BaseMapper<ActRuTask> {

    /**
     * 查询用户待办任务
     * @param assignee 用户
     * @param page 分页条件
     * @return ActRuTask
     * */
    IPage<TaskVO> queryTaskByAssignee(Page<ActRuTask> page, String assignee);
}
