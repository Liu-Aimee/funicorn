package com.funicorn.cloud.upms.center.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.exception.BaseErrorCode;
import com.funicorn.basic.common.base.exception.CommonBaseException;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.api.model.UploadFileData;
import com.funicorn.cloud.system.api.service.FunicornSystemService;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.TenantDTO;
import com.funicorn.cloud.upms.center.dto.UserTenantDTO;
import com.funicorn.cloud.upms.center.entity.Tenant;
import com.funicorn.cloud.upms.center.entity.UserTenant;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.service.TenantService;
import com.funicorn.cloud.upms.center.service.UserTenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户管理 接口
 *
 * @author Aimee
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/Tenant")
public class TenantController {

    @Resource
    private TenantService tenantService;
    @Resource
    private UserTenantService userTenantService;
    @Resource
    private FunicornSystemService funicornSystemService;

    /**
     * 查询当前用户可用租户
     * @return Result<List<Tenant>>
     * */
    @GetMapping("/list")
    public Result<List<Tenant>> list(@RequestParam(required = false) String tenantName){
        List<Tenant> tenants;
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        if (UpmsConstant.TENANT_USER_SUPER.equals(currentUser.getType())){
            LambdaQueryWrapper<Tenant> tenantQueryWrapper = Wrappers.<Tenant>lambdaQuery().eq(Tenant::getIsDelete, UpmsConstant.NOT_DELETED)
                    .orderByDesc(Tenant::getCreatedTime);
            if (StringUtils.isNotBlank(tenantName)) {
                tenantQueryWrapper.like(Tenant::getTenantName,tenantName);
            }
            tenants = tenantService.list(tenantQueryWrapper);
        } else {
            List<UserTenant> userTenants = userTenantService.list(Wrappers.<UserTenant>lambdaQuery().eq(UserTenant::getUserId,SecurityUtil.getCurrentUser().getId()));
            if (userTenants==null || userTenants.isEmpty()){
                return Result.ok(new ArrayList<>());
            }
            List<String> tenantIds = userTenants.stream().map(UserTenant::getTenantId).collect(Collectors.toList());
            LambdaQueryWrapper<Tenant> tenantQueryWrapper = Wrappers.<Tenant>lambdaQuery().eq(Tenant::getIsDelete, UpmsConstant.NOT_DELETED)
                    .in(Tenant::getId, tenantIds).orderByDesc(Tenant::getCreatedTime);
            if (StringUtils.isNotBlank(tenantName)) {
                tenantQueryWrapper.like(Tenant::getTenantName,tenantName);
            }
            tenants = tenantService.list(tenantQueryWrapper);
        }
        return Result.ok(tenants);
    }

    /**
     * 登陆时查询当前用户可选租户
     * @param username 用户名
     * @return Result
     * */
    @GetMapping("/options")
    public Result<List<UserTenant>> options(@RequestParam String username){
        List<UserTenant> userTenants = userTenantService.list(Wrappers.<UserTenant>lambdaQuery().eq(UserTenant::getUsername,username));
        if (userTenants==null || userTenants.isEmpty()){
            return Result.ok(new ArrayList<>());
        }
        return Result.ok(userTenants);
    }

    /**
     * 新增租户
     * @param tenantDTO 入参
     * @return Result
     * */
    @PostMapping("/add")
    public Result<Tenant> add(@RequestBody @Validated(Insert.class) TenantDTO tenantDTO){
        int count = tenantService.count(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantName, tenantDTO.getTenantName()));
        if (count>0) {
            throw new UpmsException(ErrorCode.TENANT_NAME_EXISTS,tenantDTO.getTenantName());
        }
        return Result.ok(tenantService.addTenant(tenantDTO));
    }

    /**
     * 修改租户信息
     * @param tenantDTO 租户信息
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> update(@RequestBody @Validated(Update.class) TenantDTO tenantDTO){
        tenantService.updateTenant(tenantDTO);
        return Result.ok();
    }

    /**
     * 上传用户头像
     * @param file 文件流
     * @return 头像访问路径
     * */
    @PostMapping(value = "/uploadLogo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<String> uploadLogo(@RequestParam(value = "file") MultipartFile file,@RequestParam(required = false) String tenantId){
        List<String> suffixList = Arrays.asList("jpg","jpeg","png","JPG","JPEG","PNG");
        if (StringUtils.isBlank(file.getContentType()) || !suffixList.contains(file.getContentType().split("/")[1])) {
            throw new UpmsException(ErrorCode.NOT_SUPPORTED_PIC_SUFFIX,file.getContentType());
        }
        Result<UploadFileData> result = funicornSystemService.upload(file,null,true);
        if (result==null || !result.isSuccess() || result.getData()==null){
            return Result.error("头像上传失败");
        }
        if (StringUtils.isNotBlank(tenantId)) {
            Tenant tenant = new Tenant();
            tenant.setId(tenantId);
            tenant.setLogoUrl(result.getData().getUrl());
            tenantService.updateById(tenant);
        }
        return Result.ok(result.getData().getUrl(),"上传logo成功");
    }

    /**
     * 根据用户信息查询已绑定的租户
     * @param userId 用户id
     * @param username 用户名
     * @return Result<List<UserTenant>>
     */
    @GetMapping("/getTenantsByUser")
    public Result<List<UserTenant>> getTenantsByUserId(@RequestParam(value = "userId",required = false) String userId,
                                                       @RequestParam(value = "username",required = false) String username) {
        if (StringUtils.isBlank(userId) && StringUtils.isBlank(username)){
            throw new CommonBaseException(BaseErrorCode.PARAM_IS_INVALID,"userId and username is null!");
        }
        LambdaQueryWrapper<UserTenant> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userId)){
            queryWrapper.eq(UserTenant::getUserId,userId);
        }
        if (StringUtils.isNotBlank(username)){
            queryWrapper.eq(UserTenant::getUsername,username);
        }
        List<UserTenant> tenants = userTenantService.list(queryWrapper);
        return Result.ok(tenants);
    }

    /**
     * 用户绑定租户
     * @param userTenantDTO 入参信息
     * @return Result
     */
    @PostMapping("/bind")
    public Result<?> bind(@RequestBody UserTenantDTO userTenantDTO) {
        userTenantService.bind(userTenantDTO);
        return Result.ok("绑定租户成功!");
    }

    /**
     * 用户解绑租户
     * @return Result
     */
    @PostMapping("/unbind")
    public Result<?> unbind(@RequestBody UserTenantDTO userTenantDTO) {
        userTenantService.unbind(userTenantDTO);
        return Result.ok("绑定租户成功!");
    }

    /**
     * 删除租户
     * @param id 租户id
     * @return Result
     * */
    @PreAuthorize("hasAuthority('tenant:delete')")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id")String id){
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setIsDelete(UpmsConstant.IS_DELETED);
        tenantService.updateById(tenant);
        return Result.ok("删除成功!");
    }
}

