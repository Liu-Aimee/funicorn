package com.funicorn.cloud.system.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.system.center.dto.BucketDTO;
import com.funicorn.cloud.system.center.entity.BucketConfig;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aimee
 * @since 2022-03-18
 */
public interface BucketConfigService extends IService<BucketConfig> {

    /**
     * 创建桶
     * @param bucketDTO 入参
     * @throws Exception Exception
     * */
    void create(BucketDTO bucketDTO) throws Exception;

    /**
     * 删除桶
     * @param id id
     * @throws Exception Exception
     * */
    void  remove(String id) throws Exception;

    /**
     * 强制删除
     * @param id id
     * @throws Exception Exception
     * */
    void  removeForce(String id) throws Exception;
}
