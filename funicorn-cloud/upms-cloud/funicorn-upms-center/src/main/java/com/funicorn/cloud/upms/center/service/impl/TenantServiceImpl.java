package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.upms.center.dto.AppTenantDTO;
import com.funicorn.cloud.upms.center.dto.TenantDTO;
import com.funicorn.cloud.upms.center.entity.AppTenant;
import com.funicorn.cloud.upms.center.entity.Tenant;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.mapper.TenantMapper;
import com.funicorn.cloud.upms.center.service.AppTenantService;
import com.funicorn.cloud.upms.center.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Resource
    private AppTenantService appTenantService;

    @Override
    public Tenant addTenant(TenantDTO tenantDTO) {
        Tenant tenant = JsonUtil.object2Object(tenantDTO,Tenant.class);
        baseMapper.insert(tenant);
        //保存租户与应用关系
        if (tenantDTO.getAppIds()!=null && !tenantDTO.getAppIds().isEmpty()){
            AppTenantDTO appTenantDTO = new AppTenantDTO();
            appTenantDTO.setTenantId(tenant.getId());
            appTenantDTO.setAppIds(tenantDTO.getAppIds());
            appTenantService.addAppTenants(appTenantDTO);
        }
        return tenant;
    }

    @Override
    public void updateTenant(TenantDTO tenantDTO) {
        Tenant odlTenant = baseMapper.selectById(tenantDTO.getId());
        if (odlTenant==null) {
            return;
        }
        int count = baseMapper.selectCount(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantName, tenantDTO.getTenantName()).ne(Tenant::getId,tenantDTO.getId()));
        if (count>0) {
            throw new UpmsException(ErrorCode.TENANT_NAME_EXISTS,tenantDTO.getTenantName());
        }

        //是否修改了租户名称
        if (!odlTenant.getTenantName().equals(tenantDTO.getTenantName())) {
            AppTenant appTenant = new AppTenant();
            appTenant.setTenantName(tenantDTO.getTenantName());
            appTenantService.update(appTenant,Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,tenantDTO.getId()));
        }

        Tenant tenant = JsonUtil.object2Object(tenantDTO,Tenant.class);
        baseMapper.updateById(tenant);
        AppTenant appTenant = new AppTenant();
        appTenant.setTenantName(tenantDTO.getTenantName());
        appTenantService.update(appTenant,Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,tenantDTO.getTenantName()));
        //保存租户与应用关系
        if (tenantDTO.getAppIds()!=null && !tenantDTO.getAppIds().isEmpty()){
            List<AppTenant> appTenants = appTenantService.list(Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,tenantDTO.getId()));
            List<String> oldAppIds = appTenants.stream().map(AppTenant::getAppId).collect(Collectors.toList());
            List<String> insertAppIds = tenantDTO.getAppIds().stream().filter(f -> !oldAppIds.contains(f)).collect(Collectors.toList());
            List<String> deleteAppIds = oldAppIds.stream().filter(f->!tenantDTO.getAppIds().contains(f)).collect(Collectors.toList());

            if (!deleteAppIds.isEmpty()) {
                //删除剔除的应用
                appTenantService.remove(Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,tenantDTO.getId()).in(AppTenant::getAppId,deleteAppIds));
            }

            //保存租户与应用关系
            if (!insertAppIds.isEmpty()) {
                AppTenantDTO appTenantDTO = new AppTenantDTO();
                appTenantDTO.setTenantId(tenant.getId());
                appTenantDTO.setAppIds(insertAppIds);
                appTenantService.addAppTenants(appTenantDTO);
            }
        } else {
            //删除租户绑定的所有应用
            appTenantService.remove(Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,tenantDTO.getId()));
        }
    }
}
