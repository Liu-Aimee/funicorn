package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/5/5 18:52
 */
@Data
public class TenantDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空",groups = Update.class)
    private String id;

    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空",groups = Insert.class)
    private String tenantName;

    /**
     * logo地址
     */
    private String logoUrl;

    /**
     * 描述
     */
    private String description;

    /**
     * 应用id数组
     * */
    private List<String> appIds;
}
