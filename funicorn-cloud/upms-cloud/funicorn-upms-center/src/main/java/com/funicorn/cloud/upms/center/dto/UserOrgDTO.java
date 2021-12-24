package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/11/1 16:00
 */
@Data
public class UserOrgDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 机构id
     * */
    @NotBlank(message = "组织机构id不能为空",groups = Insert.class)
    private String orgId;

    /**
     * 用户id数组
     * */
    @NotEmpty(message = "用户id数组不能为空",groups = Insert.class)
    private List<String> userIds;
}
