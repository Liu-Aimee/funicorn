package com.funicorn.cloud.upms.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.center.dto.UserOrgPageDTO;
import com.funicorn.cloud.upms.center.entity.UserOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与机构关系 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-11-01
 */
public interface UserOrgMapper extends BaseMapper<UserOrg> {

    /**
     * 查询机构用户列表
     * @param userOrgPageDTO 入参
     * @param page 分页信息
     * @return UserInfo
     * */
    IPage<UserInfo> selectPageOrgUsers(Page<UserInfo> page, @Param("org") UserOrgPageDTO userOrgPageDTO);

    /**
     * 查询机构用户列表
     * @param orgId 机构id
     * @param tenantId 租户id
     * @return UserInfo
     * */
    List<UserInfo> selectOrgUsers(String orgId,String tenantId);

    /**
     * 查询机构未绑定的用户列表
     * @param orgId 机构id
     * @param tenantId 租户id
     * @return UserInfo
     * */
    List<UserInfo> selectUnbindOrgUsers(String orgId,String tenantId);
}
