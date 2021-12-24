package com.funicorn.cloud.task.center.service.impl;

import com.funicorn.cloud.task.center.command.AddHisCommentCmd;
import com.funicorn.cloud.task.center.constant.CommentTypeEnum;
import com.funicorn.cloud.task.center.dto.CommentDTO;
import com.funicorn.cloud.task.center.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.ManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Aimme
 * @date 2020/12/19 12:11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    @Resource
    private ManagementService managementService;

    @Override
    public void addComment(CommentDTO commentDTO) {

        managementService.executeCommand(new AddHisCommentCmd(commentDTO.getTaskId(), commentDTO.getUserName(), commentDTO.getProcessInstanceId(),
                commentDTO.getType().name(), commentDTO.getMessage()));
    }

    @Override
    public void addComment(String taskId,String username, String processInstanceId, CommentTypeEnum type, String message) {
        if (StringUtils.isBlank(message)) {
            message = type.getName();
        }

        //1.添加备注
        CommentDTO commentDTO = CommentDTO.builder().taskId(taskId)
                .userName(username)
                .processInstanceId(processInstanceId)
                .type(type)
                .message(message)
                .build();
        this.addComment(commentDTO);
    }
}
