package com.funicorn.cloud.system.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.api.dto.SysLogDTO;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.SyslogPageDTO;
import com.funicorn.cloud.system.center.entity.SysLog;
import com.funicorn.cloud.system.center.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  日志管理接口
 *
 * @author Aimee
 * @since 2021-10-14
 */
@RestController
@RequestMapping("/SysLog")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

    /**
     * 保存操作日志
     * @param sysLogDTO 日志参数
     * @return Result
     * */
    @PostMapping("/saveOpsLog")
    public Result<?> saveOpsLog(@RequestBody SysLogDTO sysLogDTO){
        sysLogService.save(JsonUtil.object2Object(sysLogDTO, SysLog.class));
        return Result.ok();
    }

    /**
     * 分页查询操作日志
     * @param syslogPageDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<SysLog>> page(SyslogPageDTO syslogPageDTO){
        if (StringUtils.isBlank(syslogPageDTO.getTenantId())) {
            syslogPageDTO.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
        }
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysLog::getIsDelete, SystemConstant.NOT_DELETED);
        queryWrapper.eq(SysLog::getTenantId,syslogPageDTO.getTenantId());
        if (StringUtils.isNotBlank(syslogPageDTO.getLogType())) {
            queryWrapper.eq(SysLog::getLogType,syslogPageDTO.getLogType());
        }
        if (StringUtils.isNotBlank(syslogPageDTO.getOperationType())) {
            queryWrapper.eq(SysLog::getOperationType,syslogPageDTO.getOperationType());
        }
        if (StringUtils.isNotBlank(syslogPageDTO.getUsername())) {
            queryWrapper.like(SysLog::getUsername,syslogPageDTO.getUsername());
        }
        queryWrapper.orderByDesc(SysLog::getCreatedTime);
        IPage<SysLog> iPage = sysLogService.page(new Page<>(syslogPageDTO.getCurrent(),syslogPageDTO.getSize()),queryWrapper);
        return Result.ok(iPage);
    }
}

