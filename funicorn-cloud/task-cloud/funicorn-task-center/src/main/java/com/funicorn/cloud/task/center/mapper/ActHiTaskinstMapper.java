package com.funicorn.cloud.task.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.task.center.entity.ActHiTaskinst;
import com.funicorn.cloud.task.center.vo.TaskVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-12-14
 */
public interface ActHiTaskinstMapper extends BaseMapper<ActHiTaskinst> {

    /**
     * 查询用户已办任务
     * @param assignee 用户
     * @param page 分页条件
     * @return TaskVO
     * */
    IPage<TaskVO> queryTaskByAssignee(Page<ActHiTaskinst> page, String assignee);
}
