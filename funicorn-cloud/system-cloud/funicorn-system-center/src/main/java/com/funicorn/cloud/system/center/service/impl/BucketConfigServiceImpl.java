package com.funicorn.cloud.system.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.cloud.system.api.model.FileLevel;
import com.funicorn.cloud.system.center.config.OssTemplate;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.BucketDTO;
import com.funicorn.cloud.system.center.entity.BucketConfig;
import com.funicorn.cloud.system.center.entity.UploadFile;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.mapper.BucketConfigMapper;
import com.funicorn.cloud.system.center.mapper.UploadFileMapper;
import com.funicorn.cloud.system.center.service.BucketConfigService;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2022-03-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BucketConfigServiceImpl extends ServiceImpl<BucketConfigMapper, BucketConfig> implements BucketConfigService {

    @Resource
    private OssTemplate ossTemplate;
    @Resource
    private UploadFileMapper uploadFileMapper;
    @Resource
    private BucketConfigMapper bucketConfigMapper;

    @Override
    public void create(BucketDTO bucketDTO) throws Exception {
        BucketConfig existsBucket = baseMapper.selectOne(Wrappers.<BucketConfig>lambdaQuery().eq(BucketConfig::getName,bucketDTO.getName()).last("limit 1"));
        if (existsBucket!=null && SystemConstant.RECOVERY_YES.equals(existsBucket.getRecovery())) {
            throw new SystemException(SystemErrorCode.BUCKET_IS_EXISTS);
        }
        if (!FileLevel.hasType(bucketDTO.getLevel())) {
            throw new SystemException(SystemErrorCode.FILE_LEVEL_IS_ILLEGAL,bucketDTO.getLevel());
        }

        BucketConfig bucketConfig = new BucketConfig();
        bucketConfig.setName(bucketDTO.getName());
        bucketConfig.setLevel(bucketDTO.getLevel());
        bucketConfig.setTenantId(bucketDTO.getTenantId());
        bucketConfigMapper.insert(bucketConfig);
        ossTemplate.createBucket(bucketDTO.getName(),bucketDTO.getLevel());
    }

    @Override
    public void remove(String id) throws Exception {
        BucketConfig bucketConfig = getById(id);
        if (bucketConfig==null) {
            return;
        }
        BucketConfig updateBucket = new BucketConfig();
        updateBucket.setId(id);
        updateBucket.setIsDelete(SystemConstant.IS_DELETED);
        updateBucket.setDeleteDate(new Date());
        updateById(updateBucket);

        LambdaUpdateWrapper<UploadFile> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UploadFile::getBucketName,bucketConfig.getName());
        updateWrapper.set(UploadFile::getTenantId,bucketConfig.getTenantId());
        UploadFile updateFile = new UploadFile();
        updateFile.setIsDelete(SystemConstant.IS_DELETED);
        uploadFileMapper.update(updateFile,updateWrapper);
    }

    @Override
    public void removeForce(String id) throws Exception {
        BucketConfig bucketConfig = getById(id);
        if (bucketConfig==null) {
            return;
        }
        //修改桶为删除状态
        BucketConfig updateBucket = new BucketConfig();
        updateBucket.setId(id);
        updateBucket.setIsDelete(SystemConstant.IS_DELETED);
        updateBucket.setDeleteDate(new Date());
        updateBucket.setRecovery(SystemConstant.RECOVERY_NO);
        updateById(updateBucket);
        UploadFile uploadFile = new UploadFile();
        uploadFile.setIsDelete(SystemConstant.IS_DELETED);
        uploadFileMapper.update(uploadFile, Wrappers.<UploadFile>lambdaUpdate().eq(UploadFile::getBucketName,bucketConfig.getName()));
        //修改文件为删除状态
        LambdaUpdateWrapper<UploadFile> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UploadFile::getBucketName,bucketConfig.getName());
        updateWrapper.set(UploadFile::getTenantId,bucketConfig.getTenantId());
        UploadFile updateFile = new UploadFile();
        updateFile.setIsDelete(SystemConstant.IS_DELETED);
        uploadFileMapper.update(updateFile,updateWrapper);

        Iterable<Result<Item>> iterable = ossTemplate.listObjectsByPrefix(bucketConfig.getName(),null,false);
        for (Result<Item> itemResult : iterable) {
            ossTemplate.removeObject(bucketConfig.getName(),itemResult.get().objectName());
        }
        //OSS删除桶
        ossTemplate.removeBucket(bucketConfig.getName());
    }
}
