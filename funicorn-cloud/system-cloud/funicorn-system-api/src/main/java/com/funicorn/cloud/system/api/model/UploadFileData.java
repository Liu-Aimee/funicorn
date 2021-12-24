package com.funicorn.cloud.system.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/11/8 15:27
 */
@Data
public class UploadFileData implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 文件id
     */
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件名后缀名
     */
    private String suffix;

    /**
     * 文件大小 单位字节
     */
    private Long size;

    /**
     * 桶名称
     * */
    private String bucketName;

    /**
     * 文件级别
     * */
    private String fileLevel;

    /**
     * 访问链接
     **/
    private String url;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 是否提供下载
     */
    private Boolean downFlag;

    /**
     * 下载次数
     */
    private Integer downCount;
}
