package com.funicorn.cloud.system.center.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.system.center.config.OssTemplate;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.entity.UploadFile;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.service.UploadFileService;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Aimee
 * @since 2021/11/15 9:20
 * 对象存储桶接口管理
 */
@Slf4j
@RestController
@RequestMapping("/bucket")
public class BucketController {

    @Resource
    private OssTemplate ossTemplate;
    @Resource
    private UploadFileService uploadFileService;

    /**
     * 创建桶
     * @param bucketName 桶名称
     * @return Result
     * */
    @PostMapping("/create")
    public Result<?> create(@RequestParam String bucketName) throws Exception {
        ossTemplate.createBucket(bucketName);
        return Result.ok();
    }

    /**
     * 删除桶
     * @param bucketName 桶名称
     * @return Result
     * */
    @DeleteMapping("/{bucketName}")
    public Result<?> remove(@PathVariable String bucketName) throws Exception {
        Iterable<io.minio.Result<Item>> iterable = ossTemplate.listObjectsByPrefix(bucketName,null,false);
        if (iterable.iterator().hasNext()) {
            throw new SystemException(SystemErrorCode.BUCKET_HAS_FILE);
        }
        ossTemplate.removeBucket(bucketName);
        UploadFile uploadFile = new UploadFile();
        uploadFile.setIsDelete(SystemConstant.IS_DELETED);
        uploadFileService.update(uploadFile, Wrappers.<UploadFile>lambdaQuery().eq(UploadFile::getBucketName,bucketName));
        return Result.ok("已删除");
    }
}
