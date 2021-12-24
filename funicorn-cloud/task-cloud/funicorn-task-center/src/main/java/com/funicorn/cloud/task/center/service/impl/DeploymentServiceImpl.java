package com.funicorn.cloud.task.center.service.impl;

import com.funicorn.cloud.task.center.entity.ActReDeployment;
import com.funicorn.cloud.task.center.mapper.ActReDeploymentMapper;
import com.funicorn.cloud.task.center.service.DeploymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-12-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeploymentServiceImpl extends ServiceImpl<ActReDeploymentMapper, ActReDeployment> implements DeploymentService {

}
