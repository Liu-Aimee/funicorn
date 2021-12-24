package com.funicorn.cloud.task.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.task.center.entity.ActDeModel;
import com.funicorn.cloud.task.center.mapper.ActDeModelMapper;
import com.funicorn.cloud.task.center.service.BpmnModelService;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/12/9 16:34
 */
@Service
public class BpmnModelServiceImpl implements BpmnModelService {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ActDeModelMapper actDeModelMapper;

    @Override
    public EndEvent findEndFlowElement(String processDefId) {
        Process mainProcess = repositoryService.getBpmnModel(processDefId).getMainProcess();
        if (mainProcess!=null){
            List<EndEvent> endEvents = mainProcess.findFlowElementsOfType(EndEvent.class);
            if (endEvents==null || endEvents.isEmpty()) {
                return null;
            }
            return endEvents.get(0);
        }else {
            return null;
        }
    }

    @Override
    public IPage<ActDeModel> modelPage(Page<ActDeModel> page, Wrapper<ActDeModel> queryWrapper) {
        return actDeModelMapper.selectPage(page,queryWrapper);
    }
}
