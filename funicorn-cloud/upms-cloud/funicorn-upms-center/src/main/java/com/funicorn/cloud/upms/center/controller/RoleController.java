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
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.RoleDTO;
import com.funicorn.cloud.upms.center.dto.RolePageDTO;
import com.funicorn.cloud.upms.center.entity.Role;
import com.funicorn.cloud.upms.center.entity.UserRole;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.service.RoleService;
import com.funicorn.cloud.upms.center.service.UserRoleService;
import com.funicorn.cloud.upms.center.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色管理 接口
 *
 * @author Aimee
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/Role")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;

    /**
     * 角色列表分页查询
     * @param rolePageDTO 分页参数
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<RoleVO>> page(RolePageDTO rolePageDTO){
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getTenantId,currentUser.getTenantId());
        if (currentUser.getTenantId().equals(UpmsConstant.SUPER_TENANT_ID)){
            queryWrapper.eq(Role::getCode, UpmsConstant.ROLE_TENANT_ADMIN);
        }else {
            queryWrapper.ne(Role::getCode, UpmsConstant.ROLE_TENANT_ADMIN);
        }
        if (StringUtils.isNotBlank(rolePageDTO.getName())){
            queryWrapper.like(Role::getName,rolePageDTO.getName());
        }
        if (StringUtils.isNotBlank(rolePageDTO.getCode())){
            queryWrapper.like(Role::getCode,rolePageDTO.getCode());
        }
        IPage<Role> iPage =  roleService.page(new Page<>(rolePageDTO.getCurrent(),rolePageDTO.getSize()),queryWrapper);

        IPage<RoleVO> roleVoPage = ConvertUtil.page2Page(iPage, RoleVO.class);
        roleVoPage.getRecords().forEach(roleVo -> {
            int userNum = userRoleService.count(Wrappers.<UserRole>query().lambda().eq(UserRole::getRoleId,roleVo.getId()));
            roleVo.setUserNum(userNum);
        });
        return Result.ok(roleVoPage);
    }

    /**
     * 新增角色
     * @param roleDTO 角色信息
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated(Insert.class) RoleDTO roleDTO){
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        int count = roleService.count(Wrappers.<Role>query().lambda().eq(Role::getName, roleDTO.getName()).eq(Role::getTenantId, currentUser.getTenantId()));
        if (count>0) {
            throw new UpmsException(ErrorCode.ROLE_NAME_EXISTS,roleDTO.getName());
        }
        String code = "ROLE_" + roleDTO.getCode();
        count = roleService.count(Wrappers.<Role>query().lambda().eq(Role::getCode, code).eq(Role::getTenantId, currentUser.getTenantId()));
        if (count>0) {
            throw new UpmsException(ErrorCode.ROLE_CODE_EXISTS,roleDTO.getCode());
        }
        Role role = JsonUtil.object2Object(roleDTO,Role.class);
        role.setCode(code);
        role.setTenantId(currentUser.getTenantId());
        roleService.save(role);
        return Result.ok(role);
    }

    /**
     * 修改角色
     * @param roleDTO 角色信息
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> update(@RequestBody @Validated(Update.class) RoleDTO roleDTO){
        //不允许修改角色编码
        roleDTO.setCode(null);
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        if (StringUtils.isNotBlank(roleDTO.getName())){
            int count = roleService.count(Wrappers.<Role>query().lambda().eq(Role::getName, roleDTO.getName()).eq(Role::getTenantId, currentUser.getTenantId()));
            if (count>0) {
                throw new UpmsException(ErrorCode.ROLE_NAME_EXISTS,roleDTO.getName());
            }
        }
        Role role = JsonUtil.object2Object(roleDTO,Role.class);
        roleService.updateById(role);
        return Result.ok(role);
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return Result
     */
    @DeleteMapping("/{roleId}")
    public Result<?> deleteById(@PathVariable String roleId) {
        Role role = new Role();
        role.setId(roleId);
        role.setIsDelete(UpmsConstant.IS_DELETED);
        roleService.updateById(role);
        return Result.ok();
    }
}

