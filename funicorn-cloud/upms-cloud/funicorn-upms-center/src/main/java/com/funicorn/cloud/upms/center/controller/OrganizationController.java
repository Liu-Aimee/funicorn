package com.funicorn.cloud.upms.center.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.api.model.OrgTree;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.center.dto.OrganizationDTO;
import com.funicorn.cloud.upms.center.dto.UserOrgDTO;
import com.funicorn.cloud.upms.center.dto.UserOrgPageDTO;
import com.funicorn.cloud.upms.center.entity.Organization;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.service.OrganizationService;
import com.funicorn.cloud.upms.center.service.UserOrgService;
import com.funicorn.cloud.upms.center.vo.UserBindOrgVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织机构管理 接口
 *
 * @author Aimee
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/Organization")
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;
    @Resource
    private UserOrgService userOrgService;

    /**
     * 查询组织机构树
     * @param orgId 机构id 传空查询整棵树
     * @param tenantId 租户id 默认当前登陆人租户id
     * @return Result<List<TreeOrgVo>>
     * */
    @GetMapping("/getOrgTree")
    public Result<List<OrgTree>> getOrgTree(@RequestParam(required = false) String orgId,@RequestParam(required = false) String tenantId){
        if (StringUtils.isBlank(tenantId)){
            tenantId = SecurityUtil.getCurrentUser().getTenantId();
        }
        List<OrgTree> treeNodes = organizationService.getOrgTree(orgId,tenantId);
        return Result.ok(treeNodes);
    }

    /**
     * 机构用户列表分页查询
     * @param userOrgPageDTO 分页参数条件
     * @return Result
     */
    @GetMapping("/getUserPage")
    public Result<IPage<UserInfo>> getUserPage(UserOrgPageDTO userOrgPageDTO) {
        if (StringUtils.isBlank(userOrgPageDTO.getTenantId())) {
            userOrgPageDTO.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
        }
        IPage<UserInfo> iPage = userOrgService.getUserPage(userOrgPageDTO);
        return Result.ok(iPage);
    }

    /**
     * 查询已绑定和未绑定该组织的所有用户
     * @param orgId 机构id
     * @param tenantId 租户id 默认当前登陆人租户id
     * @return Result
     */
    @GetMapping("/getBindUserList")
    public Result<UserBindOrgVO> getBindUserList(@RequestParam String orgId, @RequestParam(required = false) String tenantId) {
        if (StringUtils.isBlank(tenantId)){
            tenantId = SecurityUtil.getCurrentUser().getTenantId();
        }
        Organization organization = organizationService.getById(orgId);
        if (!organization.getTenantId().equals(tenantId)){
            throw new UpmsException(ErrorCode.ORG_NOT_IN_TENANT);
        }
        return Result.ok(userOrgService.getBindUserList(orgId,tenantId));
    }

    /**
     * 用户绑定组织机构
     * @param userOrgDTO 入参
     * @return Result
     * */
    @PostMapping("/bindUser")
    public Result<?> bindUser(@RequestBody UserOrgDTO userOrgDTO){
        userOrgService.bindUser(userOrgDTO);
        return Result.ok("绑定成功");
    }

    /**
     * 新增组织机构
     * @param organizationDTO 组织机构信息
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated(Insert.class) OrganizationDTO organizationDTO) {
        Organization organization = JsonUtil.object2Object(organizationDTO,Organization.class);
        if (StringUtils.isBlank(organizationDTO.getTenantId())){
            organization.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
        }
        organizationService.save(organization);
        return Result.ok(organization);
    }

    /**
     * 修改组织机构
     * @param organizationDTO 组织机构信息
     * @return Result
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody @Validated(Update.class) OrganizationDTO organizationDTO) {
        organizationService.updateOrgById(organizationDTO);
        return Result.ok("修改成功");
    }

    /**
     * 删除组织机构
     * @param id 机构id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        organizationService.deleteOrg(id);
        return Result.ok();
    }
}

