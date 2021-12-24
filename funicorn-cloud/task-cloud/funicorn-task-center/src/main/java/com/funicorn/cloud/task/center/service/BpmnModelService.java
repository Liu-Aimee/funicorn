package com.funicorn.cloud.task.center.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.task.center.entity.ActDeModel;
import org.flowable.bpmn.model.EndEvent;

/**
 * @author Aimee
 * @since 2021/12/9 16:34
 */
public interface BpmnModelService {

    /**
     * 获取end节点
     * @param processDefId 流程定义id
     * @return EndEvent
            * */
    EndEvent findEndFlowElement(String processDefId);

    /**
     * 分页查询流程模型
     * @param page 分页条件
     * @param queryWrapper 查询条件
     * @return ActDeModel
     * */
    IPage<ActDeModel> modelPage(Page<ActDeModel> page, Wrapper<ActDeModel> queryWrapper);
}
