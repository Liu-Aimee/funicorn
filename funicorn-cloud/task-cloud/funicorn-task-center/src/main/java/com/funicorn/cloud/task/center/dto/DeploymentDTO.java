package com.funicorn.cloud.task.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Aimee
 * @since 2021/12/10 15:30
 */
@Data
public class DeploymentDTO {

    /**
     * 模型id
     * */
    @NotBlank(message = "模型id不能为空",groups = Insert.class)
    private String modelId;

    /**
     * 部署id
     * */
    private String deploymentId;

    /**
     * 部署唯一标识
     * */
    private String deploymentKey;

    /**
     * 部署名称
     * */
    private String deploymentName;

    /**
     * 部署类别
     * */
    private String deploymentCategory;
}
