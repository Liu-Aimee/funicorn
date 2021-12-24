package com.funicorn.cloud.task.center.dto;

import com.funicorn.cloud.task.center.constant.CommentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Aimme
 * @date 2020/12/19 11:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    /**
     * 任务id
     */
    protected String taskId;
    /**
     * 添加人
     */
    protected String userId;
    /**
     * 用户的名称
     */
    protected String userName;
    /**
     * 用户的头像链接
     */
    protected String userUrl;
    /**
     * 流程实例id
     */
    protected String processInstanceId;
    /**
     * 意见信息
     */
    protected String message;
    /**
     * 时间
     */
    protected Date time;
    /**
     *  审批类型
     */
    private CommentTypeEnum type;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 评论全信息
     */
    private String fullMsg;
}
