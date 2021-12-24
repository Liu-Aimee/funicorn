package com.funicorn.cloud.system.center.service.impl;

import com.funicorn.cloud.system.center.entity.SysLog;
import com.funicorn.cloud.system.center.mapper.SysLogMapper;
import com.funicorn.cloud.system.center.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户操作日志管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
