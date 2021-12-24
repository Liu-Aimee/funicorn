package com.funicorn.cloud.system.center.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Aimee
 * @since 2021/11/10 11:08
 */
@Data
public class UploadFileDTO {

    /**
     * 文件id
     * */
    @NotBlank(message = "文件id不能为空")
    private String fileId;

    /**
     * 是否允许下载 true/false
     * */
    @NotNull(message = "未设置是否允许下载")
    private Boolean downFlag;
}
