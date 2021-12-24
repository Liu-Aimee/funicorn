package com.funicorn.cloud.system.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Aimee
 * @since 2021/10/15 10:09
 */
@Data
public class DictTypeDTO {

    /**
     * 字典id
     * */
    @NotBlank(message = "字典id不能为空",groups = Update.class)
    private String id;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空",groups = Insert.class)
    private String type;

    /**
     * 类型名称
     */
    @NotBlank(message = "字典名称不能为空",groups = Insert.class)
    private String name;

    /**
     * 描述
     */
    private String remark;
}
