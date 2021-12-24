package com.funicorn.cloud.task.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.task.center.dto.TaskPageDTO;
import com.funicorn.cloud.task.center.entity.ActHiTaskinst;
import com.funicorn.cloud.task.center.mapper.ActHiTaskinstMapper;
import com.funicorn.cloud.task.center.service.ActHiTaskinstService;
import com.funicorn.cloud.task.center.vo.TaskVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-12-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActHiTaskinstServiceImpl extends ServiceImpl<ActHiTaskinstMapper, ActHiTaskinst> implements ActHiTaskinstService {

    @Override
    public IPage<TaskVO> getDoneTaskPage(TaskPageDTO taskPageDTO) {
        if (StringUtils.isBlank(taskPageDTO.getAssignee())) {
            taskPageDTO.setAssignee(SecurityUtil.getCurrentUser().getUsername());
        }
        return baseMapper.queryTaskByAssignee(new Page<>(taskPageDTO.getCurrent(),taskPageDTO.getSize()),taskPageDTO.getAssignee());
    }
}
