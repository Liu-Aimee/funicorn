package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Aimee
 * @since 2021/12/2 14:43
 */
@Data
public class DictItemDTO {

    /**
     * 主键id
     * */
    @NotBlank(message = "字典项id不能为空",groups = Update.class)
    private String id;

    /**
     * 字典类型
     * */
    @NotBlank(message = "字典类型不能为空",groups = Insert.class)
    private String dictType;

    /**
     * 值
     */
    @NotBlank(message = "字典项值不能为空",groups = Insert.class)
    private String dictValue;

    /**
     * 标签
     */
    @NotBlank(message = "字典项标签不能为空",groups = Insert.class)
    private String dictLabel;

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空",groups = Insert.class)
    private String tenantId;
}
