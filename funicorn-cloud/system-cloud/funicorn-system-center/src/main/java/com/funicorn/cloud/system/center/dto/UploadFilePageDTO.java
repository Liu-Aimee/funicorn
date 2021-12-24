package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/11/8 16:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UploadFilePageDTO extends PageDTO {
    /**
     * 文件名
     */
    private String fileName;


    /**
     * 文件名后缀名
     */
    private String suffix;

    /**
     * 桶名称
     * */
    private String bucketName;
}
