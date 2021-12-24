package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.api.model.OrgTree;
import com.funicorn.cloud.upms.center.dto.OrganizationDTO;
import com.funicorn.cloud.upms.center.entity.Organization;

import java.util.List;

/**
 * <p>
 * 组织机构管理 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
public interface OrganizationService extends IService<Organization> {

    /**
     * 查询组织机构树
     * @param orgId 机构id
     * @param tenantId 租户id
     * @return OrgTree
     * */
    List<OrgTree> getOrgTree(String orgId,String tenantId);

    /**
     * 修改组织机构
     * @param organizationDTO 组织机构信息
     */
    void updateOrgById(OrganizationDTO organizationDTO);

    /**
     * 删除组织机构
     * @param orgId 组织id
     * */
    void deleteOrg(String orgId);
}
