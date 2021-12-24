package com.funicorn.cloud.task.center.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aimee
 * @since 2021/12/9 8:40
 */
@Data
public class StartProcessDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 流程定义唯一key
     * */
    @NotBlank(message = "流程定义唯一key不能为空")
    private String processDefinitionKey;

    /**
     * 业务系统id 必填
     */
    private String businessKey;

    /**
     * 表单id
     * TODO 暂时缺少表单系统
     * */
    private String formId;

    /**
     * 流程参数
     * */
    private Map<String,Object> variables = new HashMap<>();
}
