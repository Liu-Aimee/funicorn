package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/11/1 15:24
 */
@Data
public class OrganizationDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 机构id 新增时自动生成
     */
    @NotBlank(message = "机构id不能为空",groups = Update.class)
    private String id;

    /**
     * 父id 默认0
     */
    private String parentId;

    /**
     * 机构名称·
     */
    @NotBlank(message = "机构名称不能为空",groups = Insert.class)
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户id 默认当前登陆人租户
     * */
    private String tenantId;

    /**
     * 排序 升序 默认0
     */
    private Integer sort;
}
