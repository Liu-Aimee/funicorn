package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Aimee
 * @since 2021/8/25 14:39
 */
@Data
public class ApprovalAppDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 租户与应用关联id
     * */
    @NotBlank(message = "租户与应用关联id",groups = Update.class)
    private String id;

    /**
     * 0 同意 3 拒绝
     * */
    @NotNull(message = "审批意见不能为空",groups = Update.class)
    private Integer state;
}
