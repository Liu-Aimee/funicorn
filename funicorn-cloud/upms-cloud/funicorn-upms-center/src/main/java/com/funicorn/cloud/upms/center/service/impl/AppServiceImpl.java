package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.security.model.RoleInfo;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.center.dto.AppPageDTO;
import com.funicorn.cloud.upms.center.entity.App;
import com.funicorn.cloud.upms.center.mapper.AppMapper;
import com.funicorn.cloud.upms.center.service.AppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 应用管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Override
    public List<App> getUnbindList(String tenantId) {
        return baseMapper.selectUnbindList(tenantId);
    }

    @Override
    public IPage<App> visitPage(AppPageDTO appPageDTO) {
        appPageDTO.setUsername(SecurityUtil.getCurrentUser().getUsername());
        List<String> roleIds = new ArrayList<>();
        if (SecurityUtil.getCurrentUser().getRoles()!=null && !SecurityUtil.getCurrentUser().getRoles().isEmpty()){
            roleIds = SecurityUtil.getCurrentUser().getRoles().stream().map(RoleInfo::getId).collect(Collectors.toList());
        } else {
            roleIds.add("");
        }
        appPageDTO.setRoleIds(roleIds);
        return baseMapper.queryPage(new Page<>(appPageDTO.getCurrent(),appPageDTO.getSize()), appPageDTO);
    }
}
