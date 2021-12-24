package com.funicorn.cloud.upms.center.vo;

import com.funicorn.cloud.upms.api.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/11/2 17:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBindOrgVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 组织id
     * */
    private String orgId;

    /**
     * 已绑定的用户数组
     * */
    private List<UserInfo> binds;

    /**
     * 未绑定的用户数组
     * */
    private List<UserInfo> unbinds;
}
