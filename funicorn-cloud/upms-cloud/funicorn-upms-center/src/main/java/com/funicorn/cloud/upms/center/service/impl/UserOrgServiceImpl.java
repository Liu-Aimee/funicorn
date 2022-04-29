package com.funicorn.cloud.upms.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.upms.api.model.RoleInfo;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.center.dto.UserOrgDTO;
import com.funicorn.cloud.upms.center.dto.UserOrgPageDTO;
import com.funicorn.cloud.upms.center.entity.Organization;
import com.funicorn.cloud.upms.center.entity.Role;
import com.funicorn.cloud.upms.center.entity.UserOrg;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.mapper.OrganizationMapper;
import com.funicorn.cloud.upms.center.mapper.UserOrgMapper;
import com.funicorn.cloud.upms.center.service.UserOrgService;
import com.funicorn.cloud.upms.center.service.UserRoleService;
import com.funicorn.cloud.upms.center.vo.UserBindOrgVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户与机构关系 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-11-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserOrgServiceImpl extends ServiceImpl<UserOrgMapper, UserOrg> implements UserOrgService {

    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public IPage<UserInfo> getUserPage(UserOrgPageDTO userOrgPageDTO) {
        IPage<UserInfo> userPage = baseMapper.selectPageOrgUsers(new Page<>(userOrgPageDTO.getCurrent(),userOrgPageDTO.getSize()),userOrgPageDTO);
        if (userPage!=null && userPage.getRecords()!=null && !userPage.getRecords().isEmpty()){
            userPage.getRecords().forEach(userInfo -> {
                userInfo.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
                //角色信息
                List<Role> roleList = userRoleService.getRolesByUserId(userOrgPageDTO.getTenantId(),userInfo.getId());
                if (roleList!=null && !roleList.isEmpty()){
                    userInfo.setRoleInfos(ConvertUtil.list2List(roleList, RoleInfo.class));
                }
            });
        }
        return userPage;
    }

    @Override
    public UserBindOrgVO getBindUserList(String orgId, String tenantId) {
        //已绑定的用户
        List<UserInfo> binds = baseMapper.selectOrgUsers(orgId,tenantId);
        if (binds!=null && !binds.isEmpty()) {
            binds.forEach(userInfo -> {
                userInfo.setTenantId(tenantId);
                //角色信息
                List<Role> roleList = userRoleService.getRolesByUserId(tenantId,userInfo.getId());
                if (roleList!=null && !roleList.isEmpty()){
                    userInfo.setRoleInfos(ConvertUtil.list2List(roleList, RoleInfo.class));
                }
            });
        }

        //未绑定的用户
        List<UserInfo> unbinds = baseMapper.selectUnbindOrgUsers(orgId,tenantId);
        if (unbinds!=null && !unbinds.isEmpty()) {
            unbinds.forEach(userInfo -> {
                userInfo.setTenantId(tenantId);
                userInfo.setOrgId(orgId);
                //角色信息
                List<Role> roleList = userRoleService.getRolesByUserId(tenantId,userInfo.getId());
                if (roleList!=null && !roleList.isEmpty()){
                    userInfo.setRoleInfos(ConvertUtil.list2List(roleList, RoleInfo.class));
                }
            });
        }

        return UserBindOrgVO.builder().orgId(orgId).binds(binds).unbinds(unbinds).build();
    }

    @Override
    public void bindUser(UserOrgDTO userOrgDTO) {
        Organization organization = organizationMapper.selectById(userOrgDTO.getOrgId());
        if (organization==null){
            throw new UpmsException(ErrorCode.ORG_NOT_EXISTS,userOrgDTO.getOrgId());
        }

        List<UserOrg> userOrgList = new ArrayList<>();
        userOrgDTO.getUserIds().forEach(userId->{
            UserOrg userOrg = new UserOrg();
            userOrg.setUserId(userId);
            userOrg.setOrgId(organization.getId());
            userOrg.setOrgName(organization.getName());
            userOrg.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
            userOrgList.add(userOrg);
        });

        baseMapper.delete(Wrappers.<UserOrg>lambdaQuery()
                .eq(UserOrg::getTenantId,SecurityUtil.getCurrentUser().getTenantId())
                .in(UserOrg::getUserId,userOrgDTO.getUserIds()));

        saveBatch(userOrgList);
    }
}
