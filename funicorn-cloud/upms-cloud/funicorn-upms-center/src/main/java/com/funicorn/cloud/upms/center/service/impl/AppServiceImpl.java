package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.basic.common.security.model.RoleInfo;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.AppPageDTO;
import com.funicorn.cloud.upms.center.entity.App;
import com.funicorn.cloud.upms.center.entity.AppTenant;
import com.funicorn.cloud.upms.center.mapper.AppMapper;
import com.funicorn.cloud.upms.center.mapper.AppTenantMapper;
import com.funicorn.cloud.upms.center.service.AppService;
import com.funicorn.cloud.upms.center.vo.AppVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Resource
    private AppTenantMapper appTenantMapper;

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

    @Override
    public IPage<AppVO> appPage(AppPageDTO appPageDTO) {
        List<AppTenant> appTenants = appTenantMapper.selectList(Wrappers.<AppTenant>lambdaQuery()
                .eq(AppTenant::getTenantId,appPageDTO.getTenantId()));
        if (appTenants==null || appTenants.isEmpty()) {
            return new Page<>(appPageDTO.getCurrent(),appPageDTO.getSize());
        }

        IPage<App> iPage = baseMapper.selectPage(new Page<>(appPageDTO.getCurrent(),appPageDTO.getSize()),
                Wrappers.<App>lambdaQuery()
                        .eq(App::getStatus,UpmsConstant.ENABLED)
                        .eq(App::getIsDelete,UpmsConstant.NOT_DELETED)
                        .in(App::getId,appTenants.stream().map(AppTenant::getAppId).collect(Collectors.toList())));
        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()) {
            return new Page<>(appPageDTO.getCurrent(),appPageDTO.getSize());
        }else {
            IPage<AppVO> resultPage = ConvertUtil.page2Page(iPage,AppVO.class);
            Map<String, Integer> statusMap = appTenants.stream().collect(Collectors.toMap(AppTenant::getAppId,AppTenant::getStatus));
            resultPage.getRecords().forEach(appVO -> appVO.setTenantStatus(statusMap.get(appVO.getId())));
            return resultPage;
        }
    }
}
