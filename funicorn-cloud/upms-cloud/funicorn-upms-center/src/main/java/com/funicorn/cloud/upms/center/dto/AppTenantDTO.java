package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/5/4 10:49
 */
@Data
public class AppTenantDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 租户id
     * */
    @NotBlank(message = "租户id不能为空",groups = {Insert.class, Update.class})
    private String tenantId;

    /**
     * 应用id数组
     * */
    @NotEmpty(message = "应用数组不能为空",groups = {Insert.class, Update.class})
    private List<String> appIds;
}
