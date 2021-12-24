package com.funicorn.cloud.task.center.service;

import com.funicorn.cloud.task.center.constant.CommentTypeEnum;
import com.funicorn.cloud.task.center.dto.CommentDTO;

/**
 * @author Aimme
 * @date 2020/12/19 12:09
 */
public interface CommentService {

    /**
     * 添加审批记录
     * @param commentDTO 参数
     * */
    void addComment(CommentDTO commentDTO);

    /**
     * 添加审批意见
     *
     * @param taskId            任务id
     * @param userName          用户名
     * @param processInstanceId 流程实例id
     * @param type              审批类型
     * @param message           审批意见
     */
    void addComment(String taskId, String userName, String processInstanceId, CommentTypeEnum type, String message);
}
