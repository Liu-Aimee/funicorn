package com.funicorn.cloud.system.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.system.api.model.UploadFileData;
import com.funicorn.cloud.system.center.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 文件上传信息 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-11-08
 */
public interface UploadFileService extends IService<UploadFile> {

    /**
     * 单文件上传
     * @param multipartFile 文件流
     * @param bucketName 桶名称
     * @param fileLevel 文件级别
     * @param downFlag 是否允许其他人下载 默认false
     * @return UploadFileData
     * @throws Exception 异常
     * */
    UploadFileData upload(MultipartFile multipartFile, String bucketName, boolean downFlag, String fileLevel) throws Exception;

    /**
     * 单文件上传
     * @param files 文件流数组
     * @param bucketName 桶名称
     * @param fileLevel 文件级别
     * @param downFlag 是否允许其他人下载 默认false
     * @return UploadFileData
     * @throws Exception 异常
     * */
    List<UploadFileData> multiUpload(MultipartFile[] files, String bucketName, boolean downFlag,String fileLevel) throws Exception;

    /**
     * 文件下载
     * @param fileId 文件id
     * @return InputStream 文件流
     * */
    InputStream download(String fileId) throws Exception;

    /**
     * 获取文件外链地址
     * @param fileId 文件id
     * @return 外链地址
     * */
    String getFileHtmlUrl(String fileId) throws Exception;

    /**
     * 删除文件
     * @param fileId 文件id
     * */
    void remove(String fileId) throws Exception;
}
