package com.funicorn.cloud.system.center.util;

import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author Aimee
 * @since 2021/11/9 9:34
 */
@ConditionalOnBean(MinioProperties.class)
@Slf4j
@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    /**
     * 上传文件
     * @param is 文件流
     * @param filename 文件名
     * @param contentType 内容类型
     * @param bucketName 桶名称
     */
    public void upload(InputStream is,String filename,String contentType,String bucketName) throws Exception{
        try {
            if (StringUtils.isBlank(filename)){
                throw  new SystemException(SystemErrorCode.FILE_NAME_NOT_BLANK);
            }

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .contentType(StringUtils.isNotBlank(contentType) ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .stream(is, is.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            log.error("文件上传失败",e);
            throw  new SystemException(SystemErrorCode.FILE_UPLOAD_ERROR);
        }finally {
            if (is!=null){
                is.close();
            }
        }
    }

    /**
     * 文件下载
     * @param fileName 文件名
     * @param bucketName 桶名称
     * @return InputStream 文件流
     * */
    public InputStream download(String fileName,String bucketName) {
        if (StringUtils.isBlank(fileName)) {
            throw  new SystemException(SystemErrorCode.FILE_NAME_NOT_BLANK);
        }
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();
        try {
            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            throw  new SystemException(SystemErrorCode.FILE_DELETE_ERROR);
        }
    }

    /**
     * 获取文件外链地址
     * @param fileName 文件名称
     * @param bucketName 桶名称
     * @return 外链地址
     * */
    public String getFileHtmlUrl(String fileName,String bucketName){
        try {
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(7, TimeUnit.DAYS)
                    .build();
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        }catch (Exception e){
            log.error("错误："+e.getMessage());
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileName 文件名
     * @param bucketName 桶名称
     */
    public void delete(String fileName,String bucketName) {
        if (StringUtils.isNotBlank(fileName)) {
            try {
                RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build();
                minioClient.removeObject(removeObjectArgs);
            } catch (Exception e) {
                log.error("文件删除失败", e);
                throw  new SystemException(SystemErrorCode.FILE_DELETE_ERROR);
            }
        } else {
            log.warn("filePath == >> 文件路径为空...");
        }
    }
}
