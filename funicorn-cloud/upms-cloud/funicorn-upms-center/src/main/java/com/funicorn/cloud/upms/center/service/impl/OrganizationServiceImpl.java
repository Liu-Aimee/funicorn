package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.upms.api.model.OrgTree;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.OrganizationDTO;
import com.funicorn.cloud.upms.center.entity.Organization;
import com.funicorn.cloud.upms.center.entity.UserOrg;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.mapper.OrganizationMapper;
import com.funicorn.cloud.upms.center.mapper.UserOrgMapper;
import com.funicorn.cloud.upms.center.service.OrganizationService;
import com.funicorn.cloud.upms.center.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织机构管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Resource
    private UserOrgMapper userOrgMapper;

    @Override
    public List<OrgTree> getOrgTree(String orgId,String tenantId) {
        List<OrgTree> treeNodes = new ArrayList<>();
        List<Organization> organizations = new ArrayList<>();
        if (StringUtils.isNotBlank(orgId)){
            Organization parentOrg = baseMapper.selectOne(Wrappers.<Organization>lambdaQuery()
                    .eq(Organization::getId,orgId).eq(Organization::getTenantId,tenantId));
            if (parentOrg==null) {
                return new ArrayList<>();
            } else {
                organizations.add(parentOrg);
                organizations.addAll(getOrganizations(parentOrg.getId()));
            }
        } else {
            LambdaQueryWrapper<Organization> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Organization::getIsDelete, UpmsConstant.NOT_DELETED);
            queryWrapper.eq(Organization::getTenantId,tenantId);
            queryWrapper.orderByAsc(Organization::getParentId).orderByAsc(Organization::getSort);
            organizations = baseMapper.selectList(queryWrapper);
        }
        organizations.forEach(organization -> {
            OrgTree orgTree = JsonUtil.object2Object(organization, OrgTree.class);
            treeNodes.add(orgTree);
        });

        if (!treeNodes.isEmpty()){
            return TreeUtil.buildTree(treeNodes);
        }
        return treeNodes;
    }

    @Override
    public void updateOrgById(OrganizationDTO organizationDTO) {
        if (StringUtils.isNotBlank(organizationDTO.getName())){
            Organization oldOrganization = baseMapper.selectById(organizationDTO.getId());
            if (!organizationDTO.getName().equals(oldOrganization.getName())) {
                int count = baseMapper.selectCount(Wrappers.<Organization>lambdaQuery()
                        .eq(Organization::getName,organizationDTO.getName()).ne(Organization::getId,organizationDTO.getId()));
                if (count>0){
                    throw new UpmsException(ErrorCode.ORG_NAME_IS_EXISTS,organizationDTO.getName());
                }

                UserOrg userOrg = new UserOrg();
                userOrg.setOrgName(organizationDTO.getName());
                userOrgMapper.update(userOrg,Wrappers.<UserOrg>lambdaQuery().eq(UserOrg::getOrgId,organizationDTO.getId()));
            }
        }
        Organization organization = JsonUtil.object2Object(organizationDTO,Organization.class);
        baseMapper.updateById(organization);
    }

    @Override
    public void deleteOrg(String orgId) {
        Organization parentOrg = baseMapper.selectById(orgId);
        if (parentOrg==null){
            return;
        }

        List<Organization> organizations = new ArrayList<>();
        organizations.add(parentOrg);
        organizations.addAll(getOrganizations(parentOrg.getId()));
        Organization organization = new Organization();
        organization.setIsDelete(UpmsConstant.IS_DELETED);
        baseMapper.update(organization,Wrappers.<Organization>lambdaQuery()
                .in(Organization::getId,organizations.stream().map(Organization::getId).collect(Collectors.toList())));
    }

    /**
     * 获取某个组织下面的所有子节点,包括子节点的子节点
     * @param orgId 组织机构id
     * @return List
     * */
    private List<Organization> getOrganizations(String orgId){
        List<Organization> organizations = new ArrayList<>();
        List<Organization> list = baseMapper.selectList(Wrappers.<Organization>lambdaQuery()
                .eq(Organization::getParentId,orgId).eq(Organization::getIsDelete,UpmsConstant.NOT_DELETED));
        if (list!=null && !list.isEmpty()){
            organizations.addAll(list);
            list.forEach(organization -> {
                List<Organization> children = getOrganizations(organization.getId());
                if (!children.isEmpty()){
                    organizations.addAll(children);
                }
            });
        }
        return organizations;
    }
}
