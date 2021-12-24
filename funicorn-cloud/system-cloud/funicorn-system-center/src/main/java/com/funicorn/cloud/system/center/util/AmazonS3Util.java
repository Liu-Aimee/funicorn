/*
package com.funicorn.cloud.system.center.util;

import com.funicorn.cloud.system.center.config.OssTemplate;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

*/
/**
 * 对象存储工具类
 * @author Aimee
 * @since 2021/11/13 9:20
 *//*

@SuppressWarnings("unused")
@Slf4j
@Component
public class AmazonS3Util {

    @Resource
    private OssTemplate ossTemplate;

    */
/**
     * 修改桶设置
     * @param accelerate 是否启用加上 true/false
     * @param acl 权限 public/private
     * *//*

    public void setBucketConfig(String bucketName,Boolean accelerate,String acl){
        ossTemplate.setBucket(bucketName,accelerate,acl);
    }

    */
/**
     * 上传文件
     * @param is 文件流
     * @param objectName 文件名
     * @param contentType 内容类型
     * @param bucketName 桶名称
     *//*

    public void upload(String bucketName, String objectName, InputStream is, long size, String contentType) throws Exception{
        try {
            if (StringUtils.isBlank(objectName)){
                throw  new SystemException(SystemErrorCode.FILE_NAME_NOT_BLANK);
            }
            ossTemplate.putObject(bucketName,objectName,is,size,contentType);
        } catch (Exception e) {
            log.error("文件上传失败",e);
            throw  new SystemException(SystemErrorCode.FILE_UPLOAD_ERROR);
        }finally {
            if (is!=null){
                is.close();
            }
        }
    }

    */
/**
     * 文件下载
     * @param objectName 文件名
     * @param bucketName 桶名称
     * @return InputStream 文件流
     * *//*

    public InputStream download(String bucketName, String objectName) {
        if (StringUtils.isBlank(objectName)) {
            throw  new SystemException(SystemErrorCode.FILE_NAME_NOT_BLANK);
        }
        try {
            return ossTemplate.getObject(bucketName,objectName).getObjectContent();
        } catch (Exception e) {
            throw  new SystemException(SystemErrorCode.FILE_DOWNLOAD_ERROR);
        }
    }

    */
/**
     * 获取文件签名地址
     * @param objectName 文件名称
     * @param bucketName 桶名称
     * @param expires 有效期 单位天 默认7天
     * @return 外链地址
     * *//*

    public String getFileSignUrl(String bucketName, String objectName, Integer expires){
        try {
            return ossTemplate.getFileUrl(bucketName,objectName,expires==null ? 7 : expires);
        }catch (Exception e){
            log.error("获取文件签名地址失败",e);
        }
        return null;
    }

    */
/**
     * 删除文件
     * @param objectName 文件名
     * @param bucketName 桶名称
     *//*

    public void delete(String bucketName, String objectName) {
        if (StringUtils.isNotBlank(objectName)) {
            try {
                ossTemplate.removeObject(bucketName,objectName);
            } catch (Exception e) {
                log.error("文件删除失败", e);
                throw  new SystemException(SystemErrorCode.FILE_DELETE_ERROR);
            }
        } else {
            log.warn("filePath == >> 文件路径为空...");
        }
    }
}
*/
