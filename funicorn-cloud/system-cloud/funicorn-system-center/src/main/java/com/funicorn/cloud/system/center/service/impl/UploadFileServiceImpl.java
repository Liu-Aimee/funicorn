package com.funicorn.cloud.system.center.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.api.model.FileLevel;
import com.funicorn.cloud.system.api.model.UploadFileData;
import com.funicorn.cloud.system.center.config.OssTemplate;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.entity.UploadFile;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.mapper.UploadFileMapper;
import com.funicorn.cloud.system.center.property.OssProperties;
import com.funicorn.cloud.system.center.service.UploadFileService;
import com.funicorn.cloud.system.center.thread.DownCountThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文件上传信息 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-11-08
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile> implements UploadFileService {

    /**
     * 文件最大限制
     * */
    @Value("${spring.servlet.multipart.max-file-size:100MB}")
    private String maxFileSize;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private OssProperties ossProperties;
    @Resource
    private OssTemplate ossTemplate;

    @Override
    public UploadFileData upload(MultipartFile multipartFile, String bucketName, boolean downFlag, String fileLevel) throws Exception{
        if (StringUtils.isBlank(multipartFile.getOriginalFilename())){
            throw  new SystemException(SystemErrorCode.FILE_NAME_NOT_BLANK);
        }

        // 文件名长度限制
        String filename = multipartFile.getOriginalFilename();
        if (filename.length() > 100) {
            throw  new SystemException(SystemErrorCode.FILE_NAME_TOO_LONG);
        }

        //文件大小限制
        long maxSize = Long.parseLong(maxFileSize.substring(0,maxFileSize.length()-2)) * 1024 * 1024;
        if (multipartFile.getSize() > maxSize) {
            throw  new SystemException(SystemErrorCode.FILE_SIZE_TOO_LARGE,maxFileSize);
        }

        //非法的文件级别
        if (!FileLevel.containsLevel(fileLevel)) {
            throw  new SystemException(SystemErrorCode.FILE_LEVEL_IS_ILLEGAL,fileLevel);
        }

        if (StringUtils.isBlank(bucketName)){
            bucketName = ossProperties.getBucketName();
        }
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName(multipartFile.getOriginalFilename());
        uploadFile.setSuffix(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
        uploadFile.setSize(multipartFile.getSize());
        uploadFile.setBucketName(bucketName);
        uploadFile.setFileLevel(fileLevel);
        uploadFile.setDownFlag(downFlag);
        uploadFile.setDownCount(0);
        uploadFile.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
        baseMapper.insert(uploadFile);
        String objectName = fileLevel + "/" + uploadFile.getId() + uploadFile.getSuffix();
        ossTemplate.putObject(bucketName,objectName,multipartFile.getInputStream(),multipartFile.getContentType());
        UploadFileData uploadFileData =  JsonUtil.object2Object(uploadFile,UploadFileData.class);
        if (FileLevel.PUBLIC.getValue().equals(fileLevel)) {
            uploadFileData.setUrl(ossProperties.getEndpoint() + "/" + uploadFile.getBucketName() + "/" + objectName);
        }
        return uploadFileData;
    }

    @Override
    public List<UploadFileData> multiUpload(MultipartFile[] files, String bucketName, boolean downFlag,String fileLevel) {
        List<UploadFileData> list = new ArrayList<>();
        for (MultipartFile file:files) {
            try {
                UploadFileData uploadFileData = upload(file,bucketName,downFlag,fileLevel);
                list.add(uploadFileData);
            } catch (Exception e) {
                log.error("[{}]上传失败",file.getOriginalFilename(),e);
            }
        }
        return list;
    }

    @Override
    public InputStream download(String fileId) throws Exception {
        UploadFile uploadFile = baseMapper.selectById(fileId);
        if (uploadFile==null) {
            throw new SystemException(SystemErrorCode.FILE_NOT_FOUND);
        }

        String objectName = uploadFile.getFileLevel() + "/" + uploadFile.getId() + uploadFile.getSuffix();
        InputStream is = ossTemplate.getObject(uploadFile.getBucketName(),objectName);
        //开启异步线程更新下载次数
        threadPoolTaskExecutor.execute(new DownCountThread(fileId));
        return is;
    }

    @Override
    public String getFileHtmlUrl(String fileId) throws Exception {
        UploadFile uploadFile = baseMapper.selectById(fileId);
        if (uploadFile==null) {
            throw new SystemException(SystemErrorCode.FILE_NOT_FOUND);
        }
        String objectName = uploadFile.getFileLevel() + "/" + uploadFile.getId() + uploadFile.getSuffix();
        return ossTemplate.getFileSignUrl(uploadFile.getBucketName(),objectName,7);
    }

    @Override
    public void remove(String fileId) throws Exception {
        UploadFile uploadFile = baseMapper.selectById(fileId);
        if (uploadFile==null) {
            return;
        }
        if (!SecurityUtil.getCurrentUser().getUsername().equals(uploadFile.getCreatedBy())) {
            throw  new SystemException(SystemErrorCode.FILE_NOT_BELONGING);
        }
        String objectName = uploadFile.getFileLevel() + "/" + uploadFile.getId() + uploadFile.getSuffix();
        ossTemplate.removeObject(uploadFile.getBucketName(),objectName);
        UploadFile newUploadFile = new UploadFile();
        newUploadFile.setId(uploadFile.getId());
        newUploadFile.setIsDelete(SystemConstant.IS_DELETED);
        baseMapper.updateById(newUploadFile);
    }
}
