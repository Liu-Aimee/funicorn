package com.funicorn.cloud.upms.center.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.basic.common.security.model.RoleInfo;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.center.constant.LeveEnum;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.*;
import com.funicorn.cloud.upms.center.entity.App;
import com.funicorn.cloud.upms.center.entity.AppTenant;
import com.funicorn.cloud.upms.center.entity.RoleApp;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.service.AppService;
import com.funicorn.cloud.upms.center.service.AppTenantService;
import com.funicorn.cloud.upms.center.service.RoleAppService;
import com.funicorn.cloud.upms.center.vo.AppVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 应用管理 接口
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Slf4j
@RestController
@RequestMapping("/App")
public class AppController {

    @Resource
    private AppService appService;
    @Resource
    private RoleAppService roleAppService;
    @Resource
    private AppTenantService appTenantService;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 根据应用唯一标识获取应用信息
     * */
    @GetMapping("/getAppByClientId")
    public Result<App> getAppByClientId(String clientId) {
        App app = appService.getOne(Wrappers.<App>lambdaQuery().eq(App::getClientId,clientId).eq(App::getIsDelete, UpmsConstant.NOT_DELETED).last("limit 1"));
        if (app==null) {
            throw new UpmsException(ErrorCode.APP_NOT_EXISTS,clientId);
        }
        return Result.ok(app);
    }

    /**
     * 分页查询应用
     * @param appPageDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<AppVO>> page(AppPageDTO appPageDTO) {
        LambdaQueryWrapper<App> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(App::getIsDelete,UpmsConstant.NOT_DELETED);
        if (StringUtils.isNotBlank(appPageDTO.getClientId())){
            queryWrapper.like(App::getClientId,appPageDTO.getClientId());
        }
        if (StringUtils.isNotBlank(appPageDTO.getName())){
            queryWrapper.like(App::getName,appPageDTO.getName());
        }
        IPage<App> iPage = appService.page(new Page<>(appPageDTO.getCurrent(),appPageDTO.getSize()),queryWrapper);
        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()) {
            return Result.ok(new Page<>(appPageDTO.getCurrent(),appPageDTO.getSize()));
        }
        IPage<AppVO> resultPage = ConvertUtil.page2Page(iPage,AppVO.class);
        resultPage.getRecords().forEach(appVO -> {
            int count = appTenantService.count(Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getAppId,appVO.getId()).eq(AppTenant::getStatus,UpmsConstant.APP_APPLYING));
            appVO.setApplyCount(count);
        });
        return Result.ok(resultPage);
    }

    /**
     * 分页查询自己可以访问的应用
     * @param appPageDTO 入参
     * @return Result
     * */
    @GetMapping("/visitPage")
    public Result<IPage<App>> visitPage(AppPageDTO appPageDTO) {
        return Result.ok(appService.visitPage(appPageDTO));
    }

    /**
     * 分页查询自己可以访问的应用
     * @param appPageDTO 入参
     * @return Result
     * */
    @GetMapping("/appPage")
    public Result<IPage<AppVO>> appPage(AppPageDTO appPageDTO) {
        return Result.ok(appService.appPage(appPageDTO));
    }

    /**
     * 查询应用市场的应用
     * @param appName 入参
     * @return Result
     * */
    @GetMapping("/market")
    public Result<List<AppVO>> market(@RequestParam(required = false) String appName) {
        LambdaQueryWrapper<App> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(App::getIsDelete,UpmsConstant.NOT_DELETED);
        queryWrapper.eq(App::getLevel,LeveEnum.PUBLIC.toString().toLowerCase());
        if (StringUtils.isNotBlank(appName)){
            queryWrapper.like(App::getName,appName);
        }

        List<App> apps = appService.list(queryWrapper);
        if (apps==null || apps.isEmpty()) {
            return Result.ok(new ArrayList<>());
        }

        List<AppVO> appVOS = ConvertUtil.list2List(apps,AppVO.class);
        List<AppTenant> appTenants = appTenantService.list(Wrappers.<AppTenant>lambdaQuery().eq(AppTenant::getTenantId,SecurityUtil.getCurrentUser().getTenantId()));
        if (appTenants!=null && !appTenants.isEmpty()) {
            Map<String,Integer> appTenantMap = appTenants.stream().collect(Collectors.toMap(AppTenant::getAppId,AppTenant::getStatus));
            appVOS.forEach(appVO -> appVO.setTenantStatus(appTenantMap.get(appVO.getId())));
        }
        return Result.ok(appVOS);
    }



    /**
     * 查询应用已绑定的租户列表
     * @param appId 应用id
     * @return Result
     */
    @GetMapping("/bindTenantList")
    public Result<List<AppTenant>> bindTenantList(@RequestParam String appId) {
        return Result.ok(appTenantService.bindTenantList(appId));
    }

    /**
     * 当前登陆人可访问的应用列表
     * @return Result
     */
    @GetMapping("/currentApps")
    public Result<List<App>> currentApps() {
        List<App> apps = new ArrayList<>();
        if (SecurityUtil.getCurrentUser().getRoles()!=null && !SecurityUtil.getCurrentUser().getRoles().isEmpty()){
            List<String> roleIds = SecurityUtil.getCurrentUser().getRoles().stream().map(RoleInfo::getId).collect(Collectors.toList());
            List<RoleApp> roleApps = roleAppService.list(Wrappers.<RoleApp>lambdaQuery().in(RoleApp::getRoleId,roleIds));
            List<String> appIds = roleApps.stream().map(RoleApp::getAppId).collect(Collectors.toList());
            List<AppTenant> appTenants = appTenantService.list(Wrappers.<AppTenant>lambdaQuery()
                    .eq(AppTenant::getTenantId,SecurityUtil.getCurrentUser().getTenantId())
                    .ne(AppTenant::getStatus,UpmsConstant.APP_APPLYING).ne(AppTenant::getStatus,UpmsConstant.REFUSE_APP_APPLY));
            List<String> tenantAppIds = appTenants.stream().map(AppTenant::getAppId).collect(Collectors.toList());
            List<String> realAppIds = appIds.stream().filter(tenantAppIds::contains).collect(Collectors.toList());
            if (!realAppIds.isEmpty()) {
                LambdaQueryWrapper<App> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(App::getIsDelete,UpmsConstant.NOT_DELETED);
                queryWrapper.in(App::getId,realAppIds);
                apps = appService.list(queryWrapper);
            }
        }
        return Result.ok(apps);
    }

