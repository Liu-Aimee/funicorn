package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.datasource.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2021/10/20 11:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserOrgPageDTO extends PageDTO {

    /**
     * 机构id
     * */
    private String orgId;

    /**
     * 租户id 默认当前登陆人的租户id
     * */
    private String tenantId;
}
