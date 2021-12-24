package com.funicorn.cloud.upms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户角色和可访问应用信息
 * @author Aimee
 * @since 2021/1/27 17:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户角色集合
     * */
    private List<RoleInfo> roleInfos;
    /**
     * 用户可访问应用集合
     * */
    private Set<AppInfo> apps;
}