    /**
     * 查询租户未绑定的应用
     * @param tenantId 租户id 默认当前登陆人选择的租户
     * @return Result
     */
    @GetMapping("/unbindList")
    public Result<List<App>> noOpenList(@RequestParam(value = "tenantId",required = false) String tenantId) {
        if (StringUtils.isBlank(tenantId)){
            tenantId = SecurityUtil.getCurrentUser().getTenantId();
        }
        return Result.ok(appService.getUnbindList(tenantId));
    }

    /**
     * 应用绑定租户
     * @param tenantAppDTO 入参
     * @return Result
     */
    @PostMapping("/bindTenant")
    public Result<?> bindTenant(@RequestBody TenantAppDTO tenantAppDTO) {
        appTenantService.bindTenant(tenantAppDTO);
        return Result.ok();
    }

    /**
     * 新增应用
     * @param appDTO 应用信息
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> save(@RequestBody @Validated(Insert.class) AppDTO appDTO) {
        int count = appService.count(Wrappers.<App>lambdaQuery().eq(App::getClientId,appDTO.getClientId()));
        if (count>0){
            throw new UpmsException(ErrorCode.CLIENT_ID_IS_EXISTS,appDTO.getClientId());
        }
        count = appService.count(Wrappers.<App>lambdaQuery().eq(App::getName,appDTO.getName()));
        if (count>0){
            throw new UpmsException(ErrorCode.APP_NAME_IS_EXISTS,appDTO.getName());
        }
        appDTO.setClientSecret(passwordEncoder.encode(appDTO.getClientSecret()));
        App app = JsonUtil.object2Object(appDTO,App.class);
        //设置默认授权模式
        app.setAuthorizedGrantTypes("password,refresh_token,authorization_code");
        appService.save(app);
        return Result.ok(app);
    }

    /**
     * 修改应用
     * @param appDTO 应用信息
     * @return Result
     */
    @PostMapping("/update")
    public Result<?> updateById(@RequestBody @Validated(Update.class) AppDTO appDTO) {
        //应用id和密钥不允许修改
        appDTO.setClientId(null);
        appDTO.setClientSecret(null);
        if (StringUtils.isNotBlank(appDTO.getName())){
            int count = appService.count(Wrappers.<App>lambdaQuery().eq(App::getName,appDTO.getName()).ne(App::getId,appDTO.getId()));
            if (count>0){
                throw new UpmsException(ErrorCode.APP_NAME_IS_EXISTS,appDTO.getName());
            }
        }
        appService.updateById(JsonUtil.object2Object(appDTO,App.class));
        return Result.ok("修改成功");
    }

    /**
     * 修改应用密钥
     * @param appSecretDTO 入参
     * @return Result
     */
    @PostMapping("/modifySecret")
    public Result<?> modifySecret(@RequestBody @Validated(Update.class) AppSecretDTO appSecretDTO) {
        App app = appService.getById(appSecretDTO.getAppId());
        if (app==null){
            throw new UpmsException(ErrorCode.APP_NOT_EXISTS,appSecretDTO.getAppId());
        }

        App updateApp = new App();
        updateApp.setId(appSecretDTO.getAppId());
        updateApp.setClientSecret(passwordEncoder.encode(appSecretDTO.getClientSecret()));
        appService.updateById(updateApp);
        return Result.ok("修改密钥成功");
    }

    /**
     * 待申请列表
     * @param appId 应用id
     * @return Result
     */
    @GetMapping("/applyingList")
    public Result<List<AppTenant>> applyingList(@RequestParam String appId) {
        List<AppTenant> appTenants = appTenantService.list(Wrappers.<AppTenant>lambdaQuery()
                .eq(AppTenant::getAppId,appId).eq(AppTenant::getStatus,UpmsConstant.APP_APPLYING));
        return Result.ok(appTenants);
    }

    /**
     * 申请开通应用
     * @param appTenantDTO 入参
     * @return Result
     */
    @PostMapping("/apply")
    public Result<?> apply(@RequestBody @Validated(Insert.class) AppTenantDTO appTenantDTO) {
        appTenantService.apply(appTenantDTO);
        return Result.ok();
    }

    /**
     * 审批应用申请
     * @param approvalAppDTO 入参
     * @return Result
     */
    @PostMapping("/approval")
    public Result<?> approval(@RequestBody @Validated(Update.class) ApprovalAppDTO approvalAppDTO) {
        appTenantService.approval(approvalAppDTO);
        return Result.ok("审批完成");
    }

    /**
     * 删除应用
     * @param appId 入参
     * @return Result
     */
    @DeleteMapping("/{appId}")
    public Result<?> remove(@PathVariable String appId) {
        App app = new App();
        app.setId(appId);
        app.setIsDelete(UpmsConstant.IS_DELETED);
        appService.updateById(app);
        return Result.ok("删除成功");
    }
}

