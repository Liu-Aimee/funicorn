package com.funicorn.cloud.system.center.thread;

import com.funicorn.basic.common.base.util.AppContextUtil;
import com.funicorn.cloud.system.center.entity.UploadFile;
import com.funicorn.cloud.system.center.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 文件下载次数更新线程
 * @author Aimee
 * @since 2021/11/8 17:17
 */
@Slf4j
public class DownCountThread implements Runnable{

    private final RedissonClient redissonClient;

    private final UploadFileService uploadFileService;

    private static final String LOCK_KEY = "system:add:down:count";

    private final String fileId;

    public DownCountThread(String fileId) {
        this.redissonClient = AppContextUtil.getBean(RedissonClient.class);
        this.uploadFileService = AppContextUtil.getBean(UploadFileService.class);
        this.fileId = fileId;
    }

    @Override
    public void run() {
        RLock rLock = redissonClient.getLock(LOCK_KEY);
        try {
            //自旋锁
            boolean isLocked = rLock.tryLock();
            while (!isLocked) {
                isLocked = rLock.tryLock();
            }

            UploadFile uploadFile = uploadFileService.getById(fileId);
            if (uploadFile==null) {
                return;
            }

            UploadFile newUploadFile = new UploadFile();
            newUploadFile.setId(uploadFile.getId());
            newUploadFile.setDownCount(uploadFile.getDownCount() + 1);
            uploadFileService.updateById(newUploadFile);
        } catch (Exception e) {
            log.error("更新文件下载次数是失败,fileId:[{}]",fileId,e);
        } finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }
    }
}
