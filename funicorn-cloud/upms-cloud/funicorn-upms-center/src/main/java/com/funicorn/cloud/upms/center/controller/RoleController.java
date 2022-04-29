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
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.api.model.MenuTree;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.RoleDTO;
import com.funicorn.cloud.upms.center.dto.RolePageDTO;
import com.funicorn.cloud.upms.center.dto.UserRoleDTO;
import com.funicorn.cloud.upms.center.entity.Menu;
import com.funicorn.cloud.upms.center.entity.Role;
import com.funicorn.cloud.upms.center.entity.RoleMenu;
import com.funicorn.cloud.upms.center.entity.UserRole;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.service.MenuService;
import com.funicorn.cloud.upms.center.service.RoleMenuService;
import com.funicorn.cloud.upms.center.service.RoleService;
import com.funicorn.cloud.upms.center.service.UserRoleService;
import com.funicorn.cloud.upms.center.util.TreeUtil;
import com.funicorn.cloud.upms.center.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private MenuService menuService;

    /**
     * 角色列表分页查询
     * @param rolePageDTO 分页参数
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<RoleVO>> page(RolePageDTO rolePageDTO){
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getTenantId,rolePageDTO.getTenantId());
        queryWrapper.ne(Role::getCode, UpmsConstant.ROLE_SUPER_ADMIN);
        //租户管理员只能看到普通角色
        if (!UpmsConstant.TENANT_USER_SUPER.equals(SecurityUtil.getCurrentUser().getType())) {
            queryWrapper.ne(Role::getCode, UpmsConstant.ROLE_TENANT_ADMIN);
        }
        queryWrapper.eq(Role::getIsDelete,UpmsConstant.NOT_DELETED);
        if (StringUtils.isNotBlank(rolePageDTO.getName())){
            queryWrapper.like(Role::getName,rolePageDTO.getName());
        }
        if (StringUtils.isNotBlank(rolePageDTO.getCode())){
            queryWrapper.like(Role::getCode,rolePageDTO.getCode());
        }
        IPage<Role> iPage =  roleService.page(new Page<>(rolePageDTO.getCurrent(),rolePageDTO.getSize()),queryWrapper);

        IPage<RoleVO> roleVoPage = ConvertUtil.page2Page(iPage, RoleVO.class);
        roleVoPage.getRecords().forEach(roleVo -> {
            int userNum = userRoleService.count(Wrappers.<UserRole>query().lambda().eq(UserRole::getRoleId,roleVo.getId()).eq(UserRole::getTenantId,rolePageDTO.getTenantId()));
            roleVo.setUserNum(userNum);
        });
        return Result.ok(roleVoPage);
    }

    /**
     * 获取角色菜单列表
     * @param roleId 角色id
     * @param appId 应用id
     * @return Result
     * */
    @GetMapping("/getMenuTree")
    public Result<List<MenuTree>> getMenuTree(@RequestParam String roleId,@RequestParam String appId) {
        List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId,roleId));
        List<String> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        if (menuIds.isEmpty()) {
            return Result.ok(new ArrayList<>());
        }
        List<Menu> menus = menuService.list(Wrappers.<Menu>lambdaQuery().eq(Menu::getAppId,appId).in(Menu::getId,menuIds)
                .eq(Menu::getIsDelete,UpmsConstant.NOT_DELETED).eq(Menu::getStatus,"show"));
        if (menus.isEmpty()) {
            return Result.ok(new ArrayList<>());
        }
        List<MenuTree> menuTreeList = ConvertUtil.list2List(menus,MenuTree.class);
        return Result.ok(TreeUtil.buildMenuTree(menuTreeList));
    }

    /**
     * 新增角色
     * @param roleDTO 角色信息
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated(Insert.class) RoleDTO roleDTO){
        int count = roleService.count(Wrappers.<Role>query().lambda().eq(Role::getName, roleDTO.getName()).eq(Role::getTenantId, roleDTO.getTenantId()));
        if (count>0) {
            throw new UpmsException(ErrorCode.ROLE_NAME_EXISTS,roleDTO.getName());
        }
        count = roleService.count(Wrappers.<Role>query().lambda().eq(Role::getCode, roleDTO.getCode()).eq(Role::getTenantId, roleDTO.getTenantId()));
        if (count>0) {
            throw new UpmsException(ErrorCode.ROLE_CODE_EXISTS,roleDTO.getCode());
        }
        Role role = JsonUtil.object2Object(roleDTO,Role.class);
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
        Role originalRole = roleService.getById(roleDTO.getId());
        //不允许修改角色编码
        roleDTO.setCode(null);
        if (StringUtils.isNotBlank(roleDTO.getName())){
            int count = roleService.count(Wrappers.<Role>query().lambda()
                    .eq(Role::getName, roleDTO.getName()).eq(Role::getTenantId, originalRole.getTenantId()).ne(Role::getId,roleDTO.getId()));
            if (count>0) {
                throw new UpmsException(ErrorCode.ROLE_NAME_EXISTS,roleDTO.getName());
            }
        }
        Role role = JsonUtil.object2Object(roleDTO,Role.class);
        roleService.updateById(role);
        return Result.ok(role);
    }

    /**
     * 用户关联角色
     * @param userRoleDTO 入参
     * @return Result
     * */
    @PostMapping("/roleBindUser")
    public Result<?> roleBindUser(@RequestBody UserRoleDTO userRoleDTO){
        userRoleService.userBindRole(userRoleDTO);
        return Result.ok();
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return Result
     */
    @DeleteMapping("/{roleId}")
    public Result<?> removeById(@PathVariable String roleId) {
        roleService.removeRole(roleId);
        return Result.ok();
    }
}

