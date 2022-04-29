package com.funicorn.cloud.upms.center.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.cloud.upms.api.model.MenuTree;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.MenuDTO;
import com.funicorn.cloud.upms.center.dto.RoleMenuDTO;
import com.funicorn.cloud.upms.center.entity.Menu;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.service.MenuService;
import com.funicorn.cloud.upms.center.service.RoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单管理 接口
 *
 * @author Aimee
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/Menu")
public class MenuController {

    @Resource
    private MenuService menuService;
    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 查询当前登录人选中应用的左侧菜单树
     * @param appId 应用id
     * @return Result
     */
    @GetMapping("/getCurrentMenusByAppId")
    public Result<List<MenuTree>> getCurrentMenusByAppId(@RequestParam("appId")String appId){
        return Result.ok(menuService.getCurrentMenusByAppId(appId));
    }

    /**
     * 查询当前登录人选中应用的左侧菜单树
     * @param clientId 应用唯一标识
     * @return Result
     */
    @GetMapping("/getCurrentMenusByClientId")
    public Result<List<MenuTree>> getCurrentMenusByClientId(@RequestParam("clientId")String clientId){
        return Result.ok(menuService.getCurrentMenusByClientId(clientId));
    }

    /**
     * 通过应用id查询菜单树
     * @param appId 应用id
     * @return Result
     */
    @GetMapping("/getMenusByAppId")
    public Result<List<MenuTree>> getMenusByAppId(@RequestParam("appId")String appId){
        return Result.ok(menuService.getMenusByAppId(appId));
    }

    /**
     * 根据角色查询菜单
     * @param roleId 角色id
     * @param appId 应用id
     * @return Result
     */
    @PreAuthorize("hasAuthority('upms:role:menu:bind')")
    @GetMapping("/getMenusByRoleId")
    public Result<List<MenuTree>> getMenusByRoleId(@RequestParam(value = "roleId") String roleId,@RequestParam(value = "appId") String appId) {
        List<MenuTree> treeMenus = menuService.getMenusByRoleId(roleId,appId);
        return Result.ok(treeMenus);
    }

    /**
     * 菜单绑定角色
     * @param roleMenuDTO 入参
     * @return Result
     */
    @PostMapping("/bindRole")
    public Result<?> bindRole(@RequestBody @Validated(Insert.class) RoleMenuDTO roleMenuDTO) {
        roleMenuService.bind(roleMenuDTO);
        return Result.ok("绑定成功");
    }

    /**
     * 新增菜单
     * @param menuDTO 菜单信息
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated(Insert.class) MenuDTO menuDTO) {

        List<Menu> menus = menuService.list(Wrappers.<Menu>query().lambda()
                .eq(Menu::getParentId, StringUtils.isNotBlank(menuDTO.getParentId()) ? menuDTO.getParentId() : "0")
                .eq(Menu::getName, menuDTO.getName())
                .eq(Menu::getAppId, menuDTO.getAppId())
                .eq(Menu::getIsDelete, UpmsConstant.NOT_DELETED));
        if (!menus.isEmpty()) {
            throw new UpmsException(ErrorCode.MUNU_NAME_EXISTS,menuDTO.getName());
        }
        menuService.saveMenu(menuDTO);
        return Result.ok("新增成功!");
    }

    /**
     * 修改菜单
     * @param menuDTO 菜单信息
     * @return Result
     */
    @PostMapping("/update")
    public Result<?> updateById(@RequestBody @Validated(Update.class) MenuDTO menuDTO) {
        Menu menu = JsonUtil.object2Object(menuDTO,Menu.class);
        menuService.updateById(menu);
        return Result.ok("修改成功");
    }

    /**
     * 删除菜单
     * @param id 菜单id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result<?> removeById(@PathVariable String id) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setIsDelete(UpmsConstant.IS_DELETED);
        menuService.updateById(menu);
        return Result.ok("删除成功");
    }
}

