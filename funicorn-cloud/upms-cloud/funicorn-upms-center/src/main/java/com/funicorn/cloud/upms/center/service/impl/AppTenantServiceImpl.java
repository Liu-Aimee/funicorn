package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.AppTenantDTO;
import com.funicorn.cloud.upms.center.dto.ApprovalAppDTO;
import com.funicorn.cloud.upms.center.dto.TenantAppDTO;
import com.funicorn.cloud.upms.center.entity.App;
import com.funicorn.cloud.upms.center.entity.AppTenant;
import com.funicorn.cloud.upms.center.entity.Tenant;
import com.funicorn.cloud.upms.center.entity.UserTenant;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.mapper.AppMapper;
import com.funicorn.cloud.upms.center.mapper.AppTenantMapper;
import com.funicorn.cloud.upms.center.mapper.UserTenantMapper;
import com.funicorn.cloud.upms.center.service.AppTenantService;
import com.funicorn.cloud.upms.center.service.TenantService;
import com.funicorn.cloud.upms.center.service.UserRoleService;
import com.funicorn.cloud.upms.center.vo.TenantBindVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 应用与租户关系 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppTenantServiceImpl extends ServiceImpl<AppTenantMapper, AppTenant> implements AppTenantService {

    @Resource
    private AppMapper appMapper;
    @Resource
    private TenantService tenantService;
    @Resource
    private UserTenantMapper userTenantMapper;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public void bindTenant(TenantAppDTO tenantAppDTO) {
        App app = appMapper.selectById(tenantAppDTO.getAppId());
        if (app==null || UpmsConstant.IS_DELETED.equals(app.getIsDelete())) {
            return;
        }
        tenantAppDTO.getTenantIds().forEach(tenantId->{
            if (StringUtils.isBlank(tenantId)){
                return;
            }
            Tenant tenant = tenantService.getById(tenantId);
            if (tenant==null || UpmsConstant.IS_DELETED.equals(tenant.getIsDelete())){
                return;
            }
            AppTenant appTenant = new AppTenant();
            appTenant.setTenantId(tenantId);
            appTenant.setAppId(tenantAppDTO.getAppId());
            appTenant.setAppName(app.getName());
            appTenant.setTenantName(tenant.getTenantName());
            appTenant.setStatus(UpmsConstant.ENABLED);
            AppTenant oldAppTenant = baseMapper.selectOne(Wrappers.<AppTenant>lambdaQuery()
                    .eq(AppTenant::getAppId,tenantAppDTO.getAppId())
                    .eq(AppTenant::getTenantId,tenantId));
            if (oldAppTenant!=null){
                oldAppTenant.setStatus(UpmsConstant.ENABLED);
                baseMapper.updateById(oldAppTenant);
            }else {
                baseMapper.insert(appTenant);
            }

            List<UserTenant> userTenants = userTenantMapper.selectList(Wrappers.<UserTenant>lambdaQuery()
                    .eq(UserTenant::getTenantId,tenantId)
                    .in(UserTenant::getType,Arrays.asList(UpmsConstant.TENANT_USER_SUPER,UpmsConstant.TENANT_USER_ADMIN)));
            //初始化超级管理员与应用的关系
            userTenants.stream().filter(f->UpmsConstant.TENANT_USER_SUPER.equals(f.getType())).collect(Collectors.toList())
                    .forEach(userTenant -> userRoleService.initRoleSuperAdmin(userTenant.getUserId(),userTenant.getTenantId()));

            //初始化租户管理员与应用关系
            userTenants.stream().filter(f->UpmsConstant.TENANT_USER_ADMIN.equals(f.getType())).collect(Collectors.toList())
                    .forEach(userTenant -> userRoleService.initRoleTenantAdmin(userTenant.getUserId(),userTenant.getTenantId()));
        });
    }

    @Override
    public void addAppTenants(AppTenantDTO appTenantDTO) {
        Tenant tenant = tenantService.getById(appTenantDTO.getTenantId());
        if (tenant==null) {
            return;
        }
        appTenantDTO.getAppIds().forEach(appId->{
            if (StringUtils.isBlank(appId)){
                return;
            }
            App app = appMapper.selectById(appId);
            if (app==null){
                throw new UpmsException(ErrorCode.APP_NOT_EXISTS,appId);
            }
            AppTenant appTenant = new AppTenant();
            appTenant.setTenantId(appTenantDTO.getTenantId());
            appTenant.setTenantName(tenant.getTenantName());
            appTenant.setAppId(appId);
            appTenant.setAppName(app.getName());
            appTenant.setStatus(UpmsConstant.ENABLED);
            AppTenant oldAppTenant = baseMapper.selectOne(Wrappers.<AppTenant>lambdaQuery()
                    .eq(AppTenant::getAppId,appId)
                    .eq(AppTenant::getTenantId,appTenantDTO.getTenantId()));
            if (oldAppTenant!=null){
                oldAppTenant.setStatus(UpmsConstant.ENABLED);
                baseMapper.updateById(oldAppTenant);
            }else {
                baseMapper.insert(appTenant);
            }
        });

        //初始化租户管理员与应用关系
        List<UserTenant> userTenants = userTenantMapper.selectList(Wrappers.<UserTenant>lambdaQuery()
                .eq(UserTenant::getTenantId,appTenantDTO.getTenantId())
                .eq(UserTenant::getType,UpmsConstant.TENANT_USER_ADMIN));
        userTenants.forEach(userTenant -> userRoleService.initRoleTenantAdmin(userTenant.getUserId(),userTenant.getTenantId()));
    }

    @Override
    public void apply(AppTenantDTO appTenantDTO) {
        if (StringUtils.isBlank(appTenantDTO.getTenantId())){
            appTenantDTO.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
        }
        Tenant tenant = tenantService.getById(appTenantDTO.getTenantId());
        appTenantDTO.getAppIds().forEach(appId->{
            App app = appMapper.selectById(appId);
            if (app==null){
                throw new UpmsException(ErrorCode.APP_NOT_EXISTS,appId);
            }
            AppTenant appTenant = new AppTenant();
            appTenant.setAppName(app.getName());
            appTenant.setStatus(UpmsConstant.APP_APPLYING);
            appTenant.setTenantName(tenant.getTenantName());
            baseMapper.insert(appTenant);
        });
    }

    @Override
    public void approval(ApprovalAppDTO approvalAppDTO) {
        AppTenant appTenant = baseMapper.selectById(approvalAppDTO.getId());
        if (appTenant== null){
            return;
        }
        if (UpmsConstant.REFUSE_APP_APPLY.equals(approvalAppDTO.getState())){
            //拒绝
            AppTenant appTenant1 = new AppTenant();
            appTenant1.setId(appTenant.getId());
            appTenant1.setStatus(UpmsConstant.REFUSE_APP_APPLY);
            baseMapper.updateById(appTenant1);
        }else {
            //同意
            AppTenantDTO appTenantDTO = new AppTenantDTO();
            appTenantDTO.setTenantId(appTenant.getTenantId());
            appTenantDTO.setAppIds(Collections.singletonList(appTenant.getAppId()));
            this.addAppTenants(appTenantDTO);
        }
    }

    @Override
    public TenantBindVO bindTenantList(String appId) {
        List<AppTenant> bindAppTenants = baseMapper.selectBindTenantList(appId);
        List<AppTenant> unbindAppTenants = baseMapper.selectUnbindTenantList(SecurityUtil.getCurrentUser().getId(),appId);
        TenantBindVO tenantBindVO = new TenantBindVO();
        if (bindAppTenants!=null && !bindAppTenants.isEmpty()) {
            tenantBindVO.setBind(ConvertUtil.list2List(bindAppTenants,TenantBindVO.TenantInfo.class));
        }
        if (unbindAppTenants!=null && !unbindAppTenants.isEmpty()) {
            tenantBindVO.setUnbind(ConvertUtil.list2List(unbindAppTenants,TenantBindVO.TenantInfo.class));
        }
        return tenantBindVO;
    }
}
