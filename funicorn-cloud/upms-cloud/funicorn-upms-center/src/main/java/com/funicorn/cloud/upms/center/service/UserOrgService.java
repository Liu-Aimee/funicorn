package com.funicorn.cloud.upms.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.center.dto.UserOrgDTO;
import com.funicorn.cloud.upms.center.dto.UserOrgPageDTO;
import com.funicorn.cloud.upms.center.entity.UserOrg;
import com.funicorn.cloud.upms.center.vo.UserBindOrgVO;

/**
 * <p>
 * 用户与机构关系 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-11-01
 */
public interface UserOrgService extends IService<UserOrg> {

    /**
     * 机构用户列表分页查询
     * @param userOrgPageDTO 分页参数条件
     * @return Result
     */
    IPage<UserInfo> getUserPage(UserOrgPageDTO userOrgPageDTO);

    /**
     * 查询已绑定和未绑定该组织的所有用户
     * @param orgId 机构id
     * @param tenantId 租户id 默认当前登陆人租户id
     * @return Result
     */
    UserBindOrgVO getBindUserList(String orgId,String tenantId);

    /**
     * 用户绑定组织机构
     * @param userOrgDTO 入参
     * */
    void bindUser(UserOrgDTO userOrgDTO);
}
