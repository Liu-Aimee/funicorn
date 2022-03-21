package com.funicorn.cloud.system.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.BucketDTO;
import com.funicorn.cloud.system.center.dto.BucketQueryDTO;
import com.funicorn.cloud.system.center.entity.BucketConfig;
import com.funicorn.cloud.system.center.service.BucketConfigService;
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
    private BucketConfigService bucketConfigService;

    /**
     * 分页查询桶列表
     * @param bucketQueryDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<BucketConfig>> page (BucketQueryDTO bucketQueryDTO) {
        LambdaQueryWrapper<BucketConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BucketConfig::getTenantId,bucketQueryDTO.getTenantId());
        queryWrapper.eq(BucketConfig::getIsDelete, SystemConstant.NOT_DELETED);
        return Result.ok(bucketConfigService.page(new Page<>(bucketQueryDTO.getCurrent(),bucketQueryDTO.getSize()),queryWrapper));
    }

    /**
     * 创建桶
     * @param bucketDTO 入参
     * @return Result
     * */
    @PostMapping("/create")
    public Result<?> create(@RequestBody BucketDTO bucketDTO) throws Exception {
        bucketConfigService.create(bucketDTO);
        return Result.ok();
    }

    /**
     * 删除(保留30天)
     * @param id 桶id
     * @return Result
     * */
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable String id) throws Exception {
        bucketConfigService.remove(id);
        return Result.ok("已删除");
    }

    /**
     * 强制删除(无法恢复)
     * @param id 桶id
     * @return Result
     * */
    @DeleteMapping("/removeForce/{id}")
    public Result<?> removeForce(@PathVariable String id) throws Exception {
        bucketConfigService.removeForce(id);
        return Result.ok("已删除");
    }
}
